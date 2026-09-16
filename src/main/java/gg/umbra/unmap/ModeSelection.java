package gg.umbra.unmap;

import gg.umbra.unmap.INamed;
import gg.umbra.unmap.PropertyContainer;
import gg.umbra.value.OptionSetting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModeSelection
extends PropertyContainer
implements INamed {
    private final String name;
    private static int[] legacyState;
    private OptionSetting owningMode;
    public static HashMap<OptionSetting, List<ModeSelection>> selectionsByMode;

    public ModeSelection(String name) {
        this.name = name;
    }

    public String getSectionSignStrippedName() {
        return this.getName().replace("\u00a7", "");
    }

    @Override
    public String getName() {
        return this.name;
    }


    public String getSerializedName() {
        return this.toString();
    }

    public OptionSetting getMode() {
        return this.owningMode;
    }

    public void attachToMode(OptionSetting modeValue) {
        this.owningMode = modeValue;
        if (!selectionsByMode.containsKey(modeValue)) {
            selectionsByMode.put(modeValue, new ArrayList());
        }
        selectionsByMode.get(modeValue).add(this);
    }

    public static void setLegacyState(int[] state) {
        legacyState = state;
    }

    public String toString() {
        return this.getSectionSignStrippedName();
    }

    public static ModeSelection findBySerializedName(OptionSetting modeValue, String name) {
        List<ModeSelection> selections = selectionsByMode.get(modeValue);
        for (ModeSelection selection : selections) {
            if (!selection.getSerializedName().equalsIgnoreCase(name)) continue;
            return selection;
        }
        return null;
    }

    public boolean isSpecialSelection() {
        return false;
    }

    public static int[] getLegacyState() {
        return legacyState;
    }

    static {
        selectionsByMode = new HashMap();
        ModeSelection.setLegacyState(null);
    }
}

