package gg.umbra.asm.transform.impl;

import gg.umbra.Umbra;
import gg.umbra.asm.ITramsformNode;
import gg.umbra.asm.helper.DescUtils;
import gg.umbra.asm.helper.TypedIndexedLocal;
import gg.umbra.asm.transform.ClassTransformer;
import gg.umbra.event.impl.EventClickMouse;
import gg.umbra.event.impl.EventGuiOpen;
import gg.umbra.event.impl.EventRightClickMouse;
import gg.umbra.event.impl.EventSendClickBlockToController;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MinecraftMouseActionTransformer
extends ClassTransformer {
    public MinecraftMouseActionTransformer() {
        super(MappedClasses.uP);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_20_6.v()) {
            this.injectEventAtEntry(Umbra.INSTANCE.getMappings().U.q, EventClickMouse.class, new ITramsformNode[0]);
        }
        if (ForgeVersion.MC_26_2.v()) {
            this.injectEventAtEntry(
                    Umbra.INSTANCE.getMappings().U.OS,
                    EventGuiOpen.class,
                    new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.VW))
                            .setDescriptorClass(Object.class));
        }
        this.injectEventAtEntry(Umbra.INSTANCE.getMappings().U.g, EventRightClickMouse.class, new ITramsformNode[0]);
        this.injectEventAtEntry(Umbra.INSTANCE.getMappings().U.Of, EventSendClickBlockToController.class, new ITramsformNode[0]);
    }

}

