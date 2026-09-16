package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventEntityJoinWorld;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class WorldEntityJoinEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().Cy.q;
        String string = ForgeVersion.MC_1_20_6.d() ? "$1" : "$2";
        this.c(mappingMethod, EventEntityJoinWorld.class, string);
    }

    public WorldEntityJoinEventMappingTask() {
        super(MappedClasses.Z);
    }

}

