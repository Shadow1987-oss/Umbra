package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRenderStateBridge;
import gg.umbra.wrapper.Wrapper;

public class RenderStateBridge
extends Wrapper {
    public double k() {
        return MRenderStateBridge.Z(RenderStateBridge.umbraInstance.getMappingsMapperCompat().q3, this.I);
    }

    public ITextComponent d() {
        return new ITextComponent(RenderStateBridge.umbraInstance.getMappingsMapperCompat().q3.u(this.I));
    }

    public double L() {
        return MRenderStateBridge.g(RenderStateBridge.umbraInstance.getMappingsMapperCompat().q3, this.I);
    }

    public RenderStateBridge(Object object) {
        super(object);
    }

    public double F() {
        return MRenderStateBridge.Y(RenderStateBridge.umbraInstance.getMappingsMapperCompat().q3, this.I);
    }

    public void Z(ITextComponent t3_02) {
        RenderStateBridge.umbraInstance.getMappingsMapperCompat().q3.q(this.I, t3_02.getObject());
    }
}

