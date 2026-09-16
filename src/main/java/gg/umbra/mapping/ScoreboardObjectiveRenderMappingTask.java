package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.asm.helper.DescUtils;
import gg.umbra.asm.helper.TypedIndexedLocal;
import gg.umbra.asm.transform.ClassTransformer;
import gg.umbra.event.impl.EventScoreboardObjectiveRender;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.ForgeVersion;

public class ScoreboardObjectiveRenderMappingTask
extends ClassTransformer {

    public ScoreboardObjectiveRenderMappingTask() {
        super(MappedClasses.Zj);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return;
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            this.injectEventAtEntry(Umbra.INSTANCE.getMappings().titledScreen.renderScoreboardMethod, EventScoreboardObjectiveRender.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.Y)).setDescriptorClass(Object.class), new TypedIndexedLocal(2, "I"), new TypedIndexedLocal(3, "I"), new TypedIndexedLocal(4, DescUtils.getDescriptor(MappedClasses.uQ)).setDescriptorClass(Object.class));
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.injectEventAtEntry(Umbra.INSTANCE.getMappings().titledScreen.renderScoreboardMethod, EventScoreboardObjectiveRender.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.DQ)).setDescriptorClass(Object.class), new TypedIndexedLocal(2, DescUtils.getDescriptor(MappedClasses.Y)).setDescriptorClass(Object.class));
        } else {
            this.injectEventAtEntry(Umbra.INSTANCE.getMappings().titledScreen.renderScoreboardMethod, EventScoreboardObjectiveRender.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.Y)).setDescriptorClass(Object.class), new TypedIndexedLocal(2, DescUtils.getDescriptor(MappedClasses.Zz)).setDescriptorClass(Object.class));
        }
    }
}

