package gg.umbra.wrapper.impl;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.Wrapper;

public class FontGlyph
extends Wrapper {
    public boolean isGlyphInfo() {
        return MappedClasses.v != null && MappedClasses.v.isInstance(this.I);
    }

    public GlyphInfo asGlyphInfo() {
        if (this.isGlyphInfo()) {
            return new GlyphInfo(this.I);
        }
        return null;
    }

    public FontGlyphInfo getInfo() {
        Object info = FontGlyph.umbraInstance.getMappingsMapperCompat().De.getInfo(this.I);
        return info != null ? new FontGlyphInfo(info) : null;
    }


    public FontGlyph(Object wrappedObject) {
        super(wrappedObject);
    }
}

