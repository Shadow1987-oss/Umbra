package gg.umbra.tools.inventory;

import gg.umbra.input.KeyBindingHelper;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.tools.mlg.MLGImpactState;
import gg.umbra.wrapper.impl.Container;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Slot;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class ItemStackActionPredicate {
    @Nullable
    public static Slot findSlotByItemClass(Class<?> itemClass, MLGImpactState searchRange) {
        return ItemStackActionPredicate.findSlot(slot -> isItemClass(slot, itemClass), searchRange);
    }

    private static boolean isItemClass(Slot slot, Class<?> itemClass) {
        return slot.isNotNull() && slot.getStack().isNotNull() && slot.getStack().getItem().isInstance(itemClass);
    }

    @Nullable
    public static Slot findSlot(Predicate<Slot> predicate, MLGImpactState searchRange) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull()) {
            return null;
        }
        Container container = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm();
        if (container.isNull()) {
            return null;
        }
        return ItemStackActionPredicate.findSlot(container, predicate, searchRange.getFirstSlot(), searchRange.getLastSlot());
    }

    public static boolean isInventoryScreenOpen() {
        return Minecraft.currentScreen().isInstance(MappedClasses.YS);
    }

    public static boolean openInventory() {
        if (!ItemStackActionPredicate.isInventoryScreenOpen()) {
            KeyBindingHelper.updateKeyBinding(Minecraft.gameSettings().j(), true, true);
            KeyBindingHelper.updateKeyBinding(Minecraft.gameSettings().j(), false, false);
            return true;
        }
        return false;
    }


    public static boolean isAnyScreenOpen() {
        return Minecraft.currentScreen().isNotNull();
    }

    @Nullable
    public static Slot findSlot(Container container, Predicate<Slot> predicate, int firstSlot, int lastSlot) {
        for (Slot slot : container.getInventorySlots()) {
            int slotIndex = slot.getSlotNumber();
            if (slotIndex < firstSlot || slotIndex > lastSlot || !predicate.test(slot)) continue;
            return slot;
        }
        return null;
    }

    public static boolean closeCurrentScreen() {
        if (ItemStackActionPredicate.isAnyScreenOpen()) {
            if (Minecraft.currentScreen().isInstance(MappedClasses.D2)) {
                return false;
            }
            Minecraft.thePlayer().Z$src$V$1ie832h();
            return true;
        }
        return false;
    }
}

