package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionGroup;
import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionGroupBuilder;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherPreset;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemMatcherPresetBuilder {
    private String name;
    private final List<InventoryItemMatcher> matchers;
    private final List<InventoryFilterConditionGroup> conditionGroups = new ArrayList<InventoryFilterConditionGroup>();

    public InventoryItemMatcherPreset build() {
        return new InventoryItemMatcherPreset(this.name, this.conditionGroups, this.matchers);
    }

    public InventoryItemMatcherPresetBuilder() {
        this.matchers = new ArrayList<InventoryItemMatcher>();
    }

    public InventoryItemMatcherPresetBuilder addMatcher(InventoryItemMatcher matcher) {
        this.matchers.add(matcher);
        return this;
    }

    public InventoryItemMatcherPresetBuilder addConditionGroup(InventoryFilterConditionGroup conditionGroup) {
        this.conditionGroups.add(conditionGroup);
        return this;
    }

    public InventoryItemMatcherPresetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public InventoryItemMatcherPresetBuilder addConditionGroup(InventoryFilterConditionGroupBuilder groupBuilder) {
        this.conditionGroups.add(groupBuilder.build());
        return this;
    }
}

