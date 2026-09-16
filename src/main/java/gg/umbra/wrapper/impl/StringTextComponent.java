package gg.umbra.wrapper.impl;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.Wrapper;

public class StringTextComponent
extends Wrapper {

    public StringTextComponent(Object componentHandle) {
        super(componentHandle);
    }

    public TextComponentTranslation getItemStackRenderState() {
        Object renderStateHandle = StringTextComponent.umbraInstance.getMappingsMapperCompat().stringTextComponentBridge
                .getItemStackRenderState(this.I);
        return new TextComponentTranslation(renderStateHandle);
    }

    public String getName() {
        if (MappedClasses.DE != null && ForgeVersion.MC_26_1.d()) {
            return "";
        }
        return StringTextComponent.umbraInstance.getMappingsMapperCompat().stringTextComponentBridge.getName(this.I);
    }
}

