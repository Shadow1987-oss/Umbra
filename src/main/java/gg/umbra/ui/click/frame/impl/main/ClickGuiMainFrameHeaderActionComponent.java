package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.utils.render.ImageRenderer;
import java.awt.Color;

public class ClickGuiMainFrameHeaderActionComponent
extends GuiComponent {
    private float a = 8.0f;

    public float a$src$F$1db460d() {
        return this.a;
    }

    public ClickGuiMainFrameHeaderActionComponent(float f) {
        this.a = f;
    }

    public void y(float f) {
        this.a = f;
    }

    public ClickGuiMainFrameHeaderActionComponent() {
    }

    @Override
    public void H() {
        float f = (float)ImageRenderer.getImageWidth("umbralogo") / this.a;
        float f2 = (float)ImageRenderer.getImageHeight("umbralogo") / this.a;
        ImageRenderer.drawImage(Color.WHITE, (float)this.G$src$D$1b2f02a(), (float)this.n(), "umbralogo", f, f2, false);
    }
}

