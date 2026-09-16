package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.AbstractInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemCategory;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderFoundation;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.function.BiPredicate;

public class CompositeInventoryItemMatcher
extends AbstractInventoryItemMatcher {
    private InventoryItemCategory category;
    private final BiPredicate<ItemStack, Item> matchPredicate;
    @Override
    public void setCategory(InventoryItemCategory category) {
        this.category = category;
    }

    public static InventoryItemMatcherBuilderFoundation builderFrom(InventoryItemMatcherBuilderBase<?> inventoryItemMatcherBuilderBase) {
        return new InventoryItemMatcherBuilderFoundation(inventoryItemMatcherBuilderBase, null);
    }

    @Override
    public InventoryItemCategory getCategory() {
        return this.category;
    }

    public CompositeInventoryItemMatcher(InventoryItemMatcherBuilderFoundation inventoryItemMatcherBuilderFoundation) {
        super(inventoryItemMatcherBuilderFoundation);
        this.matchPredicate = inventoryItemMatcherBuilderFoundation.getPredicate();
    }

    @Override
    public boolean matches(ItemStack itemStack, Item item) {
        return this.matchPredicate.test(itemStack, item);
    }

}

