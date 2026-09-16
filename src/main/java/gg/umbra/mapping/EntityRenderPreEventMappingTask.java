package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.EventPreRenderEntityCallback;
import gg.umbra.mapping.EventRenderPlayerPostCallback;
import gg.umbra.mapping.EventRenderPlayerPreCallback;
import gg.umbra.mapping.InjectionParameterSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;
import javassist.CtBehavior;

public class EntityRenderPreEventMappingTask
extends JavassistMappingTask {
    public EntityRenderPreEventMappingTask() {
        super(MappedClasses.Dc);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_21_10.d()) {
            this.L();
            return;
        }
        MappingMethod mappingMethod = ForgeVersion.MC_1_21_6.d() ? Umbra.INSTANCE.getMappings().CA.r : Umbra.INSTANCE.getMappings().qe.p;
        MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().CA.x;
        InjectionParameterSpec[] injectionParameterSpecArray = new InjectionParameterSpec[]{new InjectionParameterSpec(1, Object.class), new InjectionParameterSpec(6, Object.class), new InjectionParameterSpec(9, Object.class)};
        if (ForgeVersion.MC_1_21_0.H().y()) {
            injectionParameterSpecArray = new InjectionParameterSpec[]{new InjectionParameterSpec(1, Object.class), new InjectionParameterSpec(7, Object.class), new InjectionParameterSpec(0, Object.class)};
        }
        this.H(mappingMethod2, injectionParameterSpecArray);
        this.k(mappingMethod2, mappingMethod, EventRenderPlayerPreCallback.class.getName() + "#call", true, false, injectionParameterSpecArray);
        this.k(mappingMethod2, mappingMethod, EventRenderPlayerPostCallback.class.getName() + "#call", false, false, injectionParameterSpecArray);
        InjectionParameterSpec[] injectionParameterSpecArray2 = new InjectionParameterSpec[]{new InjectionParameterSpec(1, Object.class)};
        if (ForgeVersion.MC_1_21_6.d()) {
            injectionParameterSpecArray2 = new InjectionParameterSpec[]{new InjectionParameterSpec(1, Object.class), new InjectionParameterSpec(6, Object.class), new InjectionParameterSpec(5, Float.TYPE), new InjectionParameterSpec(9, Object.class), new InjectionParameterSpec(7, Object.class), new InjectionParameterSpec(8, Integer.TYPE)};
        }
        this.H(mappingMethod2, injectionParameterSpecArray2);
        if (ForgeVersion.MC_1_21_6.d()) {
            this.k(mappingMethod2, mappingMethod, EventPreRenderEntityCallback.class.getName() + "#call", true, false, injectionParameterSpecArray2);
        } else {
            this.T(mappingMethod2, mappingMethod, EventPreRenderEntityCallback.class.getName() + "#call", true, injectionParameterSpecArray2);
        }
    }

    private void L() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().CA.j;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + EventPreRenderEntityCallback.class.getName() + "#call($1);}");
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
    }

}

