package gg.umbra.tools.inventory.cleaner;

import gg.umbra.Umbra;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.tools.inventory.cleaner.AbstractInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.umbra.tools.inventory.cleaner.StringInventoryItemMatcherBuilder;
import gg.umbra.tools.inventory.cleaner.StringMatchOperator;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.Map;

public class StringInventoryItemMatcher
extends AbstractInventoryItemMatcher {
    private final Map<String, StringMatchOperator> matchOperators;

    @Override
    public boolean matches(ItemStack itemStack, Item item) {
        if (this.matchOperators.isEmpty()) {
            return false;
        }
        ItemMappingEntry mappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack);
        if (mappingEntry == null) {
            return false;
        }
        for (Map.Entry<String, StringMatchOperator> entry : this.matchOperators.entrySet()) {
            String pattern = entry.getKey();
            StringMatchOperator operator = entry.getValue();
            if (!operator.getPredicate().test(mappingEntry.getModernId(), pattern)) continue;
            return true;
        }
        return false;
    }

    public static StringInventoryItemMatcherBuilder builderFrom(InventoryItemMatcherBuilderBase<?> baseBuilder) {
        return new StringInventoryItemMatcherBuilder(baseBuilder, null);
    }


    public StringInventoryItemMatcher(StringInventoryItemMatcherBuilder builder) {
        super(builder);
        this.matchOperators = builder.getOperatorsByPattern();
    }
}

