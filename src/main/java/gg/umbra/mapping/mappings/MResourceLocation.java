package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.utils.datas.BlockData;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MResourceLocation
extends Mapping {
    private MappingMethod createMethod;
    private final MappingMethod getPathMethod;

    public MResourceLocation() {
        this(BlockData.W());
    }

    private MResourceLocation(String[] resourceLocationMappingState) {
        super(MappedClasses.zC);
        if (resourceLocationMappingState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.getPathMethod = this.Y("getPath", true, String.class);
            } else {
                this.getPathMethod = this.Y("getResourcePath", true, String.class);
            }
            if (ForgeVersion.MC_1_21_0.d()) {
                this.createMethod = this.registerStaticMethod("parse", true, MappedClasses.zC, String.class);
            } else {
                this.createMethod = this.Y("<init>", false, Void.TYPE, String.class);
            }
            return;
        }
        this.getPathMethod = this.Y("getResourcePath", true, String.class);
        if (ForgeVersion.MC_1_21_0.d()) {
            this.registerStaticMethod("parse", true, MappedClasses.zC, String.class);
        }
        this.createMethod = this.Y("<init>", false, Void.TYPE, String.class);
    }

    public String getPath(Object resourceLocationHandle) {
        return (String)this.getPathMethod.invokeObject(resourceLocationHandle);
    }

    public Object create(String location) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.createMethod.invokeObject(null, location);
        }
        return this.createMethod.newInstance(location);
    }
}

