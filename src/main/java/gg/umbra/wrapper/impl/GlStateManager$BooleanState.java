package gg.umbra.wrapper.impl;

import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.wrapper.Wrapper;

public class GlStateManager$BooleanState
extends Wrapper {
    public void m$src$V$17py9xa() {
        this.O(true);
    }


    public void z() {
        this.O(false);
    }

    public GlStateManager$BooleanState(Object object) {
        super(object);
    }

    public void O(boolean bl) {
        boolean bl2 = GlStateManager$BooleanState.umbraInstance.getMappings().he.J(this.I);
        if (bl != bl2) {
            GlStateManager$BooleanState.umbraInstance.getMappings().he.T(this.I, bl);
            if (bl) {
                OpenGlBackendHolder.backend.enableCapability(GlStateManager$BooleanState.umbraInstance.getMappings().he.n(this.I));
            } else {
                OpenGlBackendHolder.backend.disableCapability(GlStateManager$BooleanState.umbraInstance.getMappings().he.n(this.I));
            }
        }
    }
}

