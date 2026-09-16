package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MItems;
import gg.umbra.wrapper.Wrapper;

public class Items
extends Wrapper {
    public static Items perspective() {
        return new Items(MItems.getPerspective(Items.umbraInstance.getMappingsMapperCompat().projectionType));
    }

    public static Items orthographic() {
        return new Items(MItems.getOrthographic(Items.umbraInstance.getMappingsMapperCompat().projectionType));
    }

    public Items(Object handle) {
        super(handle);
    }
}
