package gg.umbra.visual;

import gg.umbra.Umbra;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.visual.entity.RenderEntityContext;
import gg.umbra.visual.entity.RenderEntityContextCache;
import gg.umbra.visual.esp.ESP2D;
import gg.umbra.visual.esp.ESP3D;
import gg.umbra.visual.esp.ESPOutline;
import gg.umbra.visual.esp.ESPSkeleton;
import gg.umbra.visual.esp.ESPSilhouette;
import gg.umbra.render.OffscreenRenderContext;
import gg.umbra.unmap.ModeOption;
import gg.umbra.utils.MutableColor;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.OptionSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderManager;
import java.awt.Color;

public class ESP
extends HackModule {
    public final ToggleSetting healthBar;
    private final ModeOption outlineMode;
    private final ToggleSetting showInvisibles;
    public final ToggleSetting showNameBackground;
    public final ToggleSetting enemyListOnly;
    public final ToggleSetting enemyOnly;
    public final ToggleSetting priorityOnly;
    private final ESPOutline outlineRenderer;
    public final OptionSetting mode;
    public final ToggleSetting useDisplayName;
    public final RenderManager renderManager;
    public final ModeOption threeDimensionalMode = new ESP3D(this, "3D").getSelectionValue();
    private final ModeOption skeletonMode;
    public final ToggleSetting showName;
    public final ColorPicker playerColor;
    public final ToggleSetting hideBots;
    private final ModeOption twoDimensionalMode;
    private final ModeOption silhouetteMode;
    public final SliderSetting silhouetteThickness;
    public final SliderSetting silhouetteFillAlpha;
    public final ToggleSetting silhouetteThroughWalls;
    public final ToggleSetting showExpandedHitbox;
    public final ToggleSetting showNormalHitbox;
    public final ToggleSetting showBoundingBox;

    public ESP() {
        super("ESP", -16711936, Category.RENDER, "Extra Sensory Perception\nRenders an ESP on players.");
        ESP2D esp2D = new ESP2D(this, "2D");
        this.twoDimensionalMode = esp2D.getSelectionValue();
        this.outlineRenderer = new ESPOutline(this, "Outline");
        this.outlineMode = this.outlineRenderer.getSelectionValue();
        this.skeletonMode = new ESPSkeleton(this, "Skeleton").getSelectionValue();
        this.silhouetteMode = new ESPSilhouette(this, "Silhouette").getSelectionValue();
        this.silhouetteThickness = SliderSetting.create((Object)this, "Thickness", "#.##", "x", 1.0, 1.03, 1.15, 0.01, "Silhouette outline thickness multiplier");
        this.silhouetteFillAlpha = SliderSetting.create((Object)this, "Fill Alpha", "#.##", "", 0.0, 1.0, 1.0, 0.05, "Opacity of the colored model fill");
        this.silhouetteThroughWalls = ToggleSetting.create(this, "Through Walls", true, "Renders the silhouette through walls");
        this.playerColor = ColorPicker.create(this, "Player Color", new Color(-14368924));
        this.showInvisibles = ToggleSetting.create(this, "Invisibles", false, "Show invisibles.");
        this.enemyOnly = ToggleSetting.create(this, "Enemy Only", false, "Only render enemies.");
        this.priorityOnly = ToggleSetting.create(this, "Priority Only", false, "Only shows the ESP box on friends/enemies.");
        this.enemyListOnly = ToggleSetting.create(this, "Enemies List Only", false);
        this.hideBots = ToggleSetting.create(this, "Hide Bots", false);
        this.showExpandedHitbox = ToggleSetting.create(this, "Hitbox", false, "Shows the current entity hitbox size.\n(HitBoxes expansion visible)\n(3D Only)");
        this.showNormalHitbox = ToggleSetting.create(this, "Show Normal", false, "Shows the true entity hitbox size.\n(3D Only)");
        this.showBoundingBox = ToggleSetting.create(this, "Bounding Box", true, "Shows an ESP box that wraps around the players BoundingBox.");
        this.healthBar = ToggleSetting.create(this, "Health Bar", false, "Shows a healthbar next to the ESP box");
        this.showName = ToggleSetting.create(this, "Name", false, "Shows a nametag above the ESP box");
        this.useDisplayName = ToggleSetting.create(this, "Use Displayname", false, "Shows the tab list display name.");
        this.showNameBackground = ToggleSetting.create(this, "Show Background", false, "Renders a box behind the text.");
        this.renderManager = Minecraft.D();
        this.mode = ForgeVersion.MC_1_17.d() ? OptionSetting.create((Object)this, "Mode", this.outlineMode, this.outlineMode, this.threeDimensionalMode, this.twoDimensionalMode, this.silhouetteMode) : (ForgeVersion.MC_1_12_2.d() ? OptionSetting.create((Object)this, "Mode", this.outlineMode, this.outlineMode, this.threeDimensionalMode, this.twoDimensionalMode, this.skeletonMode) : OptionSetting.create((Object)this, "Mode", this.outlineMode, this.outlineMode, this.threeDimensionalMode, this.twoDimensionalMode, this.skeletonMode));
        this.showExpandedHitbox.addDependentValues(this.showNormalHitbox);
        this.showBoundingBox.addDependentValues(this.priorityOnly);
        this.showName.addDependentValues(this.useDisplayName, this.showNameBackground);
        this.mode.whenEqualTo(this.outlineMode).applyTo(this.showBoundingBox, this.showExpandedHitbox, this.showNormalHitbox, this.priorityOnly);
        this.mode.whenEqualTo(this.twoDimensionalMode).applyTo(this.showBoundingBox, this.priorityOnly, this.healthBar, this.showName, this.useDisplayName, this.showNameBackground);
        this.mode.whenEqualTo(this.threeDimensionalMode).applyTo(this.showExpandedHitbox, this.showNormalHitbox);
        this.mode.whenEqualTo(this.silhouetteMode).applyTo(this.silhouetteThickness, this.silhouetteFillAlpha, this.silhouetteThroughWalls);
        this.addValue(this.playerColor, this.mode, this.showExpandedHitbox, this.showNormalHitbox, this.showBoundingBox, this.priorityOnly, this.healthBar, this.showName, this.useDisplayName, this.showNameBackground, this.showInvisibles, this.hideBots, this.silhouetteThickness, this.silhouetteFillAlpha, this.silhouetteThroughWalls);
    }


    public MutableColor resolveEntityColor(EntityPlayerSP viewer, Object entityHandle) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return null;
        }
        if (entityHandle == null) {
            return null;
        }
        Entity entity = new Entity(entityHandle);
        if (!entity.isInstance(MappedClasses.zm)) {
            return null;
        }
        if (entityHandle.equals(viewer)) {
            return null;
        }
        EntityLivingBase entityLivingBase = new EntityLivingBase(entityHandle);
        RenderEntityContext renderEntityContext = RenderEntityContextCache.getOrCreate(entityLivingBase, viewer);
        if (renderEntityContext.isSyntheticEntity()) {
            return null;
        }
        if (this.hideBots.getEffectiveValue().booleanValue() && renderEntityContext.isBot()) {
            return null;
        }
        if (!this.showInvisibles.getEffectiveValue().booleanValue() && renderEntityContext.isInvisibleWithoutEquipment()) {
            return null;
        }
        if (entityLivingBase.isInstance(MappedClasses.lG)) {
            MutableColor mutableColor = Umbra.INSTANCE.getClientSettings().resolveEntityColor(renderEntityContext);
            if (mutableColor == null) {
                mutableColor = this.playerColor.getMutableColor();
            }
            return new MutableColor(((Color)mutableColor).getRGB(), ((Color)mutableColor).getAlpha());
        }
        return null;
    }

    public boolean isOutlineModeActive() {
        return this.mode.getValue() == this.outlineMode && this.outlineRenderer.isRenderingOutline();
    }
}

