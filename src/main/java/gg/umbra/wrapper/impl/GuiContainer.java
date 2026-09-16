package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MGuiContainer;

public class GuiContainer
extends GuiScreen {
    public static ResourceLocation m$src$Lgg_umbra_wrapper_impl_ResourceLocation_$1fc62cj() {
        return new ResourceLocation(GuiContainer.umbraInstance.getMappings().hK.v());
    }

    public Slot P() {
        return new Slot(MGuiContainer.y(GuiContainer.umbraInstance.getMappings().hK, this.I));
    }

    public int v() {
        return MGuiContainer.G(GuiContainer.umbraInstance.getMappings().hK, this.I);
    }

    public int p() {
        return MGuiContainer.B(GuiContainer.umbraInstance.getMappings().hK, this.I);
    }

    public int x() {
        return MGuiContainer.i(GuiContainer.umbraInstance.getMappings().hK, this.I);
    }

    public Slot getSlotAtPosition(int n, int n2) {
        return new Slot(MGuiContainer.a(GuiContainer.umbraInstance.getMappings().hK, this.I, n, n2));
    }

    public int b() {
        return MGuiContainer.e(GuiContainer.umbraInstance.getMappings().hK, this.I);
    }

    public GuiContainer(Object object) {
        super(object);
    }

    public Container getInventorySlots() {
        return new Container(GuiContainer.umbraInstance.getMappings().hK.y(this.I));
    }
}

