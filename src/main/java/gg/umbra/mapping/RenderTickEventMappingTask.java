package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.asm.ITramsformNode;
import gg.umbra.asm.helper.DescUtils;
import gg.umbra.asm.helper.TypedIndexedLocal;
import gg.umbra.asm.transform.ClassTransformer;
import gg.umbra.event.impl.EventPostRenderTick;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class RenderTickEventMappingTask
extends ClassTransformer {

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().RY.J;
        if (ForgeVersion.MC_1_21_4.d()) {
            this.injectEventAtEntry(mappingMethod, EventPreRenderTick.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.uy)).setDescriptorClass(Object.class));
            this.injectEventAtExit(mappingMethod, EventPostRenderTick.class, new ITramsformNode[0]);
        }
    }

    public RenderTickEventMappingTask() {
        super(MappedClasses.FW);
    }
}

