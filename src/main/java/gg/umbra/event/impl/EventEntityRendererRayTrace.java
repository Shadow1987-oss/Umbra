package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.hacks.exploits.BlockIn;
import gg.umbra.hacks.exploits.BridgeBuilder;
import gg.umbra.settings.MouseDelayFix;
import gg.umbra.tools.Clutch;

public class EventEntityRendererRayTrace
extends Event {
    private static Clutch clutch;
    private static final EventListeners EVENT_LISTENERS;
    private final float partialTicks;
    private static BlockIn blockIn;
    private final Object entityHandle;
    private static MouseDelayFix mouseDelayFix;
    private static BridgeBuilder scaffold;

    public EventEntityRendererRayTrace(Object entityHandle, float partialTicks) {
        this.entityHandle = entityHandle;
        this.partialTicks = partialTicks;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if (mouseDelayFix == null) {
            mouseDelayFix = Umbra.INSTANCE.getHackManager().getMod(MouseDelayFix.class);
            scaffold = Umbra.INSTANCE.getHackManager().getMod(BridgeBuilder.class);
            clutch = Umbra.INSTANCE.getHackManager().getMod(Clutch.class);
            blockIn = Umbra.INSTANCE.getHackManager().getMod(BlockIn.class);
        }
        if (!mouseDelayFix.boolean_r() && !scaffold.boolean_r()) {
            if (!blockIn.boolean_r()) {
                if (!clutch.boolean_r()) {
                    return false;
                }
            }
        }
        return MappedClasses.z5.isInstance(this.entityHandle);
    }

    public Object getVec() {
        return Umbra.INSTANCE.getMappingsMapperCompat().Rr.jL.invokeNativeBridge(this.entityHandle, Float.valueOf(this.partialTicks));
    }


    static {
        EVENT_LISTENERS = new EventListeners();
    }
}

