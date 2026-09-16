package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MActiveRenderInfo;
import gg.umbra.wrapper.Wrapper;

public class ActiveRenderInfo
extends Wrapper {
    public Entity W() {
        return new Entity(MActiveRenderInfo.Z(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I));
    }

    public void g(Vec3 vec3) {
        MActiveRenderInfo.P(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I, vec3.getObject());
    }

    public Quaternion G() {
        return new Quaternion(MActiveRenderInfo.d(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I));
    }

    public float Z() {
        return MActiveRenderInfo.q(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I);
    }

    public float x() {
        return MActiveRenderInfo.A(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I);
    }

    public Vec3 o() {
        return new Vec3(MActiveRenderInfo.p(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I));
    }

    public ActiveRenderInfo(Object object) {
        super(object);
    }

    public BlockReader x$src$Lgg_umbra_wrapper_impl_BlockReader_$120g8sh() {
        return new BlockReader(MActiveRenderInfo.i(ActiveRenderInfo.umbraInstance.getMappings().hi, this.I));
    }
}

