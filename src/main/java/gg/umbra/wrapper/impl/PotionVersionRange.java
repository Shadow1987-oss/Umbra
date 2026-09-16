package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MPotionVersionRange;
import gg.umbra.wrapper.Wrapper;
import java.util.Set;

public class PotionVersionRange
extends Wrapper {
    public Set entrySet() {
        return (Set)MPotionVersionRange.getEntrySet(PotionVersionRange.umbraInstance.getMappingsMapperCompat().itemEnchantments, this.I);
    }

    public static PotionVersionRange empty() {
        return new PotionVersionRange(MPotionVersionRange.getEmpty(PotionVersionRange.umbraInstance.getMappingsMapperCompat().itemEnchantments));
    }

    public PotionVersionRange(Object handle) {
        super(handle);
    }
}
