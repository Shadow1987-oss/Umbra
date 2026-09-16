package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MMatrixStackEntry;
import gg.umbra.wrapper.Wrapper;

public class MatrixStackEntry
extends Wrapper {
    public Matrix4f getMatrix() {
        return new Matrix4f(MMatrixStackEntry.getMatrix(MatrixStackEntry.umbraInstance.getMappingsMapperCompat().CK, this.I));
    }

    public MatrixStackEntry(Object wrappedObject) {
        super(wrappedObject);
    }
}
