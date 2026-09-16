package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.EventRender2DStaticCallback;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class Render2DStaticCallbackMappingTask
extends JavassistMappingTask {
    private static final String c = "#call();}";

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_26_1.d()) {
            return;
        }
        Render2DStaticCallbackMappingTask.p(EventRender2DStaticCallback.class);
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().titledScreen.renderHudMethod;
        if (mappingMethod == null) {
            return;
        }
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertAfter("{" + EventRender2DStaticCallback.class.getName() + c);
        }
        catch (CannotCompileException cannotCompileException) {
            Umbra.logThrowable(cannotCompileException);
        }
    }

    public Render2DStaticCallbackMappingTask() {
        super(MappedClasses.Zj);
    }
}
