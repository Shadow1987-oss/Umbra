package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;
import java.util.List;

public class Ordering
extends Wrapper {
    public Ordering(Object wrappedObject) {
        super(wrappedObject);
    }

    public List sortedCopy(Iterable iterable) {
        return Ordering.umbraInstance.getMappingsMapperCompat().qm.sortedCopy(this.I, iterable);
    }
}
