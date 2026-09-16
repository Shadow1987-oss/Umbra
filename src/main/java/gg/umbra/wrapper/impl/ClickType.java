package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MClickType;
import gg.umbra.wrapper.Wrapper;

public class ClickType
extends Wrapper {
    public static ClickType[] VALUES = new ClickType[]{ClickType.pickup(), ClickType.quickMove(), ClickType.swap(), ClickType.cloneStack(), ClickType.throwStack(), ClickType.quickCraft(), ClickType.pickupAll()};

    public static ClickType quickCraft() {
        return new ClickType(MClickType.getQuickCraft(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }

    public ClickType(Object object) {
        super(object);
    }

    public static ClickType pickup() {
        return new ClickType(MClickType.getPickup(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType cloneStack() {
        return new ClickType(MClickType.getClone(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType quickMove() {
        return new ClickType(MClickType.getQuickMove(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType pickupAll() {
        return new ClickType(MClickType.getPickupAll(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType throwStack() {
        return new ClickType(MClickType.getThrow(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }

    public static ClickType swap() {
        return new ClickType(MClickType.getSwap(ClickType.umbraInstance.getMappingsMapperCompat().clickType));
    }
}
