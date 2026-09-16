package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MGlyphProvider;
import gg.umbra.wrapper.Wrapper;

public class GlyphProvider
extends Wrapper {
    public GlyphProvider(Object handle) {
        super(handle);
    }

    public FontGlyph getGlyph(int codePoint) {
        return new FontGlyph(MGlyphProvider.getGlyph(GlyphProvider.umbraInstance.getMappingsMapperCompat().glyphSource, this.I, codePoint));
    }
}
