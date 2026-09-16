package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MFoodStats;
import gg.umbra.wrapper.Wrapper;

public class FoodStats
extends Wrapper {
    public int getFoodLevel() {
        return MFoodStats.getFoodLevel(FoodStats.umbraInstance.getMappingsMapperCompat().DJ, this.I);
    }

    public FoodStats(Object wrappedObject) {
        super(wrappedObject);
    }
}
