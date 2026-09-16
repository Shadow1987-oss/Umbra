package gg.umbra.notification;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventRender2D;
import gg.umbra.ui.font.FontManager;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.wrapper.impl.Minecraft;
import java.awt.Color;

/**
 * Centered welcome overlay shown right after injection. Renders the client
 * name and the GUI keybind for a few seconds, then fades out and disappears
 * on its own, so new users know how to open the GUI without a permanent HUD
 * element.
 */
public class WelcomeOverlay
implements EventListener {
    private static final long DISPLAY_DURATION_MS = 5000L;
    private static final long FADE_DURATION_MS = 1000L;
    private static final double PANEL_WIDTH_PADDING = 48.0;
    private static final double PANEL_HEIGHT = 56.0;

    private static WelcomeOverlay instance;
    private String title;
    private String hint;
    private long shownAtMillis = -1L;

    public static WelcomeOverlay getInstance() {
        if (instance == null) {
            instance = new WelcomeOverlay();
        }
        return instance;
    }

    public void show(String title, String hint) {
        this.title = title;
        this.hint = hint;
        this.shownAtMillis = System.currentTimeMillis();
    }

    @Listen
    public void onRender2D(EventRender2D eventRender2D) {
        if (this.title == null || this.shownAtMillis < 0L) {
            return;
        }
        long elapsedMillis = System.currentTimeMillis() - this.shownAtMillis;
        if (elapsedMillis > DISPLAY_DURATION_MS) {
            this.title = null;
            this.hint = null;
            this.shownAtMillis = -1L;
            return;
        }
        FontManager fontManager = Umbra.INSTANCE.getFontManager();
        if (fontManager == null) {
            return;
        }
        int displayWidth = Minecraft.J();
        int displayHeight = Minecraft.h();
        double centerX = displayWidth / 2.0;
        double panelY = displayHeight / 2.0 - 40.0;
        float alpha = 1.0f;
        if (elapsedMillis > DISPLAY_DURATION_MS - FADE_DURATION_MS) {
            alpha = (float)(DISPLAY_DURATION_MS - elapsedMillis) / (float)FADE_DURATION_MS;
        }
        SmoothFontRenderer titleFont = fontManager.D(20, false);
        SmoothFontRenderer hintFont = fontManager.D(11, false);
        double panelWidth = Math.max(titleFont.N(this.title), hintFont.N(this.hint)) + PANEL_WIDTH_PADDING;
        GuiRenderPrimitives.C(centerX - panelWidth / 2.0, panelY, panelWidth, PANEL_HEIGHT, new Color(18, 18, 24, (int)(190.0f * alpha)));
        titleFont.f(this.title, centerX, panelY + 10.0, new Color(255, 255, 255, (int)(255.0f * alpha)));
        hintFont.f(this.hint, centerX, panelY + 32.0, new Color(170, 190, 255, (int)(255.0f * alpha)));
    }
}
