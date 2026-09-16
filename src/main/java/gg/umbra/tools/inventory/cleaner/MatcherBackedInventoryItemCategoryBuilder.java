package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryItemCategoryBuilder;
import gg.umbra.tools.inventory.cleaner.MatcherBackedInventoryItemCategory;

public class MatcherBackedInventoryItemCategoryBuilder
extends InventoryItemCategoryBuilder<MatcherBackedInventoryItemCategoryBuilder> {
    public MatcherBackedInventoryItemCategory build() {
        return new MatcherBackedInventoryItemCategory(this);
    }
}

