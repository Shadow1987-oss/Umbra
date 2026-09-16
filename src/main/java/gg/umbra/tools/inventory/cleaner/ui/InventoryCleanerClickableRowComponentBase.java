package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.utils.render.GuiRenderPrimitives;
import org.jetbrains.annotations.Nullable;

public class InventoryCleanerClickableRowComponentBase
extends GuiComponent {
    @Nullable
    private GuiClickListener clickListener;


    @Override
    public void H() {
        if (this.w$src$Z$e457mb()) {
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), InventoryCleanerClickableRowComponentBase.J.l);
        }
    }

    public InventoryCleanerClickableRowComponentBase() {
    }

    public InventoryCleanerClickableRowComponentBase(@Nullable GuiClickListener guiClickListener) {
        this.clickListener = guiClickListener;
    }

    public void K(@Nullable GuiClickListener guiClickListener) {
        this.clickListener = guiClickListener;
    }

    @Nullable
    public GuiClickListener O$src$Lgg_umbra_ui_click_component_GuiClickListener_$729v32() {
        return this.clickListener;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        GuiClickListener guiClickListener = this.clickListener;
        if (guiClickListener != null) {
            guiClickListener.onPrimaryClick();
        }
    }
}

