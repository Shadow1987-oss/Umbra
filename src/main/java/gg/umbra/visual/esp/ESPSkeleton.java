package gg.umbra.visual.esp;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventRenderPlayerPost;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.visual.ESP;
import gg.umbra.visual.entity.RenderEntityContext;
import gg.umbra.visual.entity.RenderEntityContextCache;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.ModelBiped;
import gg.umbra.wrapper.impl.ModelBipedSkeletonBridge;
import gg.umbra.wrapper.impl.ModelRenderer;
import gg.umbra.wrapper.impl.Render;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.RenderPlayer;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ESPSkeleton
extends SubHack<ESP> {
    private final ESP parent = (ESP)this.getParent();

    @Listen
    public void onRenderPlayerPost(EventRenderPlayerPost event) {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        float radToDeg = 57.29578f;
        GL11.glPushMatrix();
        OpenGlBackendHolder.backend.disableCapability(2929);
        event.getEntityRenderer().B(0.0);
        double camX = RenderManager.getInterpolatedRenderPosX();
        double camY = RenderManager.getInterpolatedRenderPosY();
        double camZ = RenderManager.getInterpolatedRenderPosZ();
        Color color = this.parent.resolveEntityColor(event.getThePlayer(), entityPlayer.getObject());
        if (color != null) {
            RenderPlayer renderPlayer;
            ModelBipedSkeletonBridge modelBipedSkeletonBridge;
            RenderEntityContext renderEntityContext = RenderEntityContextCache.getOrCreate(entityPlayer, event.getThePlayer());
            if (renderEntityContext.isAttackable()) {
                color = Color.BLUE;
            }
            float partialTicks = Minecraft.getTimer().renderPartialTicks();
            double prevX = entityPlayer.M();
            double prevY = entityPlayer.W();
            double prevZ = entityPlayer.m$src$D$fwnne5();
            double renderX = prevX + (entityPlayer.z() - prevX) * (double)partialTicks - camX;
            double renderY = prevY + (entityPlayer.N() - prevY) * (double)partialTicks - camY;
            double renderZ = prevZ + (entityPlayer.h() - prevZ) * (double)partialTicks - camZ;
            boolean blendWasEnabled = GL11.glIsEnabled((int)3042);
            RenderUtil.d();
            GL11.glBlendFunc((int)770, (int)771);
            if (!blendWasEnabled) {
                OpenGlBackendHolder.backend.enableCapability(3042);
            }
            GL11.glBlendFunc((int)770, (int)771);
            RenderUtils.w(color);
            OpenGlBackendHolder.backend.disableCapability(2896);
            OpenGlBackendHolder.backend.enableCapability(2848);
            OpenGlBackendHolder.backend.disableCapability(3553);
            GL11.glTranslated((double)renderX, (double)renderY, (double)renderZ);
            Render render = this.parent.renderManager.getEntityRenderObject(entityPlayer);
            if (render.isInstance(MappedClasses.D0) && (modelBipedSkeletonBridge = (renderPlayer = new RenderPlayer(render.getObject())).getMainModel()).isNotNull() && modelBipedSkeletonBridge.isInstance(MappedClasses.zV)) {
                float distance = (float)renderEntityContext.getDistance();
                float lineWidth = Math.max(4.0f * ((100.0f - Math.min(distance, 100.0f)) / 100.0f), 0.1f);
                GL11.glLineWidth((float)lineWidth);
                ModelBiped modelBiped = new ModelBiped(modelBipedSkeletonBridge.getObject());
                boolean sneaking = renderEntityContext.isSneaking();
                float legOffset = sneaking ? 0.6f : 0.75f;
                float bodyRotation = entityPlayer.W$src$F$153nzpr();
                GL11.glRotatef((float)bodyRotation, (float)0.0f, (float)-999.0f, (float)0.0f);
                double sneakOffset = sneaking ? -0.2 : 0.0;
                GL11.glTranslated((double)-0.15, (double)legOffset, (double)sneakOffset);
                ModelRenderer rightLeg = modelBiped.getBipedRightLeg();
                float angleX = rightLeg.getRotateAngleX() * radToDeg;
                float angleY = rightLeg.getRotateAngleY() * radToDeg;
                float angleZ = rightLeg.getRotateAngleZ() * radToDeg;
                GL11.glRotatef((float)angleX, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleY), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleZ), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)(-legOffset), (double)0.0);
                GL11.glEnd();
                GL11.glRotatef((float)angleZ, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)angleY, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleX), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslated((double)0.3, (double)0.0, (double)0.0);
                ModelRenderer leftLeg = modelBiped.getBipedLeftLeg();
                angleX = leftLeg.getRotateAngleX() * radToDeg;
                angleY = leftLeg.getRotateAngleY() * radToDeg;
                angleZ = leftLeg.getRotateAngleZ() * radToDeg;
                GL11.glRotatef((float)angleX, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleY), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleZ), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)(-legOffset), (double)0.0);
                GL11.glEnd();
                GL11.glRotatef((float)angleZ, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)angleY, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleX), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslated((double)-0.15, (double)0.0, (double)0.0);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.15, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)-0.15, (double)0.0, (double)0.0);
                GL11.glEnd();
                if (sneaking) {
                    GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)0.65, (double)0.0);
                GL11.glEnd();
                GL11.glTranslated((double)0.0, (double)0.65, (double)0.0);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.35, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)-0.35, (double)0.0, (double)0.0);
                GL11.glEnd();
                GL11.glTranslated((double)-0.35, (double)0.0, (double)0.0);
                ModelRenderer rightArm = modelBiped.getBipedRightArm();
                angleX = rightArm.getRotateAngleX() * radToDeg;
                angleY = rightArm.getRotateAngleY() * radToDeg;
                angleZ = rightArm.getRotateAngleZ() * radToDeg;
                GL11.glRotatef((float)angleX, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleY), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleZ), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)-0.6, (double)0.0);
                GL11.glEnd();
                GL11.glRotatef((float)angleZ, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)angleY, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleX), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslated((double)0.7, (double)0.0, (double)0.0);
                ModelRenderer leftArm = modelBiped.getBipedLeftArm();
                angleX = leftArm.getRotateAngleX() * radToDeg;
                angleY = leftArm.getRotateAngleY() * radToDeg;
                angleZ = leftArm.getRotateAngleZ() * radToDeg;
                GL11.glRotatef((float)angleX, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleY), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleZ), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)-0.6, (double)0.0);
                GL11.glEnd();
                GL11.glRotatef((float)angleZ, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)angleY, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-angleX), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslated((double)-0.35, (double)0.0, (double)0.0);
                GL11.glRotatef((float)(-bodyRotation), (float)0.0f, (float)-999.0f, (float)0.0f);
                float headYaw = entityPlayer.J();
                float headPitch = entityPlayer.V();
                double headLength = 0.4;
                GL11.glRotated((double)headYaw, (double)0.0, (double)-999.0, (double)0.0);
                GL11.glRotated((double)headPitch, (double)999.0, (double)0.0, (double)0.0);
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)headLength, (double)0.0);
                GL11.glEnd();
                GL11.glBegin((int)1);
                GL11.glVertex3d((double)0.0, (double)headLength, (double)0.0);
                GL11.glVertex3d((double)0.0, (double)headLength, (double)0.25);
                GL11.glEnd();
                GL11.glRotated((double)headPitch, (double)999.0, (double)0.0, (double)0.0);
                GL11.glRotated((double)(-headYaw), (double)0.0, (double)999.0, (double)0.0);
            }
            if (!blendWasEnabled) {
                OpenGlBackendHolder.backend.disableCapability(3042);
            }
            OpenGlBackendHolder.backend.enableCapability(3553);
            OpenGlBackendHolder.backend.disableCapability(2848);
            OpenGlBackendHolder.backend.enableCapability(2896);
            RenderUtil.Y();
        }
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        event.getEntityRenderer().O(0.0);
        OpenGlBackendHolder.backend.enableCapability(2929);
        GL11.glPopMatrix();
    }


    public ESPSkeleton(HackModule parent, String name) {
        super(parent, name);
    }
}

