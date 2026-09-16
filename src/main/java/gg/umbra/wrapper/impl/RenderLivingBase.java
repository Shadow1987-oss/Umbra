package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRenderLivingBase;

import java.nio.FloatBuffer;
import java.util.List;

public class RenderLivingBase<T extends EntityLivingBase>
extends Render<T> {
    public ModelBipedSkeletonBridge getMainModel() {
        return new ModelBipedSkeletonBridge(MRenderLivingBase.x(RenderLivingBase.umbraInstance.getMappingsMapperCompat().CP, this.I));
    }

    public List<Object> getLayerRenderers() {
        return (List)MRenderLivingBase.i(RenderLivingBase.umbraInstance.getMappingsMapperCompat().CP, this.I);
    }

    public RenderLivingBase(Object object) {
        super(object);
    }

    public void setLayerRenderers(List list) {
        MRenderLivingBase.O(RenderLivingBase.umbraInstance.getMappingsMapperCompat().CP, this.I, list);
    }

    public FloatBuffer P() {
        return MRenderLivingBase.y(RenderLivingBase.umbraInstance.getMappingsMapperCompat().CP, this.I);
    }
}

