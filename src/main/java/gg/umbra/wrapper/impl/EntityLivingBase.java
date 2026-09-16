package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityLivingBase;

import java.util.Collection;

public class EntityLivingBase
extends Entity {
    private static String[] x;

    public void P$src$V$14ztfy0() {
    }

    public void o(float f) {
        MEntityLivingBase.e(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public boolean p$src$Z$15hev10() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return MEntityLivingBase.p$src$Z$qthfwb(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
        return false;
    }

    public double z(EquipmentSlotGroup equipmentSlotGroup) {
        return MEntityLivingBase.c(EntityLivingBase.umbraInstance.getMappings().hx, this.I, equipmentSlotGroup.getObject());
    }

    public boolean l(BlockStateWorldBridge blockStateWorldBridge) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return MEntityLivingBase.c$src$Z$uftdxu(EntityLivingBase.umbraInstance.getMappings().hx, this.I, blockStateWorldBridge.getObject());
        }
        Object object = blockStateWorldBridge.getType().getObject();
        if (object == null) {
            return false;
        }
        return MEntityLivingBase.c$src$Z$uftdxu(EntityLivingBase.umbraInstance.getMappings().hx, this.I, object);
    }

    public EnumHand G$src$Lgg_umbra_wrapper_impl_EnumHand_$33es8x() {
        if (ForgeVersion.MC_1_12_2.v()) {
            return null;
        }
        Object object = EntityLivingBase.umbraInstance.getMappings().hx.r(this.I);
        if (object == null) {
            return EnumHand.mainHand();
        }
        return new EnumHand(object);
    }

    public void t$src$V$15jm1b0() {
        MEntityLivingBase.h(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public float N$src$F$14ypudi() {
        return MEntityLivingBase.b(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void q(int n) {
        if (ForgeVersion.MC_1_21_4.d()) {
            Registry registry = BuiltInRegistries.j();
            Object object = registry.t(n);
            Holder holder = registry.J(object);
            MEntityLivingBase.r(EntityLivingBase.umbraInstance.getMappings().hx, this.I, holder.getObject());
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            MEntityLivingBase.r(EntityLivingBase.umbraInstance.getMappings().hx, this.I, StatusEffect.E(n).getObject());
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            MEntityLivingBase.E(EntityLivingBase.umbraInstance.getMappings().hx, this.I, Potion.getPotionById(n).getObject());
            return;
        }
        MEntityLivingBase.m(EntityLivingBase.umbraInstance.getMappings().hx, this.I, n);
    }

    public boolean i(StatusEffect statusEffect) {
        if (ForgeVersion.MC_1_20_6.d()) {
            Registry registry = BuiltInRegistries.j();
            return MEntityLivingBase.m(EntityLivingBase.umbraInstance.getMappings().hx, this.I, registry.J(statusEffect.getObject()).getObject());
        }
        return MEntityLivingBase.m(EntityLivingBase.umbraInstance.getMappings().hx, this.I, statusEffect.getObject());
    }

    public EnumCreatureAttribute k$src$Lgg_umbra_wrapper_impl_EnumCreatureAttribute_$uojvxj() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return null;
        }
        return new EnumCreatureAttribute(EntityLivingBase.umbraInstance.getMappings().hx.i(this.I));
    }

    public boolean k$src$Z$15enw27() {
        if (ForgeVersion.MC_1_12_2.v()) {
            return false;
        }
        return MEntityLivingBase.o$src$Z$10vin0a(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public float s() {
        return MEntityLivingBase.z(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    static {
        if (EntityLivingBase.L() != null) {
            EntityLivingBase.g(new String[3]);
        }
    }

    public void V(Object object) {
        MEntityLivingBase.P(EntityLivingBase.umbraInstance.getMappings().hx, this.I, object);
    }

    public void B$src$V$14s4bmy() {
        if (ForgeVersion.MC_1_21_4.d()) {
            MEntityLivingBase.V(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
    }

    public void Y(float f) {
        MEntityLivingBase.Y(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public AttributeInstance t(Holder holder) {
        return new AttributeInstance(MEntityLivingBase.G(EntityLivingBase.umbraInstance.getMappings().hx, this.I, holder.getObject()));
    }

    public void j(float f) {
        if (ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_1_21_10.v()) {
            MEntityLivingBase.q(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
        }
    }

    public void z(float f) {
        MEntityLivingBase.J(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public void T(float f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            SPacketExplosion sPacketExplosion = new SPacketExplosion(EntityLivingBase.umbraInstance.getMappings().hx.u(this.I));
            sPacketExplosion.setPreviousSpeed(f);
            return;
        }
        MEntityLivingBase.d(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public void G(float f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            SPacketExplosion sPacketExplosion = new SPacketExplosion(EntityLivingBase.umbraInstance.getMappings().hx.u(this.I));
            sPacketExplosion.setPosition(f);
            return;
        }
        MEntityLivingBase.P(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public EntityLivingBase(Object object) {
        super(object);
    }

    public float F() {
        return MEntityLivingBase.w(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void z(Entity entity) {
        EntityLivingBase.umbraInstance.getMappings().hx.F(this.I, entity.getObject());
    }

    public boolean S$src$Z$151gttj() {
        return MEntityLivingBase.O$src$Z$3j0onu(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public int B$src$I$14s4bbr() {
        return MEntityLivingBase.M(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void k$src$V$5315b7(float f) {
        MEntityLivingBase.l(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public void X(float f) {
        MEntityLivingBase.c(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public void m$src$V$15frh5h() {
        if (ForgeVersion.MC_1_12_2.d()) {
            MEntityLivingBase.a(EntityLivingBase.umbraInstance.getMappings().hx, this.I, EnumHand.mainHand().getObject());
            return;
        }
        MEntityLivingBase.w$src$V$rgd406(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public Vec3 O(float f) {
        return new Vec3(EntityLivingBase.umbraInstance.getMappings().hx.w(this.I, f));
    }

    public boolean e$src$Z$15bd4i1() {
        return MEntityLivingBase.e(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void I(float f) {
        MEntityLivingBase.X(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public ItemStack B$src$Lgg_umbra_wrapper_impl_ItemStack_$impdvt() {
        return new ItemStack(MEntityLivingBase.s(EntityLivingBase.umbraInstance.getMappings().hx, this.I));
    }

    public RayTraceResult W(double d, float f) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return new RayTraceResult(EntityLivingBase.umbraInstance.getMappings().hx.Y(this.I, d, f, false));
        }
        return new RayTraceResult(EntityLivingBase.umbraInstance.getMappings().hx.v(this.I, d, f));
    }

    public static AttributeModifier A$src$Lgg_umbra_wrapper_impl_AttributeModifier_$lg3ax4() {
        return new AttributeModifier(MEntityLivingBase.I(EntityLivingBase.umbraInstance.getMappings().hx));
    }

    public void R$src$V$150x14q() {
        if (ForgeVersion.MC_1_21_4.d()) {
            MEntityLivingBase.Q(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
    }

    public boolean V$src$Z$15347lm() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return MEntityLivingBase.N$src$Z$dl1vrt(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
        return false;
    }

    public float m$src$F$15frgrp() {
        return MEntityLivingBase.L(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void H(boolean bl) {
        EntityLivingBase.umbraInstance.getMappings().hx.G(this.I, bl);
    }

    public float B$src$F$14s4b96() {
        if (ForgeVersion.MC_1_20_6.d()) {
            SPacketExplosion sPacketExplosion = new SPacketExplosion(EntityLivingBase.umbraInstance.getMappings().hx.u(this.I));
            return sPacketExplosion.getPreviousSpeed();
        }
        return MEntityLivingBase.A(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public Vec3 n(Vec3 vec3) {
        return new Vec3(MEntityLivingBase.R(EntityLivingBase.umbraInstance.getMappings().hx, this.I, vec3.getObject()));
    }

    public Collection B$src$Ljava_util_Collection_$1uxz2f9() {
        return (Collection)MEntityLivingBase.C(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }


    public int i() {
        return EntityLivingBase.umbraInstance.getMappings().hx.U(this.I);
    }

    public float w$src$F$15l9epb() {
        return MEntityLivingBase.I(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public final float e() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return MEntityLivingBase.o(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
        return 1.0f;
    }

    public ItemStack Y$src$Lgg_umbra_wrapper_impl_ItemStack_$1e6807m() {
        return new ItemStack(MEntityLivingBase.P(EntityLivingBase.umbraInstance.getMappings().hx, this.I));
    }

    public static String[] L() {
        return x;
    }

    public float b(float f) {
        return MEntityLivingBase.b(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public int c$src$I$15a9iwo() {
        return MEntityLivingBase.Z(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void b(boolean bl) {
        MEntityLivingBase.L(EntityLivingBase.umbraInstance.getMappings().hx, this.I, bl);
    }

    public float y$src$F$15mczw1() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return EntityLivingBase.umbraInstance.getMappings().hx.X(this.I);
        }
        return MEntityLivingBase.t(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public int d(EnumHand enumHand) {
        if (ForgeVersion.MC_1_12_2.v()) {
            return 0;
        }
        Object object = EntityLivingBase.umbraInstance.getMappings().hx.r(this.I);
        if (object == null) {
            return 0;
        }
        if (object.equals(enumHand.getObject())) {
            return EntityLivingBase.umbraInstance.getMappings().hx.Y(this.I);
        }
        return 0;
    }

    public float K() {
        if (ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_1_21_10.v()) {
            return MEntityLivingBase.Y(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
        return 1.0f;
    }

    public float U$src$F$152kej1() {
        if (ForgeVersion.MC_1_20_6.d()) {
            SPacketExplosion sPacketExplosion = new SPacketExplosion(EntityLivingBase.umbraInstance.getMappings().hx.u(this.I));
            return sPacketExplosion.getPosition();
        }
        return MEntityLivingBase.N(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public double J$src$D$14winyc() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return MEntityLivingBase.n(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
        }
        return 0.08;
    }

    public static void g(String[] stringArray) {
        x = stringArray;
    }

    public void s(PotionEffect potionEffect) {
        MEntityLivingBase.B(EntityLivingBase.umbraInstance.getMappings().hx, this.I, potionEffect.getObject());
    }

    public float P$src$F$14ztfk8() {
        return EntityLivingBase.umbraInstance.getMappings().hx.J(this.I);
    }

    public float W$src$F$153nzpr() {
        return MEntityLivingBase.O(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void h$src$V$15d0i6o() {
        MEntityLivingBase.A$src$V$299c5s(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public float H$src$F$14vf2tc() {
        if (ForgeVersion.MC_1_20_6.d()) {
            SPacketExplosion sPacketExplosion = new SPacketExplosion(EntityLivingBase.umbraInstance.getMappings().hx.u(this.I));
            return sPacketExplosion.getSpeed();
        }
        return MEntityLivingBase.p(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void e(float f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            SPacketExplosion sPacketExplosion = new SPacketExplosion(EntityLivingBase.umbraInstance.getMappings().hx.u(this.I));
            sPacketExplosion.setPreviousSpeed(f);
            return;
        }
        MEntityLivingBase.F(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public PotionEffect e(StatusEffect statusEffect) {
        if (ForgeVersion.MC_1_20_6.d()) {
            Registry registry = BuiltInRegistries.j();
            return new PotionEffect(MEntityLivingBase.T(EntityLivingBase.umbraInstance.getMappings().hx, this.I, registry.J(statusEffect.getObject()).getObject()));
        }
        return new PotionEffect(MEntityLivingBase.T(EntityLivingBase.umbraInstance.getMappings().hx, this.I, statusEffect.getObject()));
    }

    public void t(float f) {
        MEntityLivingBase.S(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public void I(int n) {
        MEntityLivingBase.H(EntityLivingBase.umbraInstance.getMappings().hx, this.I, n);
    }

    public float p() {
        return EntityLivingBase.umbraInstance.getMappings().hx.f(this.I);
    }

    public void M(float f) {
        MEntityLivingBase.L(EntityLivingBase.umbraInstance.getMappings().hx, this.I, f);
    }

    public PotionEffect getActivePotionEffect(Potion potion) {
        return new PotionEffect(MEntityLivingBase.T(EntityLivingBase.umbraInstance.getMappings().hx, this.I, potion.getObject()));
    }

    public float S$src$F$151gtcb() {
        return EntityLivingBase.umbraInstance.getMappings().hx.C(this.I);
    }

    public Vec3 J(float f) {
        return new Vec3(EntityLivingBase.umbraInstance.getMappings().hx.m(this.I, f));
    }

    public float L(float f) {
        return EntityLivingBase.umbraInstance.getMappings().hx.X(this.I, f);
    }

    public boolean Y$src$Z$154rldp() {
        return MEntityLivingBase.c(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public PotionEffect b(PotionEntry potionEntry) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.e(potionEntry.getStatusEffect());
        }
        return this.getActivePotionEffect(potionEntry.getLegacyPotion());
    }

    public boolean i(PotionEntry potionEntry) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.i(potionEntry.getStatusEffect());
        }
        return this.isPotionActive(potionEntry.getLegacyPotion());
    }

    public AttributeInstance h(EquipmentSlotGroup equipmentSlotGroup) {
        return new AttributeInstance(MEntityLivingBase.G(EntityLivingBase.umbraInstance.getMappings().hx, this.I, equipmentSlotGroup.getObject()));
    }

    public boolean canEntityBeSeen(Entity entity) {
        return EntityLivingBase.umbraInstance.getMappings().hx.C(this.I, entity.getObject());
    }

    public ItemStack i(EnumHand enumHand) {
        return new ItemStack(MEntityLivingBase.f(EntityLivingBase.umbraInstance.getMappings().hx, this.I, enumHand.getObject()));
    }

    public Vec3 G(double d, boolean bl, Vec3 vec3) {
        return new Vec3(MEntityLivingBase.g(EntityLivingBase.umbraInstance.getMappings().hx, this.I, d, bl, vec3.getObject()));
    }

    public void p(int n) {
        EntityLivingBase.umbraInstance.getMappings().hx.d(this.I, n);
    }

    public Object z$src$Ljava_lang_Object_$1k68ls2() {
        return MEntityLivingBase.z$src$Ljava_lang_Object_$rov6uz(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public void L(int n) {
        MEntityLivingBase.W(EntityLivingBase.umbraInstance.getMappings().hx, this.I, n);
    }

    public int a$src$I$1595xpy() {
        return MEntityLivingBase.H(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public boolean isPotionActive(Potion potion) {
        return MEntityLivingBase.m(EntityLivingBase.umbraInstance.getMappings().hx, this.I, potion.getObject());
    }

    public float I$src$F$14vyvep() {
        return MEntityLivingBase.v(EntityLivingBase.umbraInstance.getMappings().hx, this.I);
    }

    public double o(Holder holder) {
        return MEntityLivingBase.c(EntityLivingBase.umbraInstance.getMappings().hx, this.I, holder.getObject());
    }

    public boolean boolean_S() {
        return this.S$src$Z$151gttj();
    }
}

