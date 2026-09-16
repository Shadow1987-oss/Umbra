package gg.umbra.ui.click.component.input;

import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.input.BindableInputComponent;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.unmap.Bendable;
import gg.umbra.value.BindValue;
import java.awt.Color;

public class BindValueRowComponent
extends GuiComponent {
    private BindableInputComponent bindInput;
    private boolean unusedState;
    private String label;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 20.0;
    }

    public BindValueRowComponent(BindValue bindValue) {
        this(bindValue.getName(), (Bendable)bindValue.getValue());
    }

    @Override
    public void H() {
        this.onDisable();
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.9);
        double labelHeight = fontRenderer.d(this.label);
        fontRenderer.d(this.label, this.G$src$D$1b2f02a() + 5.0, this.n() + this.L() / 2.0 - labelHeight / 2.0, BindValueRowComponent.J.Z);
        this.bindInput.getBindLabel().setMaxWidth(this.A() - 12.5);
        this.bindInput.K(this.G$src$D$1b2f02a() + this.A() - 5.0 - this.bindInput.A());
        this.bindInput.S(this.n() + 5.0);
    }

    @Override
    public void F() {
    }

    public BindValueRowComponent(String label, Bendable bendable, Color color) {
        this.label = label;
        this.bindInput = new BindableInputComponent(bendable, color);
        this.addChildren(this.bindInput);
    }

    @Override
    public void u() {
    }

    public BindValueRowComponent(String label, Bendable bendable) {
        this(label, bendable, null);
    }

    public BindableInputComponent getBindInput() {
        return this.bindInput;
    }

    @Override
    public double x() {
        return 110.0;
    }
}
