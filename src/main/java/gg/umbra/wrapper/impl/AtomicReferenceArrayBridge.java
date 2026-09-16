package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicReferenceArrayBridge
extends Wrapper {
    public AtomicReferenceArray getChunks() {
        return (AtomicReferenceArray)AtomicReferenceArrayBridge.umbraInstance.getMappingsMapperCompat().H.getChunks(this.I);
    }

    public AtomicReferenceArrayBridge(Object wrappedObject) {
        super(wrappedObject);
    }
}
