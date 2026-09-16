package gg.umbra.mapping;

import gg.umbra.mapping.EventPostTickCallback;
import gg.umbra.mapping.EventPreTickCallback;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.MinecraftTickEventMappingTask;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MinecraftTickCallbackExprEditor
extends ExprEditor {
    final MinecraftTickEventMappingTask a;
    final MappingMethod m;
    final boolean[] W;

    public MinecraftTickCallbackExprEditor(MinecraftTickEventMappingTask minecraftTickEventMappingTask, MappingMethod mappingMethod, boolean[] blArray) {
        this.a = minecraftTickEventMappingTask;
        this.m = mappingMethod;
        this.W = blArray;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (MinecraftTickEventMappingTask.y(methodCall, this.m)) {
            this.W[0] = true;
            methodCall.replace("{" + EventPreTickCallback.class.getName() + "#call();$_ = $proceed($$);" + EventPostTickCallback.class.getName() + "#call();}");
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}

