package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.utils.datas.BlockData;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MMutableBoundingBox
extends Mapping {
    public MappingMethod constructor;
    public MappingMethod intersectsMethod;

    public boolean intersects(Object boundingBoxHandle, Object otherBoundingBoxHandle) {
        return this.intersectsMethod.invokeBoolean(boundingBoxHandle, otherBoundingBoxHandle);
    }

    public MMutableBoundingBox() {
        this(BlockData.W());
    }

    private MMutableBoundingBox(String[] boundingBoxMappingState) {
        super(MappedClasses.f);
        if (boundingBoxMappingState != null) {
            this.constructor = this.Y("<init>", false, Void.TYPE,
                    Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            if (ForgeVersion.MC_1_17.d()) {
                this.intersectsMethod = this.Y(
                        "m_71049_", ForgeVersion.MC_1_20_6.d(), Boolean.TYPE, MappedClasses.f);
            } else {
                this.intersectsMethod = this.Y("intersectsWith", true, Boolean.TYPE, MappedClasses.f);
            }
            return;
        }
        this.intersectsMethod = this.Y("<init>", false, Void.TYPE,
                Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        this.constructor = null;
    }


    public Object create(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return this.constructor.newInstance(minX, minY, minZ, maxX, maxY, maxZ);
    }
}

