package gg.umbra.mapping;

import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

@Deprecated
public class DeprecatedNoopMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
    }

    public DeprecatedNoopMappingTask() {
        super(MappedClasses.DC);
    }
}
