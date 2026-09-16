package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.input.KeyboardInput;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.utils.ThreadBoundExecutor;
import gg.umbra.utils.render.shader.ShaderProgram;
import gg.umbra.wrapper.impl.Minecraft;

public class EventTickBase
extends Event {
    private static final EventListeners EVENT_LISTENERS;
    private static GuiComponent[] obfuscationState;
    public static final ThreadBoundExecutor POST_TICK_EXECUTOR;
    public static final ThreadBoundExecutor PRE_TICK_EXECUTOR;

    public static void setTickObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static {
        PRE_TICK_EXECUTOR = new ThreadBoundExecutor();
        POST_TICK_EXECUTOR = new ThreadBoundExecutor();
        EventTickBase.setTickObfuscationState(null);
        EVENT_LISTENERS = new EventListeners();
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    @Override
    public boolean fire() {
        try {
            if (Umbra.INSTANCE.isTickActionPending()) {
                gg.umbra.settings.ClientSettings.INSTANCE.openGui();
                Umbra.INSTANCE.setPendingTickAction(false);
            }
            if (Minecraft.theWorld().isNotNull()) {
                ClientSettings.pendingSanityReset = true;
            }
            if ((KeyboardInput.isKeyDown(163) || KeyboardInput.isKeyDown(162) || KeyboardInput.isKeyDown(161)) && KeyboardInput.isKeyDown(36) && this instanceof EventPostTick && Minecraft.currentScreen().isNull()) {
                Umbra.INSTANCE.getHackManager().getMod(gg.umbra.settings.ClientSettings.class).toggle();
            }
            ShaderProgram.setCurrentProgramId(-1);
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
        return super.fire();
    }

    public static GuiComponent[] getTickObfuscationState() {
        return obfuscationState;
    }
}
