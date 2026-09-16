package gg.umbra.event;

import gg.umbra.event.EventListenerInvoker;
import gg.umbra.event.EventListener;
import gg.umbra.event.IEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodHandleEventListenerInvoker
implements EventListenerInvoker {
    private final EventListener listener;
    private final MethodHandle methodHandle;
    private final Class<? extends IEvent> eventType;

    @Override
    public <T extends IEvent> void invoke(T event) {
        try {
            this.methodHandle.invoke(this.listener, event);
        }
        catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public MethodHandleEventListenerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        this.listener = listener;
        this.eventType = eventType;
        try {
            this.methodHandle = MethodHandles.lookup().unreflect(method);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }
}
