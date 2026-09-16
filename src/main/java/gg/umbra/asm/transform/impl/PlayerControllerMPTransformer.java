package gg.umbra.asm.transform.impl;

import gg.umbra.Umbra;
import gg.umbra.asm.helper.IndexedLocal;
import gg.umbra.asm.transform.ClassTransformer;
import gg.umbra.event.impl.EventPostAttack;
import gg.umbra.event.impl.EventPreAttack;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.PlayerControllerMPEventMappingTask;

public class PlayerControllerMPTransformer
extends ClassTransformer {
    private static int opaqueState;

    static {
        if (PlayerControllerMPTransformer.opaquePredicate() != 0) {
            PlayerControllerMPTransformer.setOpaqueState(19);
        }
    }

    public static int getOpaqueState() {
        return opaqueState;
    }

    public PlayerControllerMPTransformer() {
        super(MappedClasses.ld);
    }


    public static void setOpaqueState(int state) {
        opaqueState = state;
    }

    @Override
    public void transform() {
        if (!PlayerControllerMPEventMappingTask.U) {
            return;
        }
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().hj.i;
        this.injectEventAtEntry(mappingMethod, EventPreAttack.class, new IndexedLocal(2).setDescriptorClass(Object.class));
        this.injectEventAtExit(mappingMethod, EventPostAttack.class, new IndexedLocal(2).setDescriptorClass(Object.class));
    }

    public static int opaquePredicate() {
        int state = PlayerControllerMPTransformer.getOpaqueState();
        if (state == 0) {
            return 52;
        }
        return 0;
    }
}

