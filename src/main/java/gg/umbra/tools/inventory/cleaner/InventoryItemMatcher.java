package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.InventoryItemCategory;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatchContext;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilder;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherGroup;
import gg.umbra.unmap.INamed;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import java.util.Comparator;
import gg.umbra.tools.inventory.cleaner.DescribedOption;
import org.jetbrains.annotations.Nullable;

public interface InventoryItemMatcher
extends INamed,
DescribedOption {
    default public boolean matches(ItemStack itemStack) {
        return this.matches(itemStack, itemStack.getItem());
    }

    default public InventoryItemCategory getCategory() {
        return null;
    }

    @Nullable
    public String getIconName();

    @Nullable
    public Comparator<InventoryItemMatchContext> getComparator();

    public static InventoryItemMatcherBuilder builder() {
        return new InventoryItemMatcherBuilder();
    }

    public String getId();

    public InventoryItemMatcherGroup getGroup();

    public boolean matches(ItemStack itemStack, Item item);

    default public void setCategory(InventoryItemCategory category) {
    }
}
