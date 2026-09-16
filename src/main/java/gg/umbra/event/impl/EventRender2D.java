package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.settings.ClientSettings;
import gg.umbra.runtime.NativeBridge;
import gg.umbra.ui.click.GuiScreenNativeCallbackBridge;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GlStateManager;
import gg.umbra.wrapper.impl.Minecraft;

public class EventRender2D
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private static long lastBadlionGuiTickNanos;
    private static int displayHeight;
    private static int displayWidth;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public static void create() {
        if (ForgeVersion.MC_1_17.d() && Minecraft.thePlayer().isNull()) {
            return;
        }
        displayWidth = Minecraft.J();
        displayHeight = Minecraft.h();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GuiRenderPrimitives.o(displayWidth, displayHeight);
        ClientSettings clientSettings = Umbra.INSTANCE.getHackManager()
                .getMod(ClientSettings.class);
        clientSettings.renderHudOverlay();
        EventRender2D eventRender2D = new EventRender2D();
        eventRender2D.fire();
        GuiRenderPrimitives.L(displayWidth, displayHeight);
        if (NativeBridge.isBadlion189Runtime()) {
            long now = System.nanoTime();
            if (!clientSettings.isInputEnabled()
                    && now - lastBadlionGuiTickNanos >= 50000000L) {
                lastBadlionGuiTickNanos = now;
                clientSettings.onTick();
            }
            GuiScreenNativeCallbackBridge.drawScreen(
                    null, 0, 0, 0.0f);
        }
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }
}

