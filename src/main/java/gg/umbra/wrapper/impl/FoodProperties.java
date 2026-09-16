package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class FoodProperties
extends Wrapper {
    public FoodProperties(Object wrappedObject) {
        super(wrappedObject);
    }

    public boolean canAlwaysEat() {
        return FoodProperties.umbraInstance.getMappingsMapperCompat().foodProperties.canAlwaysEat(this.getObject());
    }

    public int getNutrition() {
        return FoodProperties.umbraInstance.getMappingsMapperCompat().foodProperties.getNutrition(this.getObject());
    }

    public float getSaturation() {
        return FoodProperties.umbraInstance.getMappingsMapperCompat().foodProperties.getSaturation(this.getObject());
    }
}
