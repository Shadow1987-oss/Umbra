package gg.umbra.tools.inventory.cleaner;

import gg.umbra.Umbra;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InventoryItemMatchContext {
    private final ItemMappingEntry mappingEntry;
    private final Item item;
    private final ItemStack itemStack;


    public Item getItem() {
        return this.item;
    }

    @Nullable
    public static InventoryItemMatchContext fromItemStack(ItemStack itemStack) {
        if (itemStack == null || itemStack.isNull()) {
            return null;
        }
        ItemMappingEntry itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack);
        return new InventoryItemMatchContext(itemMappingEntry, itemStack);
    }

    public InventoryItemMatchContext(ItemMappingEntry itemMappingEntry, ItemStack itemStack) {
        this(itemMappingEntry, itemStack, itemStack.getItem());
    }

    public ItemMappingEntry getMappingEntry() {
        return this.mappingEntry;
    }

    public InventoryItemMatchContext(ItemMappingEntry itemMappingEntry, ItemStack stack, Item resolvedItem) {
        this.mappingEntry = itemMappingEntry;
        this.itemStack = stack;
        this.item = resolvedItem;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}

