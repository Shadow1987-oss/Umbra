package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.ArmorInventoryItemMatchers;
import gg.umbra.tools.inventory.cleaner.BlockInventoryItemMatchers;
import gg.umbra.tools.inventory.cleaner.EmptySlotInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.FoodInventoryItemMatchers;
import gg.umbra.tools.inventory.cleaner.HiddenInventoryItemMatchers;
import gg.umbra.tools.inventory.cleaner.InventoryItemCategoryRegistry;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherGroup;
import gg.umbra.tools.inventory.cleaner.ToolInventoryItemMatchers;
import gg.umbra.tools.inventory.cleaner.WeaponInventoryItemMatchers;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryItemMatcherRegistry {
    private static final Map<String, InventoryItemMatcher> matchersByName = new LinkedHashMap<String, InventoryItemMatcher>();
    private static final Map<InventoryItemMatcherGroup, List<InventoryItemMatcher>> matchersByGroup = new LinkedHashMap<InventoryItemMatcherGroup, List<InventoryItemMatcher>>();

    public static @UnmodifiableView Collection<InventoryItemMatcher> getAll() {
        return matchersByName.values();
    }

    private static List createGroupList(InventoryItemMatcherGroup group) {
        return new ArrayList();
    }

    @Nullable
    public static InventoryItemMatcher getByName(String name) {
        return matchersByName.get(name);
    }

    public static void register(InventoryItemMatcher matcher) {
        matchersByName.put(matcher.getId(), matcher);
        matchersByGroup.computeIfAbsent(matcher.getGroup(), InventoryItemMatcherRegistry::createGroupList).add(matcher);
    }

    public static @UnmodifiableView List<InventoryItemMatcher> getByGroup(InventoryItemMatcherGroup group) {
        return matchersByGroup.get(group);
    }

    @Nullable
    public static InventoryItemMatcher findBestMatch(ItemStack itemStack) {
        if (itemStack.isNull()) {
            return EmptySlotInventoryItemMatcher.EMPTY_SLOT;
        }
        Item item = itemStack.getItem();
        if (item.isNull()) {
            return EmptySlotInventoryItemMatcher.EMPTY_SLOT;
        }
        ArrayList<InventoryItemMatcher> matchingMatchers = new ArrayList<InventoryItemMatcher>();
        for (InventoryItemMatcher matcher : matchersByName.values()) {
            if (!matcher.matches(itemStack, itemStack.getItem())) continue;
            matchingMatchers.add(matcher);
        }
        matchingMatchers.sort(InventoryItemMatcherRegistry::compareByPriority);
        Collections.reverse(matchingMatchers);
        return matchingMatchers.isEmpty() ? null : matchingMatchers.get(0);
    }

    private static int compareByPriority(InventoryItemMatcher firstMatcher, InventoryItemMatcher secondMatcher) {
        boolean hasPriorityFirst = firstMatcher.getComparator() != null;
        boolean hasPrioritySecond = secondMatcher.getComparator() != null;
        return Boolean.compare(hasPriorityFirst, hasPrioritySecond);
    }


    static {
        HiddenInventoryItemMatchers.initialize();
        WeaponInventoryItemMatchers.initialize();
        ToolInventoryItemMatchers.initialize();
        FoodInventoryItemMatchers.initialize();
        BlockInventoryItemMatchers.initialize();
        ArmorInventoryItemMatchers.initialize();
        InventoryItemCategoryRegistry.initialize();
    }
}

