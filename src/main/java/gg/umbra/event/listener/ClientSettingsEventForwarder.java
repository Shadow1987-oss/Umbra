package gg.umbra.event.listener;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.settings.ClientSettings;
import gg.umbra.utils.render.RenderUtils;

public class ClientSettingsEventForwarder
implements EventListener {
    private ClientSettings clientSettings;


    @Listen
    public void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
        ClientSettings clientSettings = this.getClientSettings();
        if (clientSettings == null) {
            return;
        }
        RenderUtils.C();
        clientSettings.updateGuiScale();
    }

    private ClientSettings getClientSettings() {
        if (this.clientSettings == null) {
            this.clientSettings = Umbra.INSTANCE.getHackManager().getMod(ClientSettings.class);
        }
        return this.clientSettings;
    }

    @Listen
    public void onTick(EventPreTick eventPreTick) {
        ClientSettings clientSettings = this.getClientSettings();
        if (clientSettings == null) {
            return;
        }
        clientSettings.tickStandaloneMode();
        clientSettings.updateTickFrames();
    }
}

