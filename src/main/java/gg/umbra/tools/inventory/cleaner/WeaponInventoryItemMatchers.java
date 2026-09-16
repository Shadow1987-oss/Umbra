package gg.umbra.tools.inventory.cleaner;

import gg.umbra.config.ClientSettings;
import gg.umbra.tools.inventory.cleaner.CompositeInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatchContext;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderFoundation;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherGroup;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.Comparator;

public class WeaponInventoryItemMatchers {
    public static final CompositeInventoryItemMatcher AXES;
    public static final CompositeInventoryItemMatcher ANY_WEAPON;
    public static final CompositeInventoryItemMatcher SWORDS;

    public static void initialize() {
        InventoryItemMatcherRegistry.register(ANY_WEAPON);
        InventoryItemMatcherRegistry.register(SWORDS);
        InventoryItemMatcherRegistry.register(AXES);
    }

    private static boolean matchesAnyWeapon(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.h(item) || ItemStackScoreUtil.T(item);
    }

    private static double axeSortScore(InventoryItemMatchContext inventoryItemMatchContext) {
        return ClientSettings.getWeaponDamageScore(inventoryItemMatchContext.getItemStack());
    }

    private static boolean matchesAxe(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.T(item);
    }

    private static double swordSortScore(InventoryItemMatchContext inventoryItemMatchContext) {
        return ClientSettings.getWeaponDamageScore(inventoryItemMatchContext.getItemStack());
    }

    static {
        String[] labels = new String[]{"weapons", "axe-weapon", "swords", "weapons", "Axe", "axe-weapon", "sword-hover@2x", "Any Weapon", "Any type of axe", "Sword", "Any type of weapon (sword or axe)", "Any type of sword"};
        ANY_WEAPON = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[3])).withName(labels[7])).withDescription(labels[10])).withIconName(labels[0])).withGroup(InventoryItemMatcherGroup.WEAPONS)).withPredicate(WeaponInventoryItemMatchers::matchesAnyWeapon).withComparator(Comparator.comparingDouble(WeaponInventoryItemMatchers::anyWeaponSortScore))).build();
        SWORDS = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[2])).withName(labels[9])).withDescription(labels[11])).withIconName(labels[6])).withGroup(InventoryItemMatcherGroup.WEAPONS)).withPredicate(WeaponInventoryItemMatchers::matchesSword).withComparator(Comparator.comparingDouble(WeaponInventoryItemMatchers::swordSortScore))).build();
        AXES = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[1])).withName(labels[4])).withDescription(labels[8])).withIconName(labels[5])).withGroup(InventoryItemMatcherGroup.WEAPONS)).withPredicate(WeaponInventoryItemMatchers::matchesAxe).withComparator(Comparator.comparingDouble(WeaponInventoryItemMatchers::axeSortScore))).build();
    }

    private static boolean matchesSword(ItemStack itemStack, Item item) {
        return ItemStackScoreUtil.h(item);
    }

    private static double anyWeaponSortScore(InventoryItemMatchContext inventoryItemMatchContext) {
        return ClientSettings.getWeaponDamageScore(inventoryItemMatchContext.getItemStack());
    }

}

