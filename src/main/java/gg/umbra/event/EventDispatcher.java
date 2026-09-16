package gg.umbra.event;

import gg.umbra.event.EventDispatchTrace;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventListenerRegistration;
import gg.umbra.event.EventListenerTiming;
import gg.umbra.event.EventListeners;
import gg.umbra.event.EventPriority;
import gg.umbra.event.EventTimingHistory;
import gg.umbra.event.IEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import gg.umbra.Umbra;

public class EventDispatcher {
    public static boolean timingEnabled;
    private static int obfuscationState;
    private static Method cachedHasListenersMethod;
    private final EventTimingHistory timingHistory;
    private static Method cachedFireMethod;
    private final Map<Class<? extends IEvent>, EventListeners> listenerCountersByEventType = new LinkedHashMap<Class<? extends IEvent>, EventListeners>();
    private final Map<EventListener, EventListenerRegistration> registrationsByListener = new LinkedHashMap<EventListener, EventListenerRegistration>();
    private static final EventDispatcher INSTANCE;
    private final Map<Class<? extends IEvent>, ArrayList<EventListenerRegistration>> registrationsByEventType = new LinkedHashMap<Class<? extends IEvent>, ArrayList<EventListenerRegistration>>();

    private static Throwable identityThrowable(Throwable throwable) {
        return throwable;
    }

    public static void setObfuscationState(int state) {
        obfuscationState = state;
    }

    public EventTimingHistory getTimingHistory() {
        return this.timingHistory;
    }

    public static Method getHasListenersMethod() {
        if (cachedHasListenersMethod == null) {
            for (Method method : EventListeners.class.getDeclaredMethods()) {
                if (method.getReturnType() != Boolean.TYPE || method.getParameterCount() != 0) continue;
                cachedHasListenersMethod = method;
                break;
            }
        }
        return cachedHasListenersMethod;
    }

    private EventListeners resolveEventListeners(Class<? extends IEvent> eventType) {
        EventListeners eventListeners = this.listenerCountersByEventType.get(eventType);
        if (eventListeners != null) {
            return eventListeners;
        }
        try {
            Method method = EventDispatcher.findEventListenersAccessor(eventType);
            if (method == null) {
                return null;
            }
            EventListeners resolvedListeners = (EventListeners)method.invoke(null, new Object[0]);
            this.listenerCountersByEventType.put(eventType, resolvedListeners);
            return resolvedListeners;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    static {
        INSTANCE = new EventDispatcher();
        timingEnabled = false;
        EventDispatcher.setObfuscationState(32);
    }

    public static int getObfuscationState() {
        return obfuscationState;
    }

    public static int getObfuscationZero() {
        int state = EventDispatcher.getObfuscationState();
        return 0;
    }

    public Map<Class<? extends IEvent>, ArrayList<EventListenerRegistration>> getRegistrationsByEventType() {
        return this.registrationsByEventType;
    }

    public static Method findEventListenersAccessor(Class<? extends IEvent> eventType) {
        for (Method method : eventType.getDeclaredMethods()) {
            if (method.getReturnType() != EventListeners.class || method.getParameterCount() != 0 || !Modifier.isStatic(method.getModifiers())) continue;
            return method;
        }
        if (IEvent.class.isAssignableFrom(eventType.getSuperclass())) {
            return EventDispatcher.findEventListenersAccessor(eventType.getSuperclass().asSubclass(IEvent.class));
        }
        return null;
    }

    public <T extends IEvent> T post(T event) {
        EventDispatchTrace dispatchTrace = null;
        if (timingEnabled) {
            dispatchTrace = new EventDispatchTrace(event.getClass());
        }
        try {
            ArrayList<EventListenerRegistration> registrations = this.registrationsByEventType.get(event.getClass());
            if (registrations != null && !registrations.isEmpty()) {
                for (EventPriority priority : EventPriority.values()) {
                    for (int index = 0; index < registrations.size(); ++index) {
                        EventListenerRegistration registration = registrations.get(index);
                        if (!registration.passesFilters(event)) continue;
                        try {
                            EventListenerTiming listenerTiming = null;
                            if (timingEnabled) {
                                listenerTiming = new EventListenerTiming(registration);
                            }
                            registration.dispatch(event, priority);
                            if (!timingEnabled) continue;
                            listenerTiming.finish();
                            dispatchTrace.addListenerTiming(listenerTiming);
                        }
                        catch (Throwable throwable) {
                Umbra.logThrowable(throwable);
            }
                    }
                }
            }
        }
        catch (Throwable throwable) {
                Umbra.logThrowable(throwable);
            }
        if (timingEnabled) {
            dispatchTrace.finish();
            this.timingHistory.addTrace(dispatchTrace);
        }
        return event;
    }

    private static ArrayList<EventListenerRegistration> createRegistrationList(Class<? extends IEvent> eventType) {
        return new ArrayList<>();
    }

    public boolean unregisterListener(EventListener eventListener) {
        if (eventListener == null) {
            return false;
        }
        EventListenerRegistration registration = this.registrationsByListener.remove(eventListener);
        if (registration == null) {
            return false;
        }
        Collection<Class<? extends IEvent>> eventTypes = registration.getEventTypes();
        if (eventTypes == null || eventTypes.isEmpty()) {
            return false;
        }
        for (Class<? extends IEvent> eventType : eventTypes) {
            List<EventListenerRegistration> registrations = this.registrationsByEventType.get(eventType);
            if (registrations == null) continue;
            registrations.remove(registration);
            this.resolveEventListeners(eventType).decrementListenerCount();
        }
        return true;
    }

    public static EventDispatcher getInstance() {
        return INSTANCE;
    }

    public EventDispatcher() {
        this.timingHistory = new EventTimingHistory();
    }

    @SafeVarargs
    public final void registerListener(EventListener eventListener, Predicate<IEvent> ... filters) {
        if (this.registrationsByListener.containsKey(eventListener)) {
            return;
        }
        EventListenerRegistration registration = new EventListenerRegistration(eventListener, filters);
        this.registrationsByListener.put(eventListener, registration);
        for (Class<? extends IEvent> eventType : registration.getEventTypes()) {
            this.registrationsByEventType.computeIfAbsent(eventType, EventDispatcher::createRegistrationList).add(registration);
            this.resolveEventListeners(eventType).incrementListenerCount();
        }
    }

    public static Method getFireMethod(Class<?> eventType) {
        if (cachedFireMethod == null) {
            for (Method method : IEvent.class.getDeclaredMethods()) {
                if (method.getReturnType() != Boolean.TYPE || method.getParameterCount() != 0) continue;
                cachedFireMethod = method;
                break;
            }
        }
        return cachedFireMethod;
    }
}
