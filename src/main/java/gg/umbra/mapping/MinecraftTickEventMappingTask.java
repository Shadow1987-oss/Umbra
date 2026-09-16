package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventClickMouse;
import gg.umbra.event.impl.EventMouseOverUpdate;
import gg.umbra.event.impl.EventPostTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.MinecraftTickCallbackExprEditor;
import gg.umbra.wrapper.impl.ForgeVersion;
import javassist.CtBehavior;
import javassist.expr.MethodCall;

public class MinecraftTickEventMappingTask
extends JavassistMappingTask {
    private static Exception a(Exception exception) {
        return exception;
    }

    public MinecraftTickEventMappingTask() {
        super(MappedClasses.uP);
    }

    @Override
    public void transform() {
        try {
            MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().U.a;
            CtBehavior ctBehavior = this.F(Umbra.INSTANCE.getMappings().U.v);
            boolean[] blArray = new boolean[]{false};
            ctBehavior.instrument(new MinecraftTickCallbackExprEditor(this, mappingMethod, blArray));
            if (!blArray[0]) {
                this.O(mappingMethod, EventPreTick.class, "", "");
                this.j(mappingMethod, EventPostTick.class, "", "");
            }
            if (ForgeVersion.MC_1_20_6.d()) {
                MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().U.q;
                this.O(mappingMethod2, EventClickMouse.class, "", "false");
                if (ForgeVersion.MC_26_1.d() && Umbra.INSTANCE.getMappings().U.i != null && !Umbra.INSTANCE.getMappings().U.i.hasResolutionFailed()) {
                    this.k(Umbra.INSTANCE.getMappings().U.i, EventMouseOverUpdate.class, "$1");
                }
            }
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to create hooks", exception);
        }
    }

    public static boolean y(MethodCall methodCall, MappingMethod mappingMethod) {
        return MinecraftTickEventMappingTask.c(methodCall, mappingMethod);
    }
}
