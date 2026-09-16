package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDefaultVertexFormats;
import gg.umbra.wrapper.Wrapper;

public class DefaultVertexFormats
extends Wrapper {
    public static DefaultVertexFormatBridge p() {
        return new DefaultVertexFormatBridge(MDefaultVertexFormats.getPositionColor(DefaultVertexFormats.umbraInstance.getMappingsMapperCompat().DC));
    }

    public DefaultVertexFormats(Object object) {
        super(object);
    }
}
