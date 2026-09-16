package gg.umbra.asm.transform.impl;

import gg.umbra.Umbra;
import gg.umbra.asm.helper.DescUtils;
import gg.umbra.asm.helper.TypedIndexedLocal;
import gg.umbra.asm.transform.ClassTransformer;
import gg.umbra.event.impl.EventGuiOpen;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class GuiScreenOpenTransformer
extends ClassTransformer {
    public GuiScreenOpenTransformer() {
        super(MappedClasses.uH);
    }

    @Override
    public void transform() {
        MappingMethod setScreenMethod = Umbra.INSTANCE.getMappings().U.getGuiSetScreenMethod();
        if (setScreenMethod == null || setScreenMethod.getOwnerClass() != MappedClasses.uH) {
            throw new IllegalStateException("Gui.setScreen mapping is unavailable for the 26.2 GUI hook");
        }
        this.injectEventAtEntry(
                setScreenMethod,
                EventGuiOpen.class,
                new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.VW))
                        .setDescriptorClass(Object.class));
    }
}
