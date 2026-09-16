package gg.umbra.status;

import gg.umbra.Umbra;
import gg.umbra.settings.TextGuiSettings;
import gg.umbra.runtime.NativeBridge;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.utils.TimerUtil;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.ServerData;

public class NativePresenceUpdater {
    private final TimerUtil updateTimer = new TimerUtil();
    private static GuiComponent[] controlFlowMarker;
    private String lastServerDescription;
    private String lastClientDescription;

    public void updatePresence(boolean enabled) {
        if (!this.updateTimer.hasTimeElapsed(1000L)) {
            return;
        }
        this.updateTimer.reset();
        if (!enabled) {
            NativeBridge.updc(null, null);
            return;
        }
        ServerData serverData = Minecraft.H();
        String serverDescription = "Not in a server";
        if (serverData.isNotNull()) {
            serverDescription = "Playing legit on " + serverData.getServerIp();
        }
        String clientDescription = Umbra.INSTANCE.getHackManager().getMod(TextGuiSettings.class).getEnabledModuleNames();
        if (clientDescription.length() >= 128) {
            clientDescription = clientDescription.substring(0, 128);
        }
        if (!serverDescription.equals(this.lastServerDescription)
                || !this.lastClientDescription.equals(clientDescription)) {
            NativeBridge.updc(serverDescription, clientDescription);
        }
        this.lastServerDescription = serverDescription;
        this.lastClientDescription = clientDescription;
    }

    static {
        NativePresenceUpdater.setControlFlowMarker(new GuiComponent[5]);
    }


    public static void setControlFlowMarker(GuiComponent[] marker) {
        controlFlowMarker = marker;
    }

    public static GuiComponent[] getControlFlowMarker() {
        return controlFlowMarker;
    }
}

