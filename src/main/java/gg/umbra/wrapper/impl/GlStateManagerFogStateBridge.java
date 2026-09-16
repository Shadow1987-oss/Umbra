package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;
import java.util.List;

public class GlStateManagerFogStateBridge
extends Wrapper {
    public List<String> getItemStates() {
        return (List<String>)GlStateManagerFogStateBridge.umbraInstance.getMappingsMapperCompat().q2.getItemStates(this.I);
    }

    public GlStateManagerFogStateBridge(Object wrappedObject) {
        super(wrappedObject);
    }
}
