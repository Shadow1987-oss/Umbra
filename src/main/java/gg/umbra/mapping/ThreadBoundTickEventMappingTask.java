package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.InjectionParameterSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.ThreadBoundEventPostTickCallback;
import gg.umbra.mapping.ThreadBoundEventPreTickCallback;

public class ThreadBoundTickEventMappingTask
extends JavassistMappingTask {
    public ThreadBoundTickEventMappingTask() {
        super(MappedClasses.zY);
    }

    @Override
    public void transform() {
        ThreadBoundTickEventMappingTask.p(ThreadBoundEventPreTickCallback.class);
        ThreadBoundTickEventMappingTask.p(ThreadBoundEventPostTickCallback.class);
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().itemBucketUse.useMethod;
        MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().CF.p;
        this.k(mappingMethod, mappingMethod2, ThreadBoundEventPreTickCallback.class.getName() + "#call", true, false, new InjectionParameterSpec[0]);
        this.k(mappingMethod, mappingMethod2, ThreadBoundEventPostTickCallback.class.getName() + "#call", false, false, new InjectionParameterSpec[0]);
    }
}
