package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MTextureAtlasSpriteInfoBridge
extends Mapping {
    private final MappingMethod texturePathMethod;

    public MTextureAtlasSpriteInfoBridge() {
        super(MappedClasses.zI);
        this.texturePathMethod = this.Y("texturePath", true, MappedClasses.zC, new Class[]{});
    }

    public Object getTexturePath(Object spriteInfo) {
        return this.texturePathMethod.invokeObject(spriteInfo, new Object[0]);
    }
}

