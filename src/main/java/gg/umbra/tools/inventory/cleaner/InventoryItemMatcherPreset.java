package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.AbstractInventoryFilterPreset;
import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionGroup;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherPresetBuilder;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryItemMatcherPreset
extends AbstractInventoryFilterPreset {
    private final List<InventoryFilterConditionGroup> conditionGroups;
    private final List<InventoryItemMatcher> matchers;

    InventoryItemMatcherPreset(String name, List<InventoryFilterConditionGroup> conditionGroups, List<InventoryItemMatcher> matchers) {
        super(name);
        this.conditionGroups = conditionGroups;
        this.matchers = matchers;
    }

    @Override
    public List<InventoryFilterConditionGroup> getConditionGroups() {
        return this.conditionGroups;
    }

    public static InventoryItemMatcherPresetBuilder builder() {
        return new InventoryItemMatcherPresetBuilder();
    }

    public @UnmodifiableView List<InventoryItemMatcher> getMatchers() {
        return this.matchers;
    }
}
