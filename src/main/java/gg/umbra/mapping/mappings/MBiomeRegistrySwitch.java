package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;

public class MBiomeRegistrySwitch
extends Mapping {
    private static int[] controlFlowState;

    public MBiomeRegistrySwitch() {
        super(MappedClasses.FU);
    }

    public static void setBiomeRegistryControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public static int[] getBiomeRegistryControlFlowState() {
        return controlFlowState;
    }

    static {
        if (MBiomeRegistrySwitch.getBiomeRegistryControlFlowState() == null) {
            MBiomeRegistrySwitch.setBiomeRegistryControlFlowState(new int[5]);
        }
    }
}
