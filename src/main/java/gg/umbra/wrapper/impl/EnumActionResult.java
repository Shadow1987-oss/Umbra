package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class EnumActionResult
extends Wrapper {
    public EnumActionResult(Object actionResultHandle) {
        super(actionResultHandle);
    }

    public static EnumActionResult pass() {
        return new EnumActionResult(EnumActionResult.umbraInstance.getMappingsMapperCompat().enumActionResult.getPass());
    }

    public static EnumActionResult fail() {
        return new EnumActionResult(EnumActionResult.umbraInstance.getMappingsMapperCompat().enumActionResult.getFail());
    }

    public static EnumActionResult success() {
        return new EnumActionResult(EnumActionResult.umbraInstance.getMappingsMapperCompat().enumActionResult.getSuccess());
    }
}
