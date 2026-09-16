package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.ItemStackTooltipCallback;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class ItemStackTooltipMappingTask
extends JavassistMappingTask {

    public ItemStackTooltipMappingTask() {
        super(MappedClasses.VK);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().q8.J;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, ItemStackTooltipCallback.class);
        eventInjectionSpec.setConstructorArguments("$0, $1, $2");
        eventInjectionSpec.setReturnExpression("($r) $event.getTooltip()");
        if (ForgeVersion.MC_1_17.v()) {
            this.registerEventInjection(eventInjectionSpec);
            try {
                this.F(mappingMethod).getMethodInfo().rebuildStackMap(e);
            }
            catch (Exception exception) {
                throw new IllegalStateException("Could not rebuild ItemStack tooltip stack map", exception);
            }
        }
    }
}

