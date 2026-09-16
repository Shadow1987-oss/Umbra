package gg.umbra.tools.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.umbra.tools.inventory.cleaner.InventoryFilterCondition;
import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionType;
import gg.umbra.wrapper.impl.ItemStack;

public class EmptyInventoryFilterCondition
implements InventoryFilterCondition<EmptyInventoryFilterCondition> {
    @Override
    public EmptyInventoryFilterCondition copy() {
        return new EmptyInventoryFilterCondition();
    }
    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public InventoryFilterConditionType getType() {
        return null;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        return false;
    }

}
