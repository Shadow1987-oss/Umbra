package gg.umbra.wrapper.impl;

public class RegistryNamespaced
extends RegistrySimple {
    public Object getByValue(int id) {
        return RegistryNamespaced.umbraInstance.getMappingsMapperCompat().qc.getByValue(this.I, id);
    }

    public RegistryNamespaced(Object wrappedObject) {
        super(wrappedObject);
    }
}
