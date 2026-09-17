package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.Umbra;
import gg.umbra.visual.hud.PingHudModule;
import gg.umbra.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.wrapper.impl.Minecraft;
import java.awt.Color;

public class PingHudFrame
extends HudModuleConfigFrameBase {
    private static final int SHADOW_COLOR_ARGB = 0x80000000;


    @Override
    public double A() {
        return 50.0;
    }

    @Override
    public void renderHudContent() {
        SmoothFontRenderer smoothFontRenderer = Umbra.INSTANCE.getFontManager().W(1.2, false);
        int ping = PingHudModule.getPingMillis();
        String pingText = (ping < 0 ? "--" : String.valueOf(ping)) + " ms";
        float textX = (int)(this.G$src$D$1b2f02a() + this.A() / 2.0
                - smoothFontRenderer.N(pingText) / 2.0);
        float textY = (int)(this.n() + this.L() / 2.0
                - smoothFontRenderer.d(pingText) / 2.0);
        if (this.shouldRenderHudBackground()) {
            smoothFontRenderer.d(pingText, textX, textY, this.getEditorForegroundColor());
        } else {
            smoothFontRenderer.T(pingText, textX, textY,
                    this.getEditorForegroundColor(),
                    this.applyDefaultEditorAlpha(new Color(SHADOW_COLOR_ARGB, true)));
        }
    }

    public PingHudFrame() {
        super(PingHudModule.class);
    }

    @Override
    public String getName() {
        return "PingFrame";
    }

    @Override
    public double L() {
        return 20.0;
    }
}
