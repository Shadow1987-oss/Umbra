package gg.umbra.event;

import gg.umbra.event.EventListener;
import gg.umbra.event.GeneratedEventListenerInvoker;
import gg.umbra.event.IEvent;
import gg.umbra.event.ReflectiveEventListenerInvoker;
import java.lang.reflect.Method;

public interface EventListenerInvoker {
    public <T extends IEvent> void invoke(T event);

    public static EventListenerInvoker create(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        try {
            return new GeneratedEventListenerInvoker(listener, eventType, method);
        }
        catch (Throwable throwable) {
            return new ReflectiveEventListenerInvoker(listener, eventType, method);
        }
    }
}
