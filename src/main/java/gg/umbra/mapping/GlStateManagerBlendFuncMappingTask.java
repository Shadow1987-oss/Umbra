package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.InsertedCallbackLockMarker;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class GlStateManagerBlendFuncMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().Dt.O;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            String string = "{if(" + InsertedCallbackLockMarker.class.getName() + "#check($1, $2)) {   return;}}";
            ctBehavior.insertBefore(string);
        }
        catch (CannotCompileException cannotCompileException) {
            Umbra.logThrowable(cannotCompileException);
        }
    }

    public GlStateManagerBlendFuncMappingTask() {
        super(MappedClasses.K);
    }
}
