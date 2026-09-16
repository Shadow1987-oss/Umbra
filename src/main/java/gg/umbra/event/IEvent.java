package gg.umbra.event;

import gg.umbra.event.EventDispatcher;
import gg.umbra.event.EventListeners;

public interface IEvent {
    public EventListeners getListeners();

    default public boolean fire() {
        EventDispatcher.getInstance().post(this);
        return true;
    }
}

