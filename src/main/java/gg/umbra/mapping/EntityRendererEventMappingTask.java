package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventEntityRendererMouseUpdate;
import gg.umbra.event.impl.EventMouseOverUpdate;
import gg.umbra.event.impl.EventPostEntityRendererMouseUpdate;
import gg.umbra.event.impl.EventPostRenderHand;
import gg.umbra.event.impl.EventPostRenderTick;
import gg.umbra.event.impl.EventPreEntityRendererMouseUpdate;
import gg.umbra.event.impl.EventPreRenderHand;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.EventRender2DStaticCallback;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.expr.EventFogDensityInjectionExprEditor;
import gg.umbra.mapping.expr.EventRender2DInjectionExprEditor;
import gg.umbra.mapping.expr.EventRender2DStaticCallbackExprEditor;
import gg.umbra.runtime.NativeBridge;
import gg.umbra.wrapper.impl.ForgeVersion;
import java.util.concurrent.atomic.AtomicBoolean;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.NotFoundException;

public class EntityRendererEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        if (ForgeVersion.MC_1_21_4.b().y()) {
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().RY.J, EventPreRenderTick.class);
            eventInjectionSpec.setConstructorArguments("$1");
            eventInjectionSpec.setCancelableReturnGuard(false);
            eventInjectionSpec.setInsertBefore(true);
            this.registerEventInjection(eventInjectionSpec);
            this.i();
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            this.c(Umbra.INSTANCE.getMappings().RY.B, EventPreRenderHand.class, "");
            this.k(Umbra.INSTANCE.getMappings().RY.B, EventPostRenderHand.class, "");
        }
        if (ForgeVersion.MC_1_21_11.d()) {
            this.c(Umbra.INSTANCE.getMappings().RY.C, EventPreEntityRendererMouseUpdate.class, "");
            this.k(Umbra.INSTANCE.getMappings().RY.C, EventEntityRendererMouseUpdate.class, "$1");
            this.k(Umbra.INSTANCE.getMappings().RY.a, EventPostEntityRendererMouseUpdate.class, "");
        } else {
            this.c(Umbra.INSTANCE.getMappings().RY.a, EventPreEntityRendererMouseUpdate.class, "");
            this.k(Umbra.INSTANCE.getMappings().RY.a, EventPostEntityRendererMouseUpdate.class, "");
        }
        if (ForgeVersion.MC_26_1.v() && Umbra.INSTANCE.getMappings().RY.k != null && !Umbra.INSTANCE.getMappings().RY.k.hasResolutionFailed()) {
            this.k(Umbra.INSTANCE.getMappings().RY.k, EventMouseOverUpdate.class, "$1");
        }
        this.J(this::lambda$create$0);
        if (ForgeVersion.MC_26_1.d()) {
            this.J(this::lambda$create$1);
        }
        this.J(this::lambda$create$2);
    }

    private void J$src$V$f5bloi() throws CannotCompileException {
        if (ForgeVersion.MC_1_20_6.d()
                || NativeBridge.isBadlion189Runtime()) {
            return;
        }
        String string = ForgeVersion.MC_1_17.d() ? "(IIF)V" : (ForgeVersion.MC_1_16_5.d() ? "()V" : "(IF)V");
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        CtBehavior ctBehavior = this.F(Umbra.INSTANCE.getMappings().RY.J);
        ctBehavior.instrument(new EventRender2DInjectionExprEditor(this, string, atomicBoolean));
    }

    private Object lambda$create$0() throws CannotCompileException, NotFoundException {
        this.J$src$V$f5bloi();
        return null;
    }

    private Object lambda$create$2() throws CannotCompileException, NotFoundException {
        this.r();
        return null;
    }

    private void r() throws CannotCompileException {
        if (ForgeVersion.MC_1_16_5.v()) {
            CtBehavior ctBehavior = this.F(Umbra.INSTANCE.getMappings().RY.L);
            MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().hi.A;
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            ctBehavior.instrument(new EventFogDensityInjectionExprEditor(this, mappingMethod, atomicBoolean));
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private void i() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().RY.J;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventPostRenderTick.class);
        eventInjectionSpec.setConstructorArguments("$1");
        eventInjectionSpec.setCancelableReturnGuard(false);
        eventInjectionSpec.setInsertBefore(false);
        this.registerEventInjection(eventInjectionSpec);
    }

    private void B$src$V$f0x8xm() throws CannotCompileException {
        EntityRendererEventMappingTask.p(EventRender2DStaticCallback.class);
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().Ca.z;
        MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().RY.J;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        CtBehavior ctBehavior = this.F(mappingMethod2);
        ctBehavior.instrument(new EventRender2DStaticCallbackExprEditor(this, atomicBoolean, mappingMethod));
    }

    private Object lambda$create$1() throws CannotCompileException, NotFoundException {
        this.B$src$V$f0x8xm();
        return null;
    }

    public EntityRendererEventMappingTask() {
        super(MappedClasses.FW);
    }
}
