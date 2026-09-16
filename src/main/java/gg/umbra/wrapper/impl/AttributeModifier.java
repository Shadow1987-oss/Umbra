package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MAttributeModifier;
import gg.umbra.wrapper.Wrapper;

import java.util.UUID;

public class AttributeModifier
extends Wrapper {
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(MAttributeModifier.getResourceLocation(AttributeModifier.umbraInstance.getMappings().hv, this.I));
    }

    public AttributeModifier(Object wrappedObject) {
        super(wrappedObject);
    }

    public double getAmount() {
        return AttributeModifier.umbraInstance.getMappings().hv.getAmount(this.I);
    }

    public UUID getId() {
        return MAttributeModifier.getUuid(AttributeModifier.umbraInstance.getMappings().hv, this.I);
    }
}
