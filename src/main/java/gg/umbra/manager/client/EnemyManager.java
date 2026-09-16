/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 */
package gg.umbra.manager.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import gg.umbra.friend.Enemy;
import gg.umbra.friend.ui.EnemySettingsFrame;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.settings.ClientSettings;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityOtherPlayerMP;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import gg.umbra.Umbra;

public class EnemyManager {
    public ToggleSetting useAlias;
    public ColorPicker enemyColor;
    public ToggleSetting useEnemies;
    public ToggleSetting useColor;
    public ToggleSetting spoofAlias;
    private final Set<Enemy> enemies = new HashSet<Enemy>();
    private static int[] obfuscationState;

    public EnemyManager() {
        this.useEnemies = ToggleSetting.create(this, "Use Enemies", true);
        this.useAlias = ToggleSetting.create(this, "Use Alias", true);
        this.spoofAlias = ToggleSetting.create(this, "Spoof alias", false, "This will make the enemies name be replaced in chat with their alias.\nApplies on regular Nametags as well");
        this.useColor = ToggleSetting.create(this, "Use color", true, "Re-colors certain render modules to use \"Enemies Color\" on enemies");
        this.enemyColor = ColorPicker.create(this, "Enemies Color", new Color(244, 66, 66));
        this.spoofAlias.addChangeListener(this::onSpoofAliasChanged);
    }

    public static void setObfuscationState(int[] state) {
        obfuscationState = state;
    }

    public void clearEnemies() {
        this.getEnemies().clear();
    }

    public boolean isEnemy(String name) {
        if (!this.useEnemies.getEffectiveValue().booleanValue()) {
            return false;
        }
        Enemy enemy = this.getEnemy(name);
        return enemy != null;
    }

    private void onSpoofAliasChanged(ToggleSetting ignored) {
        this.refreshPlayerNames();
    }

    public void addEnemy(Enemy enemy) {
        Enemy existing = this.getEnemy(enemy.getName());
        if (existing != null) {
            this.enemies.remove(existing);
        }
        this.enemies.add(enemy);
        this.refreshPlayerNames();
    }

    public Enemy findTargetedEnemy(String name, boolean respectEnabledSetting) {
        if (this.useEnemies.getEffectiveValue().booleanValue() || !respectEnabledSetting) {
            Enemy enemy = this.getEnemy(name);
            if (enemy != null) {
                return null;
            }
            return enemy;
        }
        return null;
    }

    public boolean isExclusiveEnemy(EntityLivingBase entity) {
        Enemy enemy = this.findTargetedEnemy(entity.getName());
        if (enemy != null) {
            return enemy.isExclusive();
        }
        return false;
    }

    public static int[] getObfuscationState() {
        return obfuscationState;
    }

    public boolean isEnemy(EntityLivingBase entity) {
        return this.isEnemy(entity.getName());
    }

    public void refreshPlayerNames() {
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        for (Object playerObject : Minecraft.theWorld().X()) {
            new EntityPlayer(playerObject).w$src$V$1iu649y();
        }
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public JsonArray toJson() {
        JsonArray result = new JsonArray();
        for (Enemy enemy : this.getEnemies()) {
            result.add((JsonElement)enemy.toJson());
        }
        return result;
    }

    public void loadJson(JsonArray serializedEnemies) {
        if (serializedEnemies.size() == 0) {
            return;
        }
        this.clearEnemies();
        for (int index = 0; index < serializedEnemies.size(); ++index) {
            try {
                JsonElement element = serializedEnemies.get(index);
                if (!element.isJsonObject() || element.isJsonNull()) continue;
                Enemy enemy = Enemy.fromJson(element.getAsJsonObject());
                this.addEnemy(enemy);
                continue;
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        }
        ClientSettings.getFrame(EnemySettingsFrame.class).refreshEntries();
    }

    public Enemy getEnemy(String name) {
        for (Enemy enemy : this.enemies) {
            if (!enemy.getName().equalsIgnoreCase(name)) continue;
            return enemy;
        }
        return null;
    }

    public Enemy findTargetedEnemy(String name) {
        return this.findTargetedEnemy(name, true);
    }

    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
        this.refreshPlayerNames();
    }

    public Set<Enemy> getEnemies() {
        return this.enemies;
    }

    public void toggleCrosshairTarget() {
        RayTraceResult rayTraceResult = Minecraft.p$src$Lgg_umbra_wrapper_impl_RayTraceResult_$5rw6n0();
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
            Enemy enemy = this.getEnemy(name);
            if (enemy != null) {
                this.removeEnemy(enemy);
            } else {
                this.addEnemy(new Enemy(name, name));
            }
        }
    }

    static {
        EnemyManager.setObfuscationState(null);
    }
}

