package gg.umbra.mapping.expr;

import gg.umbra.event.EventDispatcher;
import gg.umbra.event.impl.EventFogDensity;
import gg.umbra.mapping.EntityRendererEventMappingTask;
import gg.umbra.mapping.MappingMethod;
import java.util.concurrent.atomic.AtomicBoolean;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class EventFogDensityInjectionExprEditor
extends ExprEditor {
    final EntityRendererEventMappingTask u;
    final MappingMethod d;
    final AtomicBoolean o;

    public EventFogDensityInjectionExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, MappingMethod mappingMethod, AtomicBoolean atomicBoolean) {
        this.u = entityRendererEventMappingTask;
        this.d = mappingMethod;
        this.o = atomicBoolean;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.d.getResolvedName()) && methodCall.getSignature().equals(this.d.getDescriptor())) {
            String string = EventFogDensity.class.getName();
            methodCall.replace("$_ = $proceed($$);" + string + " umbraEvent = new " + string + "(0.1f);if (umbraEvent." + EventDispatcher.getFireMethod(EventFogDensity.class).getName() + "()) {return;}");
            this.o.set(true);
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}
