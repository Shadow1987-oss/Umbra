package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryItemCategoryBuilder;
import gg.umbra.tools.inventory.cleaner.MatcherBackedInventoryItemCategoryBuilder;
import gg.umbra.tools.inventory.cleaner.StackSizeInventoryItemCategoryBuilder;

public class DefaultInventoryItemCategoryBuilder
extends InventoryItemCategoryBuilder {
    public StackSizeInventoryItemCategoryBuilder stackSize() {
        return new StackSizeInventoryItemCategoryBuilder();
    }

    public MatcherBackedInventoryItemCategoryBuilder matcherBacked() {
        return new MatcherBackedInventoryItemCategoryBuilder();
    }
}
