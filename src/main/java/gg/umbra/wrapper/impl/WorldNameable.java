package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class WorldNameable
extends Wrapper {
    public ITextComponent getDisplayName() {
        return new ITextComponent(WorldNameable.umbraInstance.getMappingsMapperCompat().Dm.A(this.I));
    }

    public boolean hasCustomName() {
        return WorldNameable.umbraInstance.getMappingsMapperCompat().Dm.V(this.I);
    }

    public String getName() {
        if (ForgeVersion.MC_1_16_5.d()) {
            ITextComponent iTextComponent = new ITextComponent(WorldNameable.umbraInstance.getMappingsMapperCompat().Dm.s(this.I));
            return iTextComponent.getFormattedText();
        }
        return WorldNameable.umbraInstance.getMappingsMapperCompat().Dm.L(this.I);
    }

    public WorldNameable(Object object) {
        super(object);
    }
}
