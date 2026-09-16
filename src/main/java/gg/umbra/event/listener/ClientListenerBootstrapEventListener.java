package gg.umbra.event.listener;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventLivingUpdate;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.SPacketEntityStatus;
import gg.umbra.wrapper.impl.WorldClient;

public class ClientListenerBootstrapEventListener
implements EventListener {
    private static GuiComponent[] obfuscationState;


    @Listen(priority=EventPriority.LOW)
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        if (!eventPacketReceive.getPacket().isInstance(MappedClasses.lU)) {
            return;
        }
        SPacketEntityStatus entityStatusPacket = new SPacketEntityStatus(eventPacketReceive.getPacket().getObject());
        WorldClient world = Minecraft.theWorld();
        if (world.isNull()) {
            return;
        }
        Entity entity = world.V(entityStatusPacket.getEntityId());
        if (entity.isNull()) {
            return;
        }
        new EventLivingUpdate(entity).fire();
    }

    static {
        if (ClientListenerBootstrapEventListener.getObfuscationState() != null) {
            ClientListenerBootstrapEventListener.setObfuscationState(new GuiComponent[1]);
        }
    }

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }

    public static void setObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }
}

