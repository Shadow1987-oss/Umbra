package gg.umbra.event;

import gg.umbra.event.ICancelableEvent;

public abstract class BasicCancelableEvent
implements ICancelableEvent {
    private boolean canceled;

    @Override
    public void setCancelled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }
}
