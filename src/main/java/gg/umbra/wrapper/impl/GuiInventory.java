package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.utils.render.BufferedGuiRenderPrimitives;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.RenderBatchBuilder;
import gg.umbra.utils.render.RenderBatchManager;
import gg.umbra.utils.render.RenderMatrix4f;
import gg.umbra.utils.render.RenderVector4f;
import gg.umbra.utils.render.VertexCoordinateMode;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.ForgeVersion;
import java.util.function.Supplier;

public class GuiInventory
extends Wrapper {
    private static Void renderQueuedEntity(RenderMatrix4f renderMatrix, int scale, float mouseX, float mouseY, EntityLivingBase entity) {
        Umbra.INSTANCE.getMappings().guiInventory.drawEntityOnScreen(
                (int)renderMatrix.elements[0],
                (int)renderMatrix.elements[5],
                (int)((double)scale * Umbra.INSTANCE.getClientSettings().getGuiScaleFactor()),
                mouseX,
                mouseY,
                entity.getObject());
        return null;
    }

    public static void drawEntityOnScreen(int screenX, int screenY, int scale, float mouseX, float mouseY, EntityLivingBase entity) {
        if (ForgeVersion.MC_1_20_6.d()) {
            Umbra.notifyNativeStackTrace();
        }
        if (GuiRenderPrimitives.d()) {
            RenderMatrix4f renderMatrix = new RenderMatrix4f(new RenderVector4f(screenX, screenY, 0.0f, 1.0f))
                    .multiply(BufferedGuiRenderPrimitives.matrixStack.peek());
            Supplier<Void> renderCallback = () -> GuiInventory.renderQueuedEntity(
                    renderMatrix, scale, mouseX, mouseY, entity);
            RenderBatchBuilder renderBatch = new RenderBatchBuilder(VertexCoordinateMode.MINECRAFT, false)
                    .setStandaloneRenderCallback(renderCallback);
            RenderBatchManager.getInstance().queueGuiBatch(renderBatch);
            return;
        }
        Umbra.INSTANCE.getMappings().guiInventory.drawEntityOnScreen(
                screenX, screenY, scale, mouseX, mouseY, entity.getObject());
    }

    public GuiInventory(Object inventoryScreenHandle) {
        super(inventoryScreenHandle);
    }

}

