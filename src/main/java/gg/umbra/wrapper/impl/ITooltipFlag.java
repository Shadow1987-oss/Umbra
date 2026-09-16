package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ITooltipFlag
extends Wrapper {
    public static ITooltipFlag water() {
        return new ITooltipFlag(ITooltipFlag.umbraInstance.getMappingsMapperCompat().clipContextFluidMode.getWater());
    }

    public ITooltipFlag(Object handle) {
        super(handle);
    }
}
