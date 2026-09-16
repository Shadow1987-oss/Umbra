package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRender;
import gg.umbra.wrapper.Wrapper;

public class Render<T extends Entity>
extends Wrapper {
    public ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(MRender.s(Render.umbraInstance.getMappingsMapperCompat().qe, this.I, entity.getObject()));
    }

    public void doRender(Entity entity, double d, double d2, double d3, float f, float f2) {
        MRender mRender = Render.umbraInstance.getMappingsMapperCompat().qe;
        if (mRender == null || mRender.p == null || mRender.p.hasResolutionFailed()) {
            // On Minecraft 1.21.10+ (render-state / submit-graph pipelines) LivingEntityRenderer has
            // no callable doRender/render(entity, ...) equivalent, so this mapping legitimately does
            // not exist. Callers must not assume the model can be re-rendered there.
            return;
        }
        MRender.E(mRender, this.I, entity.getObject(), d, d2, d3, f, f2);
    }

    public RenderStateBridge a(Entity entity, float f) {
        return new RenderStateBridge(MRender.s(Render.umbraInstance.getMappingsMapperCompat().qe, this.I, entity.getObject(), f));
    }

    public Render(Object object) {
        super(object);
    }
}

