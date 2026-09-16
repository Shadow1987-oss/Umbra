package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MRenderWorldLastEvent;
import gg.umbra.wrapper.Wrapper;

public class RenderWorldLastEvent
extends Wrapper {

    public static float getRenderResolutionMultiplier() {
        if (!Umbra.renderReady) {
            return 1.0f;
        }
        return MRenderWorldLastEvent.getRenderResolutionMultiplier(RenderWorldLastEvent.umbraInstance.getMappingsMapperCompat().shadersConfig);
    }

    public RenderWorldLastEvent(Object handle) {
        super(handle);
    }
}

