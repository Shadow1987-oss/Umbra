package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryFilterCondition;
import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionGroup;
import java.util.ArrayList;
import java.util.List;

public class InventoryFilterConditionGroupBuilder {
    private final List<InventoryFilterCondition<?>> conditions = new ArrayList();

    public InventoryFilterConditionGroupBuilder addCondition(InventoryFilterCondition<?> condition) {
        this.conditions.add(condition);
        return this;
    }

    public InventoryFilterConditionGroup build() {
        InventoryFilterConditionGroup group = new InventoryFilterConditionGroup();
        InventoryFilterConditionGroup.mutableConditions(group).addAll(this.conditions);
        return group;
    }
}

