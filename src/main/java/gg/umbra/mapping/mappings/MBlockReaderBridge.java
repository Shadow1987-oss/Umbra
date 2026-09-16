package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MBlockReaderBridge
extends Mapping {
    private static final String GET_BLOCK_STATE_METHOD_NAME = "getBlockState";
    private final MappingMethod getBlockStateMethod;

    public Object getBlockState(Object blockReader, Object blockPos) {
        return this.getBlockStateMethod.invokeObject(blockReader, blockPos);
    }

    public MBlockReaderBridge() {
        super(MappedClasses.zJ);
        this.getBlockStateMethod = this.Y(GET_BLOCK_STATE_METHOD_NAME, true, MappedClasses.Zl, new Class[]{MappedClasses.lf});
    }
}

