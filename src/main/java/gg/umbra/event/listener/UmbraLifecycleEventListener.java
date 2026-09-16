package gg.umbra.event.listener;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.PlayerEventNameFormat;

public class UmbraLifecycleEventListener
implements EventListener {

    @Listen
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        PlayerEventNameFormat resourcePackPacket;
        String resourcePackUrl;
        if (eventPacketReceive.getPacket().isInstance(MappedClasses.l3) && (resourcePackUrl = (resourcePackPacket = new PlayerEventNameFormat(eventPacketReceive.getPacket())).getUrl()).contains("umbra")) {
            resourcePackPacket.setUrl(resourcePackUrl.replace("umbra", "-"));
        }
    }
}

