package gg.umbra.tools.inventory;

import gg.umbra.config.ClientSettings;
import gg.umbra.tools.AutoHotbar;
import gg.umbra.wrapper.impl.Slot;
import java.util.Comparator;

public class InventoryManagerPrimaryItemScoreComparator
implements Comparator<Slot> {
    @Override
    public int compare(Slot first, Slot second) {
        return this.comparePrimaryScore(first, second);
    }
    final AutoHotbar autoHotbar;

    public int comparePrimaryScore(Slot first, Slot second) {
        return Double.compare(ClientSettings.getWeaponDamageScore(first.getStack()), ClientSettings.getWeaponDamageScore(second.getStack()));
    }

    public InventoryManagerPrimaryItemScoreComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }
}
