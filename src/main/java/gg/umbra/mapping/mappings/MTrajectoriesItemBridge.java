package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MTrajectoriesItemBridge
extends Mapping {
    private final MappingMethod isChargedMethod;

    public MTrajectoriesItemBridge() {
        super(MappedClasses.YA);
        this.isChargedMethod = this.registerStaticMethod("isCharged", true, Boolean.TYPE, new Class[]{MappedClasses.VK});
    }

    public boolean isCharged(Object itemStack) {
        return this.isChargedMethod.invokeBoolean(null, itemStack);
    }
}

