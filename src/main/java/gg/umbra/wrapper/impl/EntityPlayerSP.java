package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityPlayerSP;
import gg.umbra.ui.click.component.GuiComponent;

public class EntityPlayerSP
extends AbstractClientPlayer {
    private static GuiComponent[] q;

    public float a$src$F$1txy325() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.getObject()).o();
        }
        return MEntityPlayerSP.q(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    @Override
    public float F() {
        if (ForgeVersion.MC_1_12_2.d()) {
            return EntityPlayerSP.umbraInstance.getMappings().CC.z(this.I);
        }
        return super.F();
    }

    public double X$src$D$1tszxo6() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.getObject()).w();
        }
        return MEntityPlayerSP.g(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public float O$src$F$1to1sdn() {
        return MEntityPlayerSP.M(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public void E(int n) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).setTicksSinceMovePacket(n);
            return;
        }
        MEntityPlayerSP.y(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, n);
    }

    public static GuiComponent[] M$src$ALgg_umbra_ui_click_component_GuiComponent_$1jrrwr5() {
        return q;
    }

    @Override
    public void M(float f) {
        if (ForgeVersion.MC_1_12_2.d()) {
            EntityPlayerSP.umbraInstance.getMappings().CC.l(this.I, f);
            return;
        }
        super.M(f);
    }

    public float g() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.getObject()).E$src$F$kvfqaa();
        }
        return MEntityPlayerSP.z(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public int z$src$I$1uboxyr() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return 0;
        }
        return EntityPlayerSP.umbraInstance.getMappings().CC.r(this.I);
    }

    public void A(float f) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).S(f);
            return;
        }
        MEntityPlayerSP.B(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, f);
    }

    public void Z(float f) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).w(f);
            return;
        }
        MEntityPlayerSP.Z(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, f);
    }

    public double o$src$D$1u5n7bh() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.getObject()).y$src$D$lo113c();
        }
        return MEntityPlayerSP.a(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }


    public float k$src$F$1u3g0zr() {
        return MEntityPlayerSP.a$src$F$12v06yw(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public void sendChatMessage(String string) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).sendChatMessage(string);
            return;
        }
        MEntityPlayerSP.X(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, string);
    }

    public EntityPlayerSP(Object object) {
        super(object);
    }

    public void I(double d) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).J(d);
            return;
        }
        MEntityPlayerSP.m(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, d);
    }

    public void z(double d) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).a(d);
            return;
        }
        MEntityPlayerSP.T(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, d);
    }

    public float t$src$F$1u8e6c0() {
        return MEntityPlayerSP.O(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public void a(float f) {
        MEntityPlayerSP.d(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, f);
    }

    public void E(float f) {
        MEntityPlayerSP.j(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, f);
    }

    public void d(float f) {
        MEntityPlayerSP.f(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, f);
    }

    public int L$src$I$1tmeeo5() {
        return EntityPlayerSP.umbraInstance.getMappings().CC.L(this.I);
    }

    public void Y(double d) {
        if (ForgeVersion.MC_1_7_10.B()) {
            new EntityClientPlayerMP(this.getObject()).Q(d);
            return;
        }
        MEntityPlayerSP.X(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, d);
    }

    public int y$src$I$1ub55de() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.getObject()).getTicksSinceMovePacket();
        }
        return MEntityPlayerSP.z$src$I$9x5vb2(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public NetHandlerPlayClientImpl sendQueue() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.I).sendQueue();
        }
        return new NetHandlerPlayClientImpl(MEntityPlayerSP.I(EntityPlayerSP.umbraInstance.getMappings().CC, this.I));
    }

    public float n$src$F$1u53eru() {
        return MEntityPlayerSP.V(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public float x$src$F$1ualcpg() {
        return MEntityPlayerSP.j(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public void F(float f) {
        MEntityPlayerSP.A(EntityPlayerSP.umbraInstance.getMappings().CC, this.I, f);
    }

    public double Q$src$D$1tp5din() {
        if (ForgeVersion.MC_1_7_10.B()) {
            return new EntityClientPlayerMP(this.getObject()).O$src$D$l0xo66();
        }
        return MEntityPlayerSP.T(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public float q$src$F$1u6qsjx() {
        return MEntityPlayerSP.o(EntityPlayerSP.umbraInstance.getMappings().CC, this.I);
    }

    public static void P(GuiComponent[] guiComponentArray) {
        q = guiComponentArray;
    }

    public MovementInput movementInput() {
        return new MovementInput(EntityPlayerSP.umbraInstance.getMappings().CC.r$src$Ljava_lang_Object_$zogw4x(this.I));
    }

    static {
        if (EntityPlayerSP.M$src$ALgg_umbra_ui_click_component_GuiComponent_$1jrrwr5() == null) {
            EntityPlayerSP.P(new GuiComponent[1]);
        }
    }

    public MovementInput a_jw_2_I() {
        return this.movementInput();
    }
}

