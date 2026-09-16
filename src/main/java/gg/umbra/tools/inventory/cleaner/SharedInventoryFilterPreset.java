package gg.umbra.tools.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionGroup;
import gg.umbra.tools.inventory.cleaner.InventoryFilterPreset;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class SharedInventoryFilterPreset
extends InventoryFilterPreset {
    public SharedInventoryFilterPreset(@Nullable UUID id, String name) {
        super(id, name);
    }

    public SharedInventoryFilterPreset copy() {
        SharedInventoryFilterPreset copy = new SharedInventoryFilterPreset(this.getId(), this.getName());
        for (InventoryFilterConditionGroup conditionGroup : this.getConditionGroups()) {
            copy.addConditionGroup(conditionGroup.copy());
        }
        return copy;
    }

    public SharedInventoryFilterPreset(JsonObject jsonObject) {
        super(jsonObject);
    }

    public SharedInventoryFilterPreset(InventoryFilterPreset preset) {
        super(preset.toJson());
        this.id = UUID.randomUUID();
    }
}

