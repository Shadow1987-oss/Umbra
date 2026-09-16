package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDirectionVector;
import gg.umbra.wrapper.Wrapper;

public class DirectionVector
extends Wrapper {
    public DirectionVector(Object wrappedObject) {
        super(wrappedObject);
    }

    public static DirectionVector positive() {
        return new DirectionVector(MDirectionVector.getPositive(DirectionVector.umbraInstance.getMappings().qx));
    }
}
