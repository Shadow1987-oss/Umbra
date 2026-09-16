package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class EnumCreatureAttribute
extends Wrapper {

    public static EnumCreatureAttribute undefined() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return null;
        }
        Object attributeHandle = EnumCreatureAttribute.umbraInstance.getMappingsMapperCompat()
                .enumCreatureAttributeBridge.getUndefined();
        return new EnumCreatureAttribute(attributeHandle);
    }

    public EnumCreatureAttribute(Object attributeHandle) {
        super(attributeHandle);
    }
}

