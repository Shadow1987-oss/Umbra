package gg.umbra.visual.esp;

import com.google.common.collect.Lists;
import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventPreRenderEntity;
import gg.umbra.event.impl.EventPreRenderLiving;
import gg.umbra.event.impl.EventPreRenderPlayerSpec;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.event.impl.EventSetArmorModel;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.visual.ESP;
import gg.umbra.render.OffscreenRenderContext;
import gg.umbra.utils.MutableColor;
import gg.umbra.utils.render.BufferedRenderPrimitives;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.StencilUtil;
import gg.umbra.wrapper.impl.AxisAlignedBB;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GlStateManager;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderLivingBase;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ESPOutline
extends SubHack<ESP> {
    private final ESP parentEsp = (ESP)this.getParent();
    private boolean renderingOutline;
    private boolean legacyRendererObserved;
    private int legacyRendererObservationCount;

    @Listen
    public void onPreRenderLiving(EventPreRenderLiving event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getEntity()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Umbra.INSTANCE.getEnemyManager().isEnemy(event.getEntity().getName()) : !Umbra.INSTANCE.getClientSettings().isValidTarget(event.getEntity(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        EntityPlayerSP viewer = event.getThePlayer();
        Entity entity = event.getEntity();
        if (entity.equals(viewer)) {
            return;
        }
        if (this.renderingOutline) {
            event.setCancelled(true);
        }
    }

    @Listen
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        if (Minecraft.gameSettings().M()) {
            this.parentEsp.mode.setValue(this.parentEsp.threeDimensionalMode);
            Umbra.INSTANCE.getNotificationManager().showInfo("ESP reverted to 3D mode", "Disable fast render to use outline", 5000L);
        }
    }

    public ESPOutline(HackModule parent, String name) {
        super(parent, name);
    }


    @Listen
    public void onSetArmorModel(EventSetArmorModel event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getEntity()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Umbra.INSTANCE.getEnemyManager().isEnemy(event.getEntity().getName()) : !Umbra.INSTANCE.getClientSettings().isValidTarget(event.getEntity(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (this.renderingOutline) {
            event.setResult(0);
            event.setCancelled(true);
        }
    }

    public boolean isRenderingOutline() {
        return this.renderingOutline;
    }

    @Listen
    public void onPreRenderEntity(EventPreRenderEntity event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (this.parentEsp.resolveEntityColor(event.getThePlayer(), event.getEntity()) == null) {
            return;
        }
        if (this.renderingOutline && ForgeVersion.MC_1_7_10.L()) {
            RenderUtils.g();
            RenderUtils.w(this.parentEsp.playerColor.getMutableColor());
            RenderUtils.f();
            return;
        }
        if (!event.getEntity().isInstance(MappedClasses.z5) && event.getEntity().isInstance(MappedClasses.Yl)) {
            this.legacyRendererObserved = true;
            ++this.legacyRendererObservationCount;
        }
    }

    @Listen
    public void onPreRenderPlayerSpec(EventPreRenderPlayerSpec event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getClientPlayer()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Umbra.INSTANCE.getEnemyManager().isEnemy(event.getClientPlayer().getName()) : !Umbra.INSTANCE.getClientSettings().isValidTarget(event.getClientPlayer(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (this.renderingOutline) {
            event.setCancelled(true);
        }
    }

    @Listen
    public void onRender3D(EventRender3D event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (ForgeVersion.MC_1_7_10.B() && (!this.legacyRendererObserved || this.legacyRendererObservationCount < 10)) {
            return;
        }
        // MC 1.17+ (with Iris/Sodium) uses OpenGL Core Profile, where legacy GL display lists,
        // glPushMatrix and glPolygonMode are unavailable and crash the JVM immediately.
        // Use the modern buffered render pipeline (same as ESP3D / HoleESP) there instead.
        if (GuiRenderPrimitives.d()) {
            this.renderBufferedOutline(event);
            return;
        }
        this.renderLegacyOutline(event);
    }

    private void renderBufferedOutline(EventRender3D event) {
        EntityPlayerSP viewer = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (viewer.isNull() || world.isNull()) {
            return;
        }
        RenderUtil.d();
        event.getEntityRenderer().B(1.0);
        RenderUtils.g();
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        for (Object entityHandle : world.z()) {
            Entity entity = new Entity(entityHandle);
            MutableColor color = this.parentEsp.resolveEntityColor(viewer, entity);
            if (color == null) {
                continue;
            }
            if (entity.equals(viewer) || !entity.isInstance(MappedClasses.Yl)) {
                continue;
            }
            double previousX = entity.M();
            double previousY = entity.W();
            double previousZ = entity.m$src$D$fwnne5();
            double renderX = previousX + (entity.z() - previousX) * (double)event.getTicks();
            double renderY = previousY + (entity.N() - previousY) * (double)event.getTicks();
            double renderZ = previousZ + (entity.h() - previousZ) * (double)event.getTicks();
            float expansion = entity.b();
            AxisAlignedBB bounds = entity.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl();
            AxisAlignedBB expanded = bounds.expand(expansion, expansion, expansion);
            BufferedRenderPrimitives.drawBoxOutline(
                    expanded.getMinX() - cameraX,
                    expanded.getMinY() - cameraY,
                    expanded.getMinZ() - cameraZ,
                    expanded.getMaxX() - cameraX,
                    expanded.getMaxY() - cameraY,
                    expanded.getMaxZ() - cameraZ,
                    color);
        }
        RenderUtils.f();
        event.getEntityRenderer().O(1.0);
        RenderUtil.Y();
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderLegacyOutline(EventRender3D event) {
        EntityPlayerSP viewer = event.getThePlayer();
        WorldClient world = event.getWorld();
        StencilUtil.ensureFramebufferStencilBuffer();
        int displayList = GL11.glGenLists((int)1);
        StencilUtil.getInstance().pushLayer();
        GL11.glPushMatrix();
        RenderUtils.g();
        boolean depthEnabled = GL11.glIsEnabled((int)2929);
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        boolean textureEnabled = GL11.glIsEnabled((int)3553);
        boolean lightingEnabled = GL11.glIsEnabled((int)2896);
        boolean lineSmoothingEnabled = GL11.glIsEnabled((int)2848);
        boolean alphaTestEnabled = GL11.glIsEnabled((int)3008);
        GlStateManager.disableDepth();
        StencilUtil.getInstance().configureLayerMask(true);
        GL11.glNewList((int)displayList, (int)4864);
        for (Object entityHandle : world.z()) {
            Entity entity = new Entity(entityHandle);
            MutableColor color = this.parentEsp.resolveEntityColor(event.getThePlayer(), entity);
            if (color == null || this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() == false ? !Umbra.INSTANCE.getClientSettings().isValidTarget(entity, false) : !Umbra.INSTANCE.getEnemyManager().isEnemy(entity.getName()))) continue;
            if (entity.equals(viewer) || !entity.isInstance(MappedClasses.Yl)) continue;
            double previousX = entity.M();
            double previousY = entity.W();
            double previousZ = entity.m$src$D$fwnne5();
            double renderX = previousX + (entity.z() - previousX) * (double)event.getTicks() - RenderManager.getInterpolatedRenderPosX();
            double renderY = previousY + (entity.N() - previousY) * (double)event.getTicks() - RenderManager.getInterpolatedRenderPosY();
            double renderZ = previousZ + (entity.h() - previousZ) * (double)event.getTicks() - RenderManager.getInterpolatedRenderPosZ();
            boolean entityWasInvisible = entity.J$src$Z$fdev5g();
            entity.q(false);
            GL11.glPushMatrix();
            GL11.glLineWidth((float)3.0f);
            OpenGlBackendHolder.backend.enableCapability(2848);
            GlStateManager.enableAlpha();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            event.getEntityRenderer().B(0.0);
            RenderLivingBase renderLivingBase = new RenderLivingBase(Minecraft.D().getEntityRenderObject(entity).getObject());
            if (renderLivingBase.isNotNull()) {
                this.renderingOutline = true;
                ArrayList arrayList = null;
                if (ForgeVersion.MC_1_7_10.Y()) {
                    List<Object> list = renderLivingBase.getLayerRenderers();
                    arrayList = Lists.newArrayList(list);
                    list.clear();
                }
                float[] colorComponents = RenderUtils.d(color.l());
                OpenGlBackendHolder.backend.setColor(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
                renderLivingBase.doRender(entity, renderX, renderY, renderZ, event.getTicks(), event.getTicks());
                if (ForgeVersion.MC_1_7_10.Y()) {
                    renderLivingBase.setLayerRenderers(arrayList);
                }
                this.renderingOutline = false;
            }
            entity.q(entityWasInvisible);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            event.getEntityRenderer().O(1.0);
            GL11.glPopMatrix();
        }
        GlStateManager.enableAlpha();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        event.getEntityRenderer().B(0.0);
        GL11.glEndList();
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glCallList((int)displayList);
        GL11.glPolygonMode((int)1032, (int)6912);
        GL11.glCallList((int)displayList);
        StencilUtil.getInstance().configureLayerMask(false);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glCallList((int)displayList);
        StencilUtil.getInstance().configureEqualLayerTest();
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glCallList((int)displayList);
        GL11.glPolygonMode((int)1032, (int)6912);
        GL11.glCallList((int)displayList);
        GL11.glPolygonMode((int)1032, (int)6914);
        StencilUtil.getInstance().popLayer();
        GlStateManager.r(2929, depthEnabled);
        GL11.glDeleteLists((int)displayList, (int)1);
        GlStateManager.r(2896, lightingEnabled);
        GlStateManager.r(3042, blendEnabled);
        GlStateManager.r(3553, textureEnabled);
        if (lineSmoothingEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2848);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2848);
        }
        GlStateManager.r(3008, alphaTestEnabled);
        event.getEntityRenderer().O(1.0);
        GL11.glPopMatrix();
        RenderUtils.f();
    }
}