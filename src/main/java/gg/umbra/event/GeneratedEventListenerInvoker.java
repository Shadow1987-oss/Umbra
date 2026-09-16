package gg.umbra.event;

import gg.umbra.event.EventListenerInvoker;
import gg.umbra.event.EventListener;
import gg.umbra.event.GeneratedEventListenerInvokerMarker;
import gg.umbra.event.IEvent;
import gg.umbra.mapping.access.GeneratedAccessorFactory;
import java.lang.reflect.Method;

public class GeneratedEventListenerInvoker
implements EventListenerInvoker {
    private final Class<? extends IEvent> eventType;
    private final GeneratedEventListenerInvokerMarker generatedInvoker;
    private final EventListener listener;
    static final boolean ASSERTIONS_DISABLED = !GeneratedEventListenerInvoker.class.desiredAssertionStatus();

    public GeneratedEventListenerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) throws InstantiationException, IllegalAccessException {
        this.listener = listener;
        this.eventType = eventType;
        Class<? extends GeneratedEventListenerInvokerMarker> generatedInvokerClass = GeneratedAccessorFactory.N(method.getDeclaringClass(), method);
        if (!ASSERTIONS_DISABLED && generatedInvokerClass == null) {
            throw new AssertionError();
        }
        this.generatedInvoker = generatedInvokerClass.newInstance();
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    @Override
    public <T extends IEvent> void invoke(T event) {
        this.generatedInvoker.invoke(this.listener, event);
    }
}
