package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class FontGlyphInfo
extends Wrapper {
    public float getShadowOffset() {
        return FontGlyphInfo.umbraInstance.getMappingsMapperCompat().hZ.getShadowOffset(this.I);
    }

    public FontGlyphInfo(Object wrappedObject) {
        super(wrappedObject);
    }

    public float getBoldOffset() {
        return FontGlyphInfo.umbraInstance.getMappingsMapperCompat().hZ.getBoldOffset(this.I);
    }

    public float getAdvance(boolean bold) {
        return FontGlyphInfo.umbraInstance.getMappingsMapperCompat().hZ.getAdvance(this.I, bold);
    }

    public float getAdvance() {
        return FontGlyphInfo.umbraInstance.getMappingsMapperCompat().hZ.getAdvance(this.I);
    }
}
