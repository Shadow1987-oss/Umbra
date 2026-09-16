package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryFilterConditionGroup;
import gg.umbra.unmap.INamed;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface InventoryFilterPresetData
extends INamed {
    public @UnmodifiableView List<InventoryFilterConditionGroup> getConditionGroups();

    public boolean matches(ItemStack itemStack);

    @Override
    public String getName();
}
