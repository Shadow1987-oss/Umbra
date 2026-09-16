package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.ResourceLocationKey;
import gg.umbra.wrapper.impl.ResourceLocationName;

public class EventPreRenderLivingSpecials
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Object resourceLocationHandle;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EventPreRenderLivingSpecials(Object resourceLocationHandle) {
        this.resourceLocationHandle = resourceLocationHandle;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        try {
            ResourceLocationName resourceLocationName = new ResourceLocationName(this.resourceLocationHandle);
            String resourceName = ForgeVersion.c() == ForgeVersion.MC_1_8_9.i()
                    ? resourceLocationName.getCompleteReport()
                    : resourceLocationName.getFriendlyReport(ResourceLocationKey.L());
            Umbra.logError(resourceName);
        }
        catch (Throwable throwable) {
                Umbra.logThrowable(throwable);
            }
        return super.fire();
    }
}
