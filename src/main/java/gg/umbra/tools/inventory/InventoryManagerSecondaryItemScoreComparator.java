package gg.umbra.tools.inventory;

import gg.umbra.config.ClientSettings;
import gg.umbra.tools.AutoHotbar;
import gg.umbra.wrapper.impl.Slot;
import java.util.Comparator;

public class InventoryManagerSecondaryItemScoreComparator
implements Comparator<Slot> {
    @Override
    public int compare(Slot first, Slot second) {
        return this.compareSecondaryScore(first, second);
    }
    final AutoHotbar autoHotbar;

    public InventoryManagerSecondaryItemScoreComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }

    public int compareSecondaryScore(Slot first, Slot second) {
        return Double.compare(ClientSettings.getHiddenItemScore(first.getStack()), ClientSettings.getHiddenItemScore(second.getStack()));
    }
}
