package gg.umbra.visual.hud;

import gg.umbra.Umbra;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.PingHudFrame;
import gg.umbra.wrapper.impl.GameProfile;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.PlayerInfo;
import java.util.Collection;

public class PingHudModule
extends HudModule {
    @Override
    public String getId() {
        return "ping";
    }

    public PingHudModule() {
        super("Ping", HudModuleGroup.HUD, "ping", PingHudFrame.class);
        this.setSuffix("Shows your ping to the server");
    }

    public static int getPingMillis() {
        try {
            if (Minecraft.thePlayer().isNull()) {
                return -1;
            }
            String ownName = Minecraft.getSessionUsername();
            if (ownName == null) {
                return -1;
            }
            Collection<?> infos = Minecraft.thePlayer().sendQueue().getPlayerInfoMap();
            if (infos == null) {
                return -1;
            }
            for (Object handle : infos) {
                PlayerInfo info = new PlayerInfo(handle);
                GameProfile profile = info.v();
                if (profile != null && ownName.equals(profile.getName())) {
                    return info.z();
                }
            }
        } catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
        return -1;
    }
}
