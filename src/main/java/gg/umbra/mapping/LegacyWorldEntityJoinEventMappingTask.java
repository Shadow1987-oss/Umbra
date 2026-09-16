package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventEntityJoinWorld;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class LegacyWorldEntityJoinEventMappingTask
extends JavassistMappingTask {
    private static final String c;

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return;
        }
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().Cy.q;
        this.c(mappingMethod, EventEntityJoinWorld.class, c);
    }


    public LegacyWorldEntityJoinEventMappingTask() {
        super(MappedClasses.YU);
    }

    static {
        try {
            c = "$1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

