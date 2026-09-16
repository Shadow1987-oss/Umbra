package gg.umbra.tools.inventory;

import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerFrame;
import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerSearchCloseClickHandler;
import gg.umbra.tools.inventory.HotbarSlotRuleSearchInputKeyTypedListener;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.LabeledTextInputComponent;
import gg.umbra.ui.click.component.gui.TextButton;

public class HotbarSlotRuleItemSearchComponent
extends GuiComponent {
    private TextButton saveExitButton;
    private LabeledTextInputComponent searchInput = new LabeledTextInputComponent("BlockFinder Item Name");

    @Override
    public void F() {
    }

    @Override
    public void I() {
    }

    @Override
    public double x() {
        return 220.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public static LabeledTextInputComponent g(HotbarSlotRuleItemSearchComponent hotbarSlotRuleItemSearchComponent) {
        return hotbarSlotRuleItemSearchComponent.searchInput;
    }

    public HotbarSlotRuleItemSearchComponent(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.saveExitButton = new TextButton("Save & Exit", HotbarSlotRuleItemSearchComponent.J.B);
        this.searchInput.addKeyTypedListener(new HotbarSlotRuleSearchInputKeyTypedListener(this, hotbarSlotRuleItemPickerFrame));
        this.saveExitButton.addClickListener(new HotbarSlotRuleItemPickerSearchCloseClickHandler(this));
        this.addChildren(this.searchInput, this.saveExitButton);
    }

    @Override
    public void H() {
        double unit = 27.5;
        this.searchInput.K(this.G$src$D$1b2f02a());
        this.searchInput.S(this.n() + 20.0 - 5.0 - 2.5);
        this.searchInput.o(unit * 6.0 + 5.0);
        this.saveExitButton.o(unit * 2.0);
        this.saveExitButton.Y(15.0);
        this.saveExitButton.K(this.G$src$D$1b2f02a() + this.searchInput.A());
        this.saveExitButton.S(this.n() + 20.0 - 5.0);
    }

    @Override
    public void u() {
    }

    @Override
    public double C() {
        return 40.0;
    }
}

