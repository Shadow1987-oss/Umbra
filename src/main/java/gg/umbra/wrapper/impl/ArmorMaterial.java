package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MArmorMaterial;
import gg.umbra.wrapper.Wrapper;

public class ArmorMaterial
extends Wrapper {
    public static ArmorMaterial gold() {
        return new ArmorMaterial(MArmorMaterial.getGold(ArmorMaterial.umbraInstance.getMappingsMapperCompat().e));
    }

    public static ArmorMaterial iron() {
        return new ArmorMaterial(MArmorMaterial.getIron(ArmorMaterial.umbraInstance.getMappingsMapperCompat().e));
    }

    public static ArmorMaterial chain() {
        return new ArmorMaterial(MArmorMaterial.getChain(ArmorMaterial.umbraInstance.getMappingsMapperCompat().e));
    }

    public static ArmorMaterial leather() {
        return new ArmorMaterial(MArmorMaterial.getLeather(ArmorMaterial.umbraInstance.getMappingsMapperCompat().e));
    }

    public ArmorMaterial(Object wrappedObject) {
        super(wrappedObject);
    }

    public static ArmorMaterial diamond() {
        return new ArmorMaterial(MArmorMaterial.getDiamond(ArmorMaterial.umbraInstance.getMappingsMapperCompat().e));
    }
}
