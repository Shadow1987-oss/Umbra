package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderFoundation;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherGroup;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;

public class ArmorInventoryItemMatchers {
    public static final InventoryItemMatcher ANY_ARMOR;

    static {
        String[] labels = new String[]{"any-armor", "Any armor", "armor_item", "Any type of armor"};
        ANY_ARMOR = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[0])).withName(labels[1])).withIconName(labels[2])).withDescription(labels[3])).withGroup(InventoryItemMatcherGroup.ARMOR)).withPredicate(ArmorInventoryItemMatchers::isArmorItem).build();
    }

    static void initialize() {
        InventoryItemMatcherRegistry.register(ANY_ARMOR);
    }

    private static boolean isArmorItem(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.R(item);
    }
}

