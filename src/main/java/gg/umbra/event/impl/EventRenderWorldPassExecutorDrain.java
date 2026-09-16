package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.utils.ThreadBoundExecutor;
import gg.umbra.wrapper.impl.DeltaTracker;
import gg.umbra.Umbra;

public class EventRenderWorldPassExecutorDrain
extends Event {
    private static final EventListeners EVENT_LISTENERS;
    private final float partialTicks;
    public static final ThreadBoundExecutor EXECUTOR;
    private static String[] obfuscationState;

    public EventRenderWorldPassExecutorDrain(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public static String[] getWorldPassObfuscationState() {
        return obfuscationState;
    }

    @Override
    public boolean fire() {
        try {
            EXECUTOR.runPending();
        }
        catch (Throwable throwable) {
                Umbra.logThrowable(throwable);
            }
        return false;
    }

    public static void setWorldPassObfuscationState(String[] state) {
        obfuscationState = state;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventRenderWorldPassExecutorDrain(Object deltaTrackerHandle) {
        DeltaTracker deltaTracker = new DeltaTracker(deltaTrackerHandle);
        this.partialTicks = deltaTracker.getGameTimeDeltaTicks();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static {
        EXECUTOR = new ThreadBoundExecutor();
        EVENT_LISTENERS = new EventListeners();
        EventRenderWorldPassExecutorDrain.setWorldPassObfuscationState(null);
    }
}

