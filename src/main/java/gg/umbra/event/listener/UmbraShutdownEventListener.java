package gg.umbra.event.listener;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.UseEntityPacketBridge;

public class UmbraShutdownEventListener
implements EventListener {

    @Listen(priority=EventPriority.LOWEST)
    public void onPacketSend(EventPacketSend eventPacketSend) {
        UseEntityPacketBridge useEntityPacketBridge;
        int entityId;
        Packet packet = eventPacketSend.getPacket();
        if (UseEntityPacketBridge.isUseEntityPacket(packet) && ClientSettings.isReservedEntityId(entityId = (useEntityPacketBridge = new UseEntityPacketBridge(packet.getObject())).getEntityId())) {
            eventPacketSend.setCancelled(true);
        }
    }
}

