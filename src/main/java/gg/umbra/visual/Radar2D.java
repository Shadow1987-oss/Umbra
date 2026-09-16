package gg.umbra.visual;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventRender2D;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.render.BufferedGuiRenderPrimitives;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.FontRenderer;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Radar2D
extends HackModule {
    private final ModeOption topLeftMode = new ModeOption("Top Left");
    private final ModeOption topRightMode = new ModeOption("Top Right");
    private final ModeOption bottomLeftMode = new ModeOption("Bottom Left");
    private final ModeOption bottomRightMode = new ModeOption("Bottom Right");
    private final OptionSetting position;
    private final SliderSetting radius;
    private final SliderSetting range;
    private final ToggleSetting showNames;
    private final ToggleSetting showSelf;
    private final ToggleSetting showFriends;
    private final ColorPicker playerColor;
    private final ColorPicker friendColor;
    private final ColorPicker selfColor;
    private final ColorPicker background;

    public Radar2D() {
        super("Radar 2D", 0, Category.RENDER, "Minimap with nearby players on it.");
        this.position = OptionSetting.create(this, "Position", "Where the radar is drawn on screen", (ModeSelection) this.topRightMode, this.topRightMode, this.topLeftMode, this.topRightMode, this.bottomLeftMode, this.bottomRightMode);
        this.radius = SliderSetting.create(this, "Radius", "#", "", 20.0, 60.0, 150.0, 5.0);
        this.range = SliderSetting.create(this, "Range", "#", "blocks", 10.0, 40.0, 200.0, 5.0);
        this.showNames = ToggleSetting.create(this, "Show names", true);
        this.showSelf = ToggleSetting.create(this, "Show self", true);
        this.showFriends = ToggleSetting.create(this, "Show friends", true, "Highlights friends in the friend color");
        this.playerColor = ColorPicker.create(this, "Player Color", new Color(255, 60, 60));
        this.friendColor = ColorPicker.create(this, "Friend Color", new Color(0, 255, 0));
        this.selfColor = ColorPicker.create(this, "Self Color", new Color(255, 255, 255));
        this.background = ColorPicker.createWithAlpha(this, "Background", new Color(0, 0, 0), 120);
        this.addValue(this.position, this.radius, this.range, this.showNames, this.showSelf, this.showFriends, this.playerColor, this.friendColor, this.selfColor, this.background);
    }

    @Override
    public String getId() {
        return "radar2d";
    }

    @Listen
    public void onRender2D(EventRender2D event) {
        EntityPlayerSP player = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (player.isNull() || world.isNull()) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            this.renderBuffered(event, player, world);
        } else {
            this.renderLegacy(event, player, world);
        }
    }

    private double[] computeRadarCenter(EventRender2D event, float radius) {
        int width = event.getDisplayWidth();
        int height = event.getDisplayHeight();
        int margin = 20;
        if (this.position.getValue() == this.topLeftMode) {
            return new double[]{(double) margin + (double) radius, (double) margin + (double) radius};
        }
        if (this.position.getValue() == this.bottomLeftMode) {
            return new double[]{(double) margin + (double) radius, (double) height - (double) margin - (double) radius};
        }
        if (this.position.getValue() == this.bottomRightMode) {
            return new double[]{(double) width - (double) margin - (double) radius, (double) height - (double) margin - (double) radius};
        }
        return new double[]{(double) width - (double) margin - (double) radius, (double) margin + (double) radius};
    }

    private List<EntityLivingBase> collectPlayers(EntityPlayerSP player, WorldClient world, float range) {
        ArrayList<EntityLivingBase> players = new ArrayList<EntityLivingBase>();
        for (Object handle : world.z()) {
            Entity entity = new Entity(handle);
            if (!entity.isInstance(MappedClasses.lG) || entity.equals(player)) {
                continue;
            }
            EntityLivingBase living = new EntityLivingBase(handle);
            if (living.M$src$Z$ff28xj() || living.w$src$F$15l9epb() <= 0.0f) {
                continue;
            }
            if (player.getDistanceToEntity(living) > (double) range) {
                continue;
            }
            players.add(living);
        }
        return players;
    }

    private void renderBuffered(EventRender2D event, EntityPlayerSP player, WorldClient world) {
        float radius = ((Double) this.radius.getValue()).floatValue();
        double[] center = this.computeRadarCenter(event, radius);
        float range = ((Double) this.range.getValue()).floatValue();
        Color background = this.background.getMutableColor();
        BufferedGuiRenderPrimitives.drawCircleStroke((float) center[0], (float) center[1], radius * 2.0f, 1.5f, 0.0f, background);
        BufferedGuiRenderPrimitives.fillRect(center[0] - (double) radius, center[1] - (double) radius, (double) (radius * 2.0f), (double) (radius * 2.0f), background);
        BufferedGuiRenderPrimitives.drawCircleStroke((float) center[0], (float) center[1], radius * 2.0f, 1.5f, 0.0f, new Color(255, 255, 255, 80));
        float yaw = (float) Math.toRadians(player.J());
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        for (EntityLivingBase target : this.collectPlayers(player, world, range)) {
            double dx = target.z() - player.z();
            double dz = target.h() - player.h();
            double rotatedX = dx * cos + dz * sin;
            double rotatedZ = -dx * sin + dz * cos;
            double scale = (double) radius / (double) range;
            double dotX = center[0] + rotatedX * scale;
            double dotY = center[1] + rotatedZ * scale;
            if (dotX < center[0] - (double) radius || dotX > center[0] + (double) radius || dotY < center[1] - (double) radius || dotY > center[1] + (double) radius) {
                continue;
            }
            boolean friend = Umbra.INSTANCE.getFriendManager().isFriend(target);
            Color color = friend && this.showFriends.getEffectiveValue().booleanValue() ? this.friendColor.getMutableColor() : this.playerColor.getMutableColor();
            BufferedGuiRenderPrimitives.fillRect(dotX - 1.5, dotY - 1.5, 3.0, 3.0, color);
            if (this.showNames.getEffectiveValue().booleanValue()) {
                FontRenderer fontRenderer = Minecraft.getFontRenderer();
                fontRenderer.drawStringWithShadow(target.getName(), dotX - (double) (fontRenderer.getStringWidth(target.getName()) / 2), dotY - 9.0, color.getRGB());
            }
        }
        if (this.showSelf.getEffectiveValue().booleanValue()) {
            Color selfColor = this.selfColor.getMutableColor();
            BufferedGuiRenderPrimitives.fillRect(center[0] - 2.0, center[1] - 2.0, 4.0, 4.0, selfColor);
        }
    }

    private void renderLegacy(EventRender2D event, EntityPlayerSP player, WorldClient world) {
        float radius = ((Double) this.radius.getValue()).floatValue();
        double[] center = this.computeRadarCenter(event, radius);
        float range = ((Double) this.range.getValue()).floatValue();
        Color background = this.background.getMutableColor();
        GuiRenderPrimitives.C(center[0] - (double) radius, center[1] - (double) radius, (double) (radius * 2.0f), (double) (radius * 2.0f), background);
        GuiRenderPrimitives.m((float) center[0], (float) center[1], radius * 2.0f, 1.5f, 0.0f, new Color(255, 255, 255, 80));
        float yaw = (float) Math.toRadians(player.J());
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        for (EntityLivingBase target : this.collectPlayers(player, world, range)) {
            double dx = target.z() - player.z();
            double dz = target.h() - player.h();
            double rotatedX = dx * cos + dz * sin;
            double rotatedZ = -dx * sin + dz * cos;
            double scale = (double) radius / (double) range;
            double dotX = center[0] + rotatedX * scale;
            double dotY = center[1] + rotatedZ * scale;
            if (dotX < center[0] - (double) radius || dotX > center[0] + (double) radius || dotY < center[1] - (double) radius || dotY > center[1] + (double) radius) {
                continue;
            }
            boolean friend = Umbra.INSTANCE.getFriendManager().isFriend(target);
            Color color = friend && this.showFriends.getEffectiveValue().booleanValue() ? this.friendColor.getMutableColor() : this.playerColor.getMutableColor();
            GuiRenderPrimitives.C(dotX - 1.5, dotY - 1.5, 3.0, 3.0, color);
            if (this.showNames.getEffectiveValue().booleanValue()) {
                FontRenderer fontRenderer = Minecraft.getFontRenderer();
                fontRenderer.drawStringWithShadow(target.getName(), dotX - (double) (fontRenderer.getStringWidth(target.getName()) / 2), dotY - 9.0, color.getRGB());
            }
        }
        if (this.showSelf.getEffectiveValue().booleanValue()) {
            Color selfColor = this.selfColor.getMutableColor();
            GuiRenderPrimitives.C(center[0] - 2.0, center[1] - 2.0, 4.0, 4.0, selfColor);
        }
    }
}
