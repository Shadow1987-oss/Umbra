package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.RenderBatchFlushCallbackMarker;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class ThreadBoundCallbackMappingTask
extends JavassistMappingTask {
    private static final String c = "#call();}";

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().glCommandEncoder.drawFromBuffersMethod;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + RenderBatchFlushCallbackMarker.class.getName() + c);
        }
        catch (CannotCompileException cannotCompileException) {
            Umbra.logThrowable(cannotCompileException);
        }
    }

    public ThreadBoundCallbackMappingTask() {
        super(MappedClasses.zg);
    }
}
