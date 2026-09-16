package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.ClassInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.ClassInventoryItemMatcherBuilder;
import gg.umbra.tools.inventory.cleaner.CompositeInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderFoundation;
import gg.umbra.tools.inventory.cleaner.StringInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.StringInventoryItemMatcherBuilder;

public class InventoryItemMatcherBuilder
extends InventoryItemMatcherBuilderBase<InventoryItemMatcherBuilder> {
    public InventoryItemMatcherBuilderFoundation composite() {
        return CompositeInventoryItemMatcher.builderFrom(this);
    }

    public StringInventoryItemMatcherBuilder stringMatcher() {
        return StringInventoryItemMatcher.builderFrom(this);
    }

    public ClassInventoryItemMatcherBuilder classMatcher() {
        return ClassInventoryItemMatcher.builderFrom(this);
    }
}
