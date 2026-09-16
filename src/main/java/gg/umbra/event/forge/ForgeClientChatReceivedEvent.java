package gg.umbra.event.forge;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventChat;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.ClientChatReceivedEvent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class ForgeClientChatReceivedEvent
implements EventListener {

    @Listen(priority=EventPriority.LOW)
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        if (!eventPacketReceive.getPacket().isInstance(MappedClasses.Zu)) {
            return;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            return;
        }
        ClientChatReceivedEvent clientChatReceivedEvent = new ClientChatReceivedEvent(eventPacketReceive.getPacket().getObject());
        EventChat eventChat = new EventChat(clientChatReceivedEvent.P());
        eventChat.fire();
        if (eventChat.isCanceled()) {
            eventPacketReceive.setCancelled(true);
        } else {
            clientChatReceivedEvent.t(eventChat.getMessage());
        }
    }
}

