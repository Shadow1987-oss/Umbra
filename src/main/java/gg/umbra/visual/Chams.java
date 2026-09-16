package gg.umbra.visual;

import com.google.common.collect.Lists;
import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreRenderEntity;
import gg.umbra.event.impl.EventPreRenderLiving;
import gg.umbra.event.impl.EventPreRenderPlayerSpec;
import gg.umbra.event.impl.EventRenderPlayerPost;
import gg.umbra.event.impl.EventRenderPlayerPre;
import gg.umbra.event.impl.EventSetArmorModel;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.Value;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GlStateManager;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Tessellator;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class Chams
extends HackModule {
    private static final int PASS_NONE = -1;
    private static final int PASS_OCCLUDED = 1;
    private static final int PASS_VISIBLE = 2;
    private static final int PASS_LEGACY = 3;
    private boolean renderingChamsPass;
    private EntityLivingBase temporarilyInvisiblePlayer;
    private final ColorPicker occludedColor;
    private final ColorPicker visibleColor;
    private final ToggleSetting colored;
    private final ToggleSetting colorBehindWalls;
    private final ToggleSetting hideBots = ToggleSetting.create(this, "Hide Bots", false, "Doesn't apply chams on bots.");
    private int renderPass = PASS_NONE;

    @Listen
    public void onPreRenderLiving(EventPreRenderLiving event) {
        if (!this.renderingChamsPass) {
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
        event.setCancelled(true);
    }

    @Listen
    public void onPreRenderEntity(EventPreRenderEntity event) {
        if (event.getEntity().isInstance(MappedClasses.Yl) && !event.getEntity().isInstance(MappedClasses.z5) && this.colored.getEffectiveValue().booleanValue() && this.renderingChamsPass) {
            if (this.renderPass == PASS_OCCLUDED) {
                RenderUtils.w(this.colorBehindWalls.getEffectiveValue() != false ? this.occludedColor.getMutableColor() : this.visibleColor.getMutableColor());
            }
            if (this.renderPass == PASS_VISIBLE) {
                RenderUtils.w(this.visibleColor.getMutableColor());
            }
        }
    }

    @Listen
    public void onRenderPlayerPost(EventRenderPlayerPost event) {
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getEntityPlayer()) && this.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.colored.getEffectiveValue().booleanValue()) {
            if (this.temporarilyInvisiblePlayer != null) {
                EntityPlayer player = event.getEntityPlayer();
                player.q(false);
                this.temporarilyInvisiblePlayer = null;
            }
            return;
        }
        if (event.getEntityPlayer().isInstance(MappedClasses.Yl) && !event.getEntityPlayer().isInstance(MappedClasses.z5)) {
            OpenGlBackendHolder.backend.disableCapability(32823);
            GL11.glPolygonOffset((float)1.0f, (float)2500000.0f);
        }
    }

    @Listen
    public void onPreRenderPlayerSpec(EventPreRenderPlayerSpec event) {
        if (event.getClientPlayer().isInstance(MappedClasses.z5)) {
            return;
        }
        if (!this.renderingChamsPass) {
            return;
        }
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        event.setCancelled(true);
    }

    @Listen
    public void onRenderPlayerPre(EventRenderPlayerPre event) {
        if (this.hideBots.getEffectiveValue().booleanValue() && Umbra.INSTANCE.getClientSettings().isBot(event.getEntityPlayer())) {
            return;
        }
        if (ClientSettings.isReservedEntity(event.getEntityPlayer())) {
            return;
        }
        ESP esp = Umbra.INSTANCE.getHackManager().getMod(ESP.class);
        if (this.renderingChamsPass || this.renderPass == PASS_LEGACY || esp.isEnabled() && esp.isOutlineModeActive()) {
            return;
        }
        if (!this.colored.getEffectiveValue().booleanValue()) {
            if (event.getEntityPlayer().isInstance(MappedClasses.Yl) && !event.getEntityPlayer().isInstance(MappedClasses.z5)) {
                OpenGlBackendHolder.backend.enableCapability(32823);
                GL11.glPolygonOffset((float)1.0f, (float)-2500000.0f);
            }
        } else if (event.getEntityPlayer().isNotNull() && event.getRenderer().isNotNull() && !event.getEntityPlayer().isInstance(MappedClasses.z5)) {
            if (ForgeVersion.MC_1_7_10.L()) {
                event.setCancelled(true);
            }
            EntityPlayer player = event.getEntityPlayer();
            double renderX = event.getX();
            double renderY = event.getY();
            double renderZ = event.getZ();
            float interpolatedYaw = player.j() + (player.J() - player.j()) * event.getPartialTicks();
            RenderUtil.d();
            RenderUtils.g();
            OpenGlBackendHolder.backend.disableCapability(2929);
            OpenGlBackendHolder.backend.disableCapability(3553);
            GlStateManager.disableLighting();
            RenderUtils.w(this.colorBehindWalls.getEffectiveValue() != false ? this.occludedColor.getMutableColor() : this.visibleColor.getMutableColor());
            ArrayList<Object> savedLayerRenderers = null;
            if (ForgeVersion.MC_1_7_10.Y()) {
                savedLayerRenderers = Lists.newArrayList(event.getRenderer().getLayerRenderers());
                event.getRenderer().getLayerRenderers().clear();
            }
            try {
                this.renderingChamsPass = true;
                this.renderPass = PASS_OCCLUDED;
                event.getRenderer().doRender(player, renderX, renderY, renderZ, interpolatedYaw, event.getPartialTicks());
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
            this.renderingChamsPass = false;
            OpenGlBackendHolder.backend.enableCapability(2929);
            OpenGlBackendHolder.backend.enableCapability(3553);
            if (ForgeVersion.MC_1_7_10.L()) {
                this.renderPass = PASS_LEGACY;
                GL11.glPushMatrix();
                OpenGlBackendHolder.backend.enableCapability(2896);
                event.getRenderer().doRender(player, renderX, renderY, renderZ, interpolatedYaw, event.getPartialTicks());
                GL11.glDepthMask((boolean)false);
                OpenGlBackendHolder.backend.disableCapability(2896);
                GL11.glPopMatrix();
            }
            this.renderingChamsPass = true;
            RenderUtils.w(this.visibleColor.getMutableColor());
            OpenGlBackendHolder.backend.disableCapability(3553);
            try {
                this.renderPass = PASS_VISIBLE;
                event.getRenderer().doRender(player, renderX, renderY, renderZ, interpolatedYaw, event.getPartialTicks());
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
            finally {
                this.renderingChamsPass = false;
            }
            if (ForgeVersion.MC_1_7_10.Y()) {
                event.getRenderer().setLayerRenderers(savedLayerRenderers);
            }
            OpenGlBackendHolder.backend.enableCapability(3553);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.enableLighting();
            RenderUtils.f();
            GL11.glPopMatrix();
            this.renderPass = PASS_NONE;
            if (!player.J$src$Z$fdev5g()) {
                this.temporarilyInvisiblePlayer = player;
                player.q(true);
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                Tessellator.getInstance().getWorldRenderer().Q(false);
            }
        }
    }

    public Chams() {
        super("Chams", -16711936, Category.RENDER, "Render players through walls.");
        this.colored = ToggleSetting.create(this, "Colored", false, "Colors entities.");
        this.visibleColor = ColorPicker.create(this, "Visible Color", new Color(255, 0, 0));
        this.colorBehindWalls = ToggleSetting.create(this, "Color Behind Walls", true, "Renders a different color when\nplayers are behind walls.");
        this.occludedColor = ColorPicker.create(this, "Invisible Color", new Color(255, 255, 0));
        this.addValue(new Value[]{this.hideBots, this.colored.addDependentValues(new Value[]{this.visibleColor, this.colorBehindWalls.addDependentValues(this.occludedColor)}), this.visibleColor, this.colorBehindWalls, this.occludedColor});
    }

    @Listen
    public void onSetArmorModel(EventSetArmorModel event) {
        if (this.renderingChamsPass) {
            if (Minecraft.theWorld().isNull()) {
                return;
            }
            event.setResult(0);
            event.setCancelled(true);
        }
    }
}
