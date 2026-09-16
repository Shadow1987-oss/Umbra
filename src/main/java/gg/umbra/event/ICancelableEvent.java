package gg.umbra.event;

import gg.umbra.event.EventDispatcher;
import gg.umbra.event.IEvent;

public interface ICancelableEvent
extends IEvent {
    public boolean isCanceled();

    @Override
    default public boolean fire() {
        ICancelableEvent iCancelableEvent = EventDispatcher.getInstance().post(this);
        return !iCancelableEvent.isCanceled();
    }


    public void setCancelled(boolean canceled);
}

