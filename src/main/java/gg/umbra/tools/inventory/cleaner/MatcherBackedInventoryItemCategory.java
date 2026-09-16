package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.AbstractInventoryItemCategory;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.ItemFilterSelection;
import gg.umbra.tools.inventory.cleaner.MatcherBackedInventoryItemCategoryBuilder;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;

public class MatcherBackedInventoryItemCategory
extends AbstractInventoryItemCategory {
    @Override
    public boolean isCompatible(ItemFilterSelection selection) {
        InventoryItemMatcher selectedMatcher = selection.getMatcher();
        ItemStack itemStack = selection.getItemStack();
        if (this.getMatchers().isEmpty()) {
            return true;
        }
        if (selectedMatcher != null && !this.getMatchers().contains(selectedMatcher)) {
            return false;
        }
        if (itemStack == null) {
            return selectedMatcher != null;
        }
        Item item = itemStack.getItem();
        return this.getMatchers().stream().anyMatch(matcher -> matcher.matches(itemStack, item));
    }


    public MatcherBackedInventoryItemCategory(MatcherBackedInventoryItemCategoryBuilder matcherBackedInventoryItemCategoryBuilder) {
        super(matcherBackedInventoryItemCategoryBuilder);
    }
}
