package gg.umbra.wrapper.impl;

public class ItemFood
extends Item {
    public float getSaturation() {
        return ItemFood.umbraInstance.getMappings().itemFood.getSaturation(this.I);
    }

    public ItemFood(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getNutrition() {
        return ItemFood.umbraInstance.getMappings().itemFood.getNutrition(this.I);
    }
}
