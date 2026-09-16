package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MGuiSpriteManager
extends Mapping {
    private final MappingMethod getSpriteMethod;

    public MGuiSpriteManager() {
        super(MappedClasses.D_);
        this.getSpriteMethod = this.Y("getSprite", true, MappedClasses.Db, new Class[]{MappedClasses.zC});
    }

    public Object getSprite(Object spriteManager, Object resourceLocation) {
        return this.getSpriteMethod.invokeObject(spriteManager, resourceLocation);
    }
}

