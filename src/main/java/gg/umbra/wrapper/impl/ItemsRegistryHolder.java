package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDamageSource;
import gg.umbra.wrapper.Wrapper;

public class ItemsRegistryHolder
extends Wrapper {
    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
    }

    public static Item e() {
        return new Item(MDamageSource.I(ItemsRegistryHolder.umbraInstance.getMappings().RK));
    }

    public static Item w() {
        if (ForgeVersion.MC_1_12_2.d()) {
            return new Item(MDamageSource.s(ItemsRegistryHolder.umbraInstance.getMappings().RK));
        }
        throw new UnsupportedOperationException("Totem of Undying is not available in this version.");
    }

    public static ItemSplashPotion O() {
        return new ItemSplashPotion(MDamageSource.D(ItemsRegistryHolder.umbraInstance.getMappings().RK));
    }

    public ItemsRegistryHolder(Object object) {
        super(object);
    }

    public static Item h() {
        if (ForgeVersion.MC_1_12_2.d()) {
            return new Item(MDamageSource.T(ItemsRegistryHolder.umbraInstance.getMappings().RK));
        }
        throw new UnsupportedOperationException("End Crystal is not available in this version.");
    }
}

