package gg.umbra.utils.render;

import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.ResourceLocation;
import java.awt.Color;

public interface EntityModelRenderBackend {
    public void captureEntity(EntityLivingBase entity);

    public void render(float x, float y, int width, int height, Color color, float cornerRadius);

    public void captureTexture(ResourceLocation texture);

    public void dispose();
}
