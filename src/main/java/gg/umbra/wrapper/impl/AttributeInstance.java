package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MIAttributeInstance;
import gg.umbra.wrapper.Wrapper;

import java.util.Collection;
import java.util.UUID;

public class AttributeInstance
extends Wrapper {
    public AttributeInstance(Object object) {
        super(object);
    }

    public AttributeModifier getModifier(UUID uUID) {
        return new AttributeModifier(MIAttributeInstance.x(AttributeInstance.umbraInstance.getMappings().Rv, this.I, uUID));
    }

    public void applyModifier(AttributeModifier attributeModifier) {
        MIAttributeInstance.b(AttributeInstance.umbraInstance.getMappings().Rv, this.I, attributeModifier.getObject());
    }


    public Collection I() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return MIAttributeInstance.d(AttributeInstance.umbraInstance.getMappings().Rv, this.I);
        }
        return MIAttributeInstance.h(AttributeInstance.umbraInstance.getMappings().Rv, this.I);
    }

    public double W() {
        return MIAttributeInstance.I(AttributeInstance.umbraInstance.getMappings().Rv, this.I);
    }

    public void I(double d) {
        MIAttributeInstance.W(AttributeInstance.umbraInstance.getMappings().Rv, this.I, d);
    }

    public void J() {
        MIAttributeInstance.V(AttributeInstance.umbraInstance.getMappings().Rv, this.I);
    }
}

