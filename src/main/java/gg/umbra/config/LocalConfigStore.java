package gg.umbra.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.umbra.Umbra;
import gg.umbra.manager.client.ProfilesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Local persistence for the "Auto save" setting.
 *
 * The client used to persist settings only through its online account
 * sync. With the network layer removed, nothing was written to disk, so
 * every session started from defaults. This store writes the full client
 * state (profiles, friends, settings, active profile) to
 * %APPDATA%\.umbra\config.json, both periodically (so a crash does not
 * lose recent changes) and when the game closes.
 */
public class LocalConfigStore {
    private static final String CONFIG_FILE_NAME = "config.json";
    private static final String CONFIG_BACKUP_FILE_NAME = "config.bak.json";
    private static final String ACTIVE_PROFILE_KEY = "activeProfileId";
    private static final long PERIODIC_SAVE_INTERVAL_MS = 20000L;
    private static final AtomicBoolean periodicSaverStarted = new AtomicBoolean(false);

    private LocalConfigStore() {
    }

    private static File configFile() {
        String baseDirectoryPath = System.getenv("APPDATA");
        if (baseDirectoryPath == null) {
            baseDirectoryPath = System.getProperty("user.home");
        }
        File clientDirectory = new File(baseDirectoryPath + File.separator + ".umbra");
        if (!clientDirectory.exists()) {
            clientDirectory.mkdirs();
        }
        return new File(clientDirectory, CONFIG_FILE_NAME);
    }

    private static File backupFile() {
        File config = configFile();
        return new File(config.getParentFile(), CONFIG_BACKUP_FILE_NAME);
    }

    private static void copyFile(File source, File target) throws Exception {
        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void deleteQuietly(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception ignored) {
            // best effort
        }
    }

    private static boolean isAutoSaveEnabled() {
        return Umbra.INSTANCE != null
                && Umbra.INSTANCE.getPublicProfileSettings() != null
                && Umbra.INSTANCE.getPublicProfileSettings().autoSave.getEffectiveValue().booleanValue();
    }

    /**
     * Starts a low-frequency background saver so a hard crash (e.g. a native
     * driver fault) does not wipe the user's module states. The daemon thread
     * only writes while "Auto save" is enabled.
     */
    public static void startPeriodicSaver() {
        if (!periodicSaverStarted.compareAndSet(false, true)) {
            return;
        }
        Thread saver = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(PERIODIC_SAVE_INTERVAL_MS);
                }
                catch (InterruptedException interrupted) {
                    return;
                }
                try {
                    if (isAutoSaveEnabled()) {
                        save();
                    }
                }
                catch (Exception exception) {
                    Umbra.logThrowable(exception);
                }
            }
        }, "Umbra Auto Save");
        saver.setDaemon(true);
        saver.start();
    }

    /**
     * Serializes the full client state to disk. Called from the JVM shutdown
     * hook when the game closes and from the periodic saver. When "Auto save"
     * is disabled, any previously saved config is removed so the next launch
     * starts fresh.
     */
    public static void save() {
        try {
            if (Umbra.INSTANCE == null) {
                return;
            }
            if (!isAutoSaveEnabled()) {
                File existing = configFile();
                if (existing.exists()) {
                    existing.delete();
                }
                return;
            }
            ProfilesManager profilesManager = Umbra.INSTANCE.getProfilesManager();
            if (profilesManager == null) {
                return;
            }
            Profile activeProfile = profilesManager.getActiveProfileOrNull();
            if (activeProfile != null) {
                // Capture the current module states into the active profile so
                // what the user left enabled is what gets restored.
                activeProfile.captureCurrentState();
            }
            JsonObject root = new JsonObject();
            root.add("friends", Umbra.INSTANCE.getFriendManager().toJson());
            JsonObject profilesJson = new JsonObject();
            for (Profile profile : profilesManager.getProfiles()) {
                try {
                    // A profile that was never captured has no data yet; skip
                    // it instead of aborting the whole save.
                    if (profile.getData() == null) {
                        continue;
                    }
                    profilesJson.add(profile.getLocalId().toString(), profile.toJson(false));
                }
                catch (Exception ignored) {
                    // keep saving the remaining profiles
                }
            }
            root.add("profiles", profilesJson);
            root.add("otherData", Umbra.INSTANCE.getSettingsManager().toJson());
            if (activeProfile != null) {
                root.addProperty(ACTIVE_PROFILE_KEY, activeProfile.getLocalId().toString());
            }
            File file = configFile();
            if (file.exists()) {
                // Keep the last known-good config around; if a crash cuts the
                // next write short, load() can recover from the backup.
                copyFile(file, backupFile());
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                gson.toJson(root, writer);
            }
            Umbra.debugLog("Auto save: config saved to " + file.getAbsolutePath());
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
    }

    /**
     * Restores a previously auto-saved config during startup. No-op when the
     * file does not exist (user never enabled "Auto save" or removed it by
     * disabling the setting). If the main config is corrupt, the backup is
     * restored automatically so a crashed write never wipes the profiles.
     */
    public static void load() {
        if (Umbra.INSTANCE == null) {
            return;
        }
        File file = configFile();
        if (!file.exists()) {
            return;
        }
        if (tryLoadFrom(file)) {
            return;
        }
        File backup = backupFile();
        if (backup.exists()) {
            try {
                copyFile(backup, file);
            }
            catch (Exception copyFailure) {
                Umbra.logThrowable(copyFailure);
            }
            if (tryLoadFrom(file)) {
                Umbra.debugLog("Auto save: config restored from backup " + backup.getAbsolutePath());
                return;
            }
        }
        // Both the config and the backup failed (or no backup exists): remove
        // them so the next session starts clean instead of failing every time.
        deleteQuietly(file);
        deleteQuietly(backup);
    }

    private static boolean tryLoadFrom(File file) {
        try {
            JsonObject root;
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                root = JsonParser.parseReader(reader).getAsJsonObject();
            }
            if (root == null) {
                return false;
            }
            Umbra.INSTANCE.loadConfigData(root, true);
            String activeProfileId = ConfigJsonUtils.getString(root, ACTIVE_PROFILE_KEY);
            if (activeProfileId != null) {
                try {
                    Profile restoredProfile = Umbra.INSTANCE.getProfilesManager()
                            .getProfileByLocalId(UUID.fromString(activeProfileId));
                    if (restoredProfile != null) {
                        Umbra.INSTANCE.getProfilesManager().setActiveProfile(restoredProfile);
                    }
                }
                catch (IllegalArgumentException ignored) {
                    // malformed stored id; keep the default active profile
                }
            }
            Umbra.debugLog("Auto save: config loaded from " + file.getAbsolutePath());
            return true;
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
            return false;
        }
    }
}
