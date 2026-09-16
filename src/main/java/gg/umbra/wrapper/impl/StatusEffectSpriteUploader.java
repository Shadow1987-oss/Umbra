package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MStatusEffectSpriteUploader;
import gg.umbra.wrapper.Wrapper;

public class StatusEffectSpriteUploader
extends Wrapper {
    public StatusEffectSpriteUploader(Object handle) {
        super(handle);
    }

    public TextureAtlasSprite getSprite(StatusEffect statusEffect) {
        return new TextureAtlasSprite(MStatusEffectSpriteUploader.getSprite(StatusEffectSpriteUploader.umbraInstance.getMappingsMapperCompat().statusEffectSpriteUploader, this.I, statusEffect.getObject()));
    }

    public TextureAtlasSprite getSprite(Holder holder) {
        return new TextureAtlasSprite(MStatusEffectSpriteUploader.getSprite(StatusEffectSpriteUploader.umbraInstance.getMappingsMapperCompat().statusEffectSpriteUploader, this.I, holder.getObject()));
    }

    public static StatusEffectSpriteUploader getPotionSprites() {
        Object uploaderHandle = MStatusEffectSpriteUploader.getPotionSprites(StatusEffectSpriteUploader.umbraInstance.getMappingsMapperCompat().statusEffectSpriteUploader, Minecraft.i());
        return new StatusEffectSpriteUploader(uploaderHandle);
    }
}
