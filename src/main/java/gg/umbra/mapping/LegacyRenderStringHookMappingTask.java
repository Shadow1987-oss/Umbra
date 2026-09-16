package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.LegacyRenderStringEventRender3DCallback;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class LegacyRenderStringHookMappingTask
extends JavassistMappingTask {
    private static final String c = "#call($1);}";

    public LegacyRenderStringHookMappingTask() {
        super(MappedClasses.Vk);
    }

    @Override
    public void transform() {
        LegacyRenderStringHookMappingTask.p(LegacyRenderStringEventRender3DCallback.class);
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().C5.A;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + LegacyRenderStringEventRender3DCallback.class.getName() + c);
        }
        catch (CannotCompileException cannotCompileException) {
            Umbra.logThrowable(cannotCompileException);
        }
    }
}
