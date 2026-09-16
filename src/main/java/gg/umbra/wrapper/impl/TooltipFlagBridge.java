package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.wrapper.Wrapper;

public class TooltipFlagBridge
extends Wrapper {

    public TooltipFlagBridge(Object handle) {
        super(handle);
    }

    public static TooltipFlagBridge searchTab() {
        if (ForgeVersion.MC_1_20_6.d()) {
            Umbra.notifyNativeStackTrace();
        }
        return new TooltipFlagBridge(TooltipFlagBridge.umbraInstance.getMappings().creativeTabsSearch.getSearchTab());
    }
}

