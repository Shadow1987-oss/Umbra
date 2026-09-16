package gg.umbra.tools.inventory.cleaner;

import gg.umbra.inventory.cleaner.InventoryMatcherMarker;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.function.BiPredicate;

public class InventoryItemMatcherBuilderFoundation
extends InventoryItemMatcherBuilderBase<InventoryItemMatcherBuilderFoundation> {
    private BiPredicate<ItemStack, Item> predicate;

    InventoryItemMatcherBuilderFoundation(InventoryItemMatcherBuilderBase base, InventoryMatcherMarker marker) {
        this(base);
    }

    public CompositeInventoryItemMatcher build() {
        return new CompositeInventoryItemMatcher(this);
    }

    public BiPredicate<ItemStack, Item> getPredicate() {
        return this.predicate;
    }

    public InventoryItemMatcherBuilderFoundation withPredicate(BiPredicate<ItemStack, Item> predicate) {
        this.predicate = predicate;
        return this;
    }

    private InventoryItemMatcherBuilderFoundation(InventoryItemMatcherBuilderBase<?> base) {
        super(base);
    }
}

