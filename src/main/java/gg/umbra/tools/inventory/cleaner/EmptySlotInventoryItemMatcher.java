package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.AbstractInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilder;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherGroup;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;

public class EmptySlotInventoryItemMatcher
extends AbstractInventoryItemMatcher {
    public static final EmptySlotInventoryItemMatcher EMPTY_SLOT = new EmptySlotInventoryItemMatcher();


    EmptySlotInventoryItemMatcher() {
        super((InventoryItemMatcherBuilderBase<?>)((InventoryItemMatcherBuilder)((InventoryItemMatcherBuilder)InventoryItemMatcher.builder().withName("Hand")).withDescription("No item")).withGroup(InventoryItemMatcherGroup.HIDDEN));
    }

    @Override
    public boolean matches(ItemStack itemStack, Item item) {
        return itemStack == null || itemStack.isNull() || item == null || item.isNull();
    }
}

