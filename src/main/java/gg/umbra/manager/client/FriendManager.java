/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  org.jetbrains.annotations.Nullable
 */
package gg.umbra.manager.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import gg.umbra.Umbra;
import gg.umbra.friend.Friend;
import gg.umbra.friend.FriendEntry;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.utils.RayTraceUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityOtherPlayerMP;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public class FriendManager {
    public ToggleSetting useFriends;
    public ToggleSetting spoofAlias;
    private static String obfuscationMarker;
    public ColorPicker friendColor;
    public ToggleSetting useAlias;
    private final Set<FriendEntry> friends = new HashSet<FriendEntry>();
    private final Map<FriendCacheKey, Boolean> friendCache = new HashMap<FriendCacheKey, Boolean>();
    public ToggleSetting recolorVisuals;

    public static String getObfuscationMarker() {
        return obfuscationMarker;
    }

    public static void setObfuscationMarker(String marker) {
        obfuscationMarker = marker;
    }

    public void refreshPlayerNames() {
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        for (Object playerObject : Minecraft.theWorld().X()) {
            if (playerObject == null) {
                return;
            }
            new EntityPlayer(playerObject).w$src$V$1iu649y();
        }
    }

    @Nullable
    public FriendEntry findTargetedFriend(String name, boolean respectEnabledSetting) {
        if (this.useFriends.getEffectiveValue().booleanValue() || !respectEnabledSetting) {
            ArrayList<FriendEntry> matches = this.getFriendsByName(name);
            if (matches.isEmpty()) {
                return null;
            }
            return matches.stream().filter(FriendEntry::isTargeted).findFirst().orElse(null);
        }
        return null;
    }

    public void invalidateFriendCache() {
        this.friendCache.clear();
    }

    public void removeFriend(FriendEntry friendEntry) {
        this.friends.remove(friendEntry);
        this.invalidateFriendCache();
        this.refreshPlayerNames();
    }

    public JsonArray toJson() {
        JsonArray jsonArray = new JsonArray();
        for (FriendEntry friendEntry : this.getFriends()) {
            if (!friendEntry.isPersistent()) continue;
            jsonArray.add((JsonElement)friendEntry.toJson());
        }
        return jsonArray;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public void toggleCrosshairTarget() {
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        RayTraceResult rayTraceResult = RayTraceUtil.o();
        if (rayTraceResult.isNull()) {
            return;
        }
        Entity entity = rayTraceResult.getEntity();
        if (entity.isNull()) {
            return;
        }
        if (entity.isInstance(MappedClasses.lG)) {
            EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(entity);
            String name = entityOtherPlayerMP.getName();
            ArrayList<FriendEntry> matches = this.getFriendsByName(name);
            if (matches.isEmpty()) {
                this.addFriend(new Friend(name, name));
                Umbra.INSTANCE.getNotificationManager().showInfo("\u00a7aAdded\u00a7r " + name + " to friends", "", 2000L);
            } else {
                this.removeFriend(matches.get(0));
                Umbra.INSTANCE.getNotificationManager().showInfo("\u00a7cRemoved\u00a7r " + name + " from friends", "", 2000L);
            }
        }
    }

    @Nullable
    public FriendEntry findTargetedFriend(String name) {
        return this.findTargetedFriend(name, true);
    }

    public boolean isFriend(EntityLivingBase entityLivingBase) {
        Object worldHandle = Minecraft.theWorld().isNull() ? null : Minecraft.theWorld().getObject();
        FriendCacheKey key = new FriendCacheKey(worldHandle, entityLivingBase.S());
        Boolean cached = this.friendCache.get(key);
        if (cached != null) {
            return cached.booleanValue();
        }
        boolean result = this.isFriend(entityLivingBase.getName()) || this.isFriend(entityLivingBase.X$src$Ljava_util_UUID_$1o5dyg6().toString());
        if (this.friendCache.size() > 1024) {
            this.friendCache.clear();
        }
        this.friendCache.put(key, result);
        return result;
    }

    public boolean isFriend(String name) {
        if (!this.useFriends.getEffectiveValue().booleanValue()) {
            return false;
        }
        ArrayList<FriendEntry> matches = this.getFriendsByName(name);
        return !matches.isEmpty() && matches.stream().anyMatch(FriendEntry::isTargeted);
    }

    public ArrayList<FriendEntry> getFriendsByName(String name) {
        ArrayList<FriendEntry> matches = new ArrayList<FriendEntry>();
        for (FriendEntry friendEntry : this.friends) {
            if (!friendEntry.getName().equalsIgnoreCase(name)) continue;
            matches.add(friendEntry);
        }
        return matches;
    }

    public void addFriend(FriendEntry friendEntry) {
        if (friendEntry == null) {
            return;
        }
        ArrayList<FriendEntry> matches = this.getFriendsByName(friendEntry.getName());
        if (!matches.isEmpty()) {
            for (FriendEntry friendEntry2 : matches) {
                this.removeFriend(friendEntry2);
            }
        }
        this.friends.add(friendEntry);
        this.invalidateFriendCache();
        this.refreshPlayerNames();
    }

    private void onSpoofAliasChanged(ToggleSetting ignored) {
        this.refreshPlayerNames();
    }

    public void loadFriends(JsonArray jsonArray) {
        if (jsonArray.size() == 0) {
            return;
        }
        this.clearFriends();
        for (int i = 0; i < jsonArray.size(); ++i) {
            try {
                if (i > 100) break;
                JsonElement jsonElement = jsonArray.get(i);
                if (!jsonElement.isJsonObject() || jsonElement.isJsonNull()) continue;
                Friend friend = new Friend("", "").loadJson(jsonElement.getAsJsonObject());
                this.addFriend(friend);
                continue;
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        }
    }

    public Set<FriendEntry> getFriends() {
        return this.friends;
    }

    public void clearFriends() {
        this.getFriends().clear();
        this.invalidateFriendCache();
    }

    public FriendManager() {
        this.useFriends = ToggleSetting.create(this, "Use friends", true, "If enabled, any usernames inside your Minecraft friends list will be excluded from certain modules\nFor example they will not be targeted by KillAura");
        this.useAlias = ToggleSetting.create(this, "Use alias", true);
        this.spoofAlias = ToggleSetting.create(this, "Spoof alias", false, "Replace the friend's name in chat, tablist, and regular nametags with their alias.");
        this.recolorVisuals = ToggleSetting.create(this, "Recolor visuals", true, "Re-colors certain render modules to use \"Friends Color\" on friends");
        this.friendColor = ColorPicker.create(this, "Friends Color", new Color(66, 244, 137));
        this.spoofAlias.addChangeListener(this::onSpoofAliasChanged);
        this.useFriends.addChangeListener(value -> this.invalidateFriendCache());
        this.recolorVisuals.addDependentValues(this.friendColor);
        this.useAlias.addDependentValues(this.spoofAlias);
    }

    private static final class FriendCacheKey {
        final Object worldHandle;
        final int entityId;

        FriendCacheKey(Object worldHandle, int entityId) {
            this.worldHandle = worldHandle;
            this.entityId = entityId;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FriendCacheKey)) {
                return false;
            }
            FriendCacheKey key = (FriendCacheKey)other;
            return this.entityId == key.entityId && this.worldHandle == key.worldHandle;
        }

        @Override
        public int hashCode() {
            return this.entityId * 31 + (this.worldHandle == null ? 0 : System.identityHashCode(this.worldHandle));
        }
    }

    static {
        FriendManager.setObfuscationMarker("p5mJgc");
    }
}

