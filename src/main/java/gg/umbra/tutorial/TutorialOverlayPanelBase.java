package gg.umbra.tutorial;

import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.frame.FrameComponent;
import gg.umbra.wrapper.impl.Minecraft;

public abstract class TutorialOverlayPanelBase
extends FrameComponent {
    @Override
    public void v() {
    }


    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void V() {
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void Y() {
        if (this.getParentFrameComponent() instanceof Frame) {
            this.getParentFrameComponent().K((double)(Minecraft.J() / 4) - this.x() / 2.0);
            this.getParentFrameComponent().S((double)(Minecraft.h() / 4) - this.C() / 2.0);
        }
    }

    @Override
    public double x() {
        return 20.0;
    }
}

