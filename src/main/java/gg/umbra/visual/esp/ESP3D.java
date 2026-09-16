package gg.umbra.visual.esp;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.hacks.exploits.HitBoxes;
import gg.umbra.visual.ESP;
import gg.umbra.utils.MutableColor;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.wrapper.impl.AxisAlignedBB;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.RenderManager;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ESP3D
extends SubHack<ESP> {
    private final ESP parentEsp = (ESP)this.getParent();
    private HitBoxes hitBoxes;

    @Listen
    public void onRender3D(EventRender3D event) {
        event.getEntityRenderer().B(1.0);
        RenderUtil.d();
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.setLineWidth(1.5f);
        OpenGlBackendHolder.backend.disableCapability(3553);
        OpenGlBackendHolder.backend.enableCapability(2848);
        OpenGlBackendHolder.backend.disableCapability(2929);
        OpenGlBackendHolder.backend.setDepthMask(false);
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        if (this.hitBoxes == null) {
            this.hitBoxes = Umbra.INSTANCE.getHackManager().getMod(HitBoxes.class);
        }
        for (Object entityHandle : event.getWorld().z()) {
            Entity entity = new Entity(entityHandle);
            MutableColor color = this.parentEsp.resolveEntityColor(event.getThePlayer(), entityHandle);
            if (Umbra.INSTANCE.getClientSettings().isBot(entity) && this.parentEsp.hideBots.getEffectiveValue().booleanValue() || color == null) {
                continue;
            }
            double previousX = entity.M();
            double previousY = entity.W();
            double previousZ = entity.m$src$D$fwnne5();
            double renderX = previousX + (entity.z() - previousX) * (double)event.getTicks();
            double renderY = previousY + (entity.N() - previousY) * (double)event.getTicks();
            double renderZ = previousZ + (entity.h() - previousZ) * (double)event.getTicks();
            float expansion = entity.b() + (this.parentEsp.showExpandedHitbox.getEffectiveValue().booleanValue() ? this.hitBoxes.getExpansionAmount() : 0.0f);
            AxisAlignedBB baseBounds = entity.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl();
            AxisAlignedBB expandedBounds = baseBounds.expand(expansion, expansion, expansion);
            RenderUtil.u(renderX - (expandedBounds.getMinX() - expandedBounds.getMaxX()) / 2.0, renderY + (expandedBounds.getMinY() - entity.N()), renderZ - (expandedBounds.getMinZ() - expandedBounds.getMaxZ()) / 2.0, expandedBounds.getMinZ() - expandedBounds.getMaxZ(), expandedBounds.getMaxY() - expandedBounds.getMinY(), expandedBounds.getMinX() - expandedBounds.getMaxX(), 0.1, color, null, cameraX, cameraY, cameraZ);
            if (!(this.hitBoxes.getExpansionAmount() > 0.0f) || !this.parentEsp.showExpandedHitbox.getEffectiveValue().booleanValue() || !this.parentEsp.showNormalHitbox.getEffectiveValue().booleanValue()) {
                continue;
            }
            AxisAlignedBB normalBounds = baseBounds.expand(entity.b(), entity.b(), entity.b());
            Color inverseColor = new Color(255 - ((Color)color).getRed(), 255 - ((Color)color).getGreen(), 255 - ((Color)color).getBlue(), 70);
            RenderUtil.u(renderX - (normalBounds.getMinX() - normalBounds.getMaxX()) / 2.0, renderY - (normalBounds.getMinY() - entity.N()), renderZ - (normalBounds.getMinZ() - normalBounds.getMaxZ()) / 2.0, normalBounds.getMinZ() - normalBounds.getMaxZ(), normalBounds.getMaxY() - normalBounds.getMinY(), normalBounds.getMinX() - normalBounds.getMaxX(), 0.1, inverseColor, null, cameraX, cameraY, cameraZ);
        }
        OpenGlBackendHolder.backend.setDepthMask(true);
        OpenGlBackendHolder.backend.enableCapability(2929);
        OpenGlBackendHolder.backend.enableCapability(3553);
        OpenGlBackendHolder.backend.disableCapability(2848);
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        RenderUtil.Y();
        event.getEntityRenderer().O(1.0);
    }

    public ESP3D(HackModule parent, String name) {
        super(parent, name);
    }

}
