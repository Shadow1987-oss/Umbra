package gg.umbra.rotation;

import gg.umbra.utils.render.BufferedGuiRenderPrimitives;
import gg.umbra.utils.render.RenderMatrix4f;
import gg.umbra.wrapper.impl.EntityRenderer;
import gg.umbra.wrapper.impl.GameSettings;
import gg.umbra.wrapper.impl.Matrix4f;
import gg.umbra.wrapper.impl.MatrixStack;
import gg.umbra.wrapper.impl.Minecraft;

public class LocalPlayerRotationUtil {

    public static void updateProjectionMatrix(float partialTicks) {
        Matrix4f viewMatrix;
        if (Minecraft.D().getActiveRenderInfo().isNull()) {
            return;
        }
        EntityRenderer renderer = Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf();
        GameSettings settings = Minecraft.gameSettings();
        Matrix4f projectionMatrix = renderer.l(renderer.l(), partialTicks, true);
        if (projectionMatrix.isNull()) {
            return;
        }
        MatrixStack matrixStack = MatrixStack.A();
        renderer.B(matrixStack, partialTicks);
        if (settings.k()) {
            renderer.Z(matrixStack, partialTicks);
        }
        if ((viewMatrix = matrixStack.F().getMatrix()).isNull()) {
            BufferedGuiRenderPrimitives.projectionMatrix = projectionMatrix.m$src$Lgg_umbra_utils_render_RenderMatrix4f_$1hodrum();
            BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
            return;
        }
        projectionMatrix.a(viewMatrix);
        BufferedGuiRenderPrimitives.projectionMatrix = projectionMatrix.m$src$Lgg_umbra_utils_render_RenderMatrix4f_$1hodrum();
        BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
    }

    public static void resetProjectionMatrix() {
        float left = 0.0f;
        float right = (float)Minecraft.p().I() / 2.0f;
        float bottom = (float)Minecraft.p().R() / 2.0f;
        float top = 0.0f;
        float nearPlane = -21000.0f;
        float farPlane = 21000.0f;
        BufferedGuiRenderPrimitives.projectionMatrix = new RenderMatrix4f().setIdentity().setOrthographic(left, right, bottom, top, farPlane, nearPlane);
        BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
    }
}

