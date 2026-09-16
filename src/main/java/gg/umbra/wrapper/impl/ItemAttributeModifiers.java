package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;
import java.util.Collection;

public class ItemAttributeModifiers
extends Wrapper {
    public int size() {
        return ItemAttributeModifiers.umbraInstance.getMappingsMapperCompat().itemAttributeModifiers.size(this.I);
    }

    public boolean put(Object key, Object value) {
        return ItemAttributeModifiers.umbraInstance.getMappingsMapperCompat().itemAttributeModifiers.put(this.I, key, value);
    }

    public Collection values() {
        return ItemAttributeModifiers.umbraInstance.getMappingsMapperCompat().itemAttributeModifiers.values(this.I);
    }

    public ItemAttributeModifiers(Object handle) {
        super(handle);
    }
}
