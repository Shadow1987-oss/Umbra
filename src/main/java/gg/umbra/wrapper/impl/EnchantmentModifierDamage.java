package gg.umbra.wrapper.impl;

public class EnchantmentModifierDamage
extends EnchantmentModifier {
    public DamageSource getSource() {
        return new DamageSource(EnchantmentModifierDamage.umbraInstance.getMappingsMapperCompat().enchantmentDamageModifier.getSource(this.I));
    }

    public void setSource(DamageSource damageSource) {
        EnchantmentModifierDamage.umbraInstance.getMappingsMapperCompat().enchantmentDamageModifier.setSource(this.I, damageSource.getObject());
    }

    public int getDamageModifierValue() {
        return EnchantmentModifierDamage.umbraInstance.getMappingsMapperCompat().enchantmentDamageModifier.getDamageModifier(this.I);
    }

    public void setDamageModifier(int damageModifier) {
        EnchantmentModifierDamage.umbraInstance.getMappingsMapperCompat().enchantmentDamageModifier.setDamageModifier(this.I, damageModifier);
    }

    public EnchantmentModifierDamage(Object handle) {
        super(handle);
    }
}
