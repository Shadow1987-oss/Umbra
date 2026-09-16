package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventMotion;
import gg.umbra.event.impl.EventPostMotion;
import gg.umbra.event.impl.EventPreMotion;
import gg.umbra.runtime.NativeBridge;
import gg.umbra.wrapper.impl.ForgeVersion;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

public class EntityClientPlayerMPMotionMappingTask
extends JavassistMappingTask {
    private String y;
    private String q;
    private String u;
    private CtClass p;
    private String M;
    private String b;
    private MappingMethod J = null;

    public static String z(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.u;
    }

    public static String Q(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.b;
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    private Object lambda$create$0() throws CannotCompileException, NotFoundException {
        this.s();
        return null;
    }

    public EntityClientPlayerMPMotionMappingTask(Class clazz) {
        super(clazz);
    }

    @Override
    public void transform() {
        this.J(this::lambda$create$0);
    }

    private void s() throws CannotCompileException, NotFoundException {
        String string;
        this.J = Umbra.INSTANCE.getMappings().q_.M;
        this.q = Umbra.INSTANCE.getMappings().RQ.n.getResolvedName();
        this.M = Umbra.INSTANCE.getMappings().RQ.n.getDescriptor();
        String string2 = ForgeVersion.MC_1_16_5.d() ? Umbra.INSTANCE.getMappings().Rr.jz.getResolvedName() : null;
        this.y = Umbra.INSTANCE.getMappings().Rr.jv.getResolvedName();
        this.u = Umbra.INSTANCE.getMappings().Rr.jT.getResolvedName();
        this.b = Umbra.INSTANCE.getMappings().Rr.U.getResolvedName();
        this.p = this.i(MappedClasses.uk);
        String string3 = EventMotion.class.getName();
        CtBehavior ctBehavior = this.F(this.J);
        this.c(this.J, EventPreMotion.class, "$0");
        this.k(this.J, EventPostMotion.class, "$0");
        boolean bl = Umbra.INSTANCE.isForgeAbsent();
        if (bl && NativeBridge.gc(string = "aaa") == null || ForgeVersion.MC_1_7_10.L() && Umbra.INSTANCE.isVanillaMinecraftPresent()) {
            bl = false;
        }
        boolean bl2 = bl;
        CtClass ctClass = this.i(MappedClasses.zc);
        ctBehavior.instrument(new EntityClientPlayerMPMotionExprEditor(this, string2, string3, bl2, ctClass));
    }

    public static String j(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask) {
        return entityClientPlayerMPMotionMappingTask.y;
    }
}
