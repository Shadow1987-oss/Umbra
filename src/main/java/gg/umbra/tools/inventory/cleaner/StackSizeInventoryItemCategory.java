package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.AbstractInventoryItemCategory;
import gg.umbra.tools.inventory.cleaner.ComparisonOperator;
import gg.umbra.tools.inventory.cleaner.ItemFilterSelection;
import gg.umbra.tools.inventory.cleaner.StackSizeInventoryItemCategoryBuilder;
import gg.umbra.wrapper.impl.ItemStack;

public class StackSizeInventoryItemCategory
extends AbstractInventoryItemCategory {
    private final ComparisonOperator operator;
    private final int stackSize;


    @Override
    public boolean isCompatible(ItemFilterSelection cn_22) {
        ItemStack itemStack = cn_22.getItemStack();
        if (itemStack == null) {
            return false;
        }
        return this.operator.compare(itemStack.P(), this.stackSize);
    }

    StackSizeInventoryItemCategory(StackSizeInventoryItemCategoryBuilder stackSizeInventoryItemCategoryBuilder) {
        super(stackSizeInventoryItemCategoryBuilder);
        this.stackSize = stackSizeInventoryItemCategoryBuilder.getStackSize();
        this.operator = stackSizeInventoryItemCategoryBuilder.getOperator();
    }
}

