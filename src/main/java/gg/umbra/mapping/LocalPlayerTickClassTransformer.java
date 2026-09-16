package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.asm.helper.Local;
import gg.umbra.asm.transform.ClassTransformer;
import gg.umbra.event.impl.EventPostLocalPlayerTick;
import gg.umbra.event.impl.EventPreLocalPlayerTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class LocalPlayerTickClassTransformer
extends ClassTransformer {
    private void w() {
        if (ForgeVersion.MC_1_21_10.v()) {
            return;
        }
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().CC.z;
        this.injectEventAtEntry(mappingMethod, EventPreLocalPlayerTick.class, new Local("this").setDescriptorClass(Object.class));
        this.injectEventAtExit(mappingMethod, EventPostLocalPlayerTick.class, new Local("this").setDescriptorClass(Object.class));
    }

    public LocalPlayerTickClassTransformer() {
        super(MappedClasses.z5);
    }


    @Override
    public void transform() {
        this.w();
    }
}

