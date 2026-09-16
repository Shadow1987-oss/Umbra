package gg.umbra.event;

import gg.umbra.event.EventListenerInvoker;
import gg.umbra.event.EventListenerRegistration;
import gg.umbra.event.EventPriority;
import gg.umbra.event.IEvent;

public class EventListenerEntry {
    private final EventPriority priority;
    private final EventListenerRegistration registration;
    private final boolean skipCanceled;
    private final EventListenerInvoker invoker;

    public EventListenerInvoker getInvoker() {
        return this.invoker;
    }

    public EventListenerRegistration getRegistration() {
        return this.registration;
    }

    public boolean shouldSkipCanceled() {
        return this.skipCanceled;
    }

    public EventPriority getPriority() {
        return this.priority;
    }

    public EventListenerEntry(EventListenerRegistration registration, EventPriority priority, boolean skipCanceled, EventListenerInvoker invoker) {
        this.registration = registration;
        this.priority = priority;
        this.skipCanceled = skipCanceled;
        this.invoker = invoker;
    }

    public <T extends IEvent> T invoke(T event) {
        this.invoker.invoke(event);
        return event;
    }
}
