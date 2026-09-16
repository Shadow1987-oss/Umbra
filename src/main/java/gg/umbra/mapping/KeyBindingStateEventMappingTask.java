package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventKeyBindingState;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.ForgeVersion;

public class KeyBindingStateEventMappingTask
extends JavassistMappingTask {
    private static final String c;

    public KeyBindingStateEventMappingTask() {
        super(MappedClasses.DR);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.O(Umbra.INSTANCE.getMappings().hJ.s, EventKeyBindingState.class, c, "");
            return;
        }
    }


    static {
        try {
            c = "$0, $1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

