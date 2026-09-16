package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ObjectToIntMapEntry
extends Wrapper {
    public int getIntValue() {
        return ObjectToIntMapEntry.umbraInstance.getMappings().objectToIntMapEntry.getIntValue(this.I);
    }

    public ObjectToIntMapEntry(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getKey() {
        return ObjectToIntMapEntry.umbraInstance.getMappings().objectToIntMapEntry.getKey(this.I);
    }
}
