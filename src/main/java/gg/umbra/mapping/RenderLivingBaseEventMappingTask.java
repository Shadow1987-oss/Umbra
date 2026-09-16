package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockRenderColorOverride;
import gg.umbra.mapping.expr.EventBlockRenderColorOverrideExprEditor;
import gg.umbra.wrapper.impl.ForgeVersion;
import javassist.CtBehavior;

public class RenderLivingBaseEventMappingTask
extends JavassistMappingTask {
    private static Exception a(Exception exception) {
        return exception;
    }

    public RenderLivingBaseEventMappingTask() {
        super(MappedClasses.Fq);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod;
        if (ForgeVersion.MC_1_8_9.d()) {
            mappingMethod = Umbra.INSTANCE.getMappings().CP.O;
            String string = EventBlockRenderColorOverride.class.getName();
            CtBehavior ctBehavior = this.F(mappingMethod);
            this.O(mappingMethod, EventBlockRenderColorOverride.class, "", "false");
            try {
                ctBehavior.instrument(new EventBlockRenderColorOverrideExprEditor(this, string));
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        }
        if (ForgeVersion.MC_1_7_10.L() && Umbra.INSTANCE.isForgeAbsent()) {
            mappingMethod = Umbra.INSTANCE.getMappings().CP.r;
            this.c(mappingMethod, EventPreRenderEntityForgeCallback.class, "$1, $2, $3, $4");
        }
    }
}
