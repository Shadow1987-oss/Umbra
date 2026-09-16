package gg.umbra.visual;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventEntityRenderState;
import gg.umbra.event.impl.EventPreRenderLiving;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.friend.FriendEntry;
import gg.umbra.friend.ui.OnlineRadarPreviewState;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.visual.nametags.NameTagsNameState;
import gg.umbra.visual.nametags.NameTagsRenderStateTracker;
import gg.umbra.worldmods.MurderFinder;
import gg.umbra.visual.entity.RenderEntityContext;
import gg.umbra.visual.entity.RenderEntityContextCache;
import gg.umbra.visual.hud.FreeLookHudModule;
import gg.umbra.unmap.NumberFormat;
import gg.umbra.utils.EnchantmentUtil;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.utils.MutableColor;
import gg.umbra.utils.RomanNumeralUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.utils.render.BufferedGuiRenderPrimitives;
import gg.umbra.utils.render.BufferedRenderPrimitives;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.ItemIconRenderer;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.PotionEffectIconRenderer;
import gg.umbra.utils.render.RenderMatrix4f;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ActiveRenderInfo;
import gg.umbra.wrapper.impl.Enchantment;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLiving;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EntityRenderer;
import gg.umbra.wrapper.impl.EnumHand;
import gg.umbra.wrapper.impl.FontRenderer;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GameSettings;
import gg.umbra.wrapper.impl.GlStateManager;
import gg.umbra.wrapper.impl.GuiContainer;
import gg.umbra.wrapper.impl.ITextComponent;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Matrix4f;
import gg.umbra.wrapper.impl.MatrixStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.PotionEffect;
import gg.umbra.wrapper.impl.PotionRegistry;
import gg.umbra.wrapper.impl.Quaternion;
import gg.umbra.wrapper.impl.RenderItemFontBridge;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.umbra.wrapper.impl.SharedMonsterAttributes;
import gg.umbra.wrapper.impl.Tessellator;
import gg.umbra.wrapper.impl.World;
import gg.umbra.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class EntityLabels
extends HackModule {
    private final ToggleSetting strengthIndicator;
    private final ToggleSetting ignoreInvisibles;
    private final NumberFormat numberFormat;
    private final ToggleSetting renderAnimals;
    private static final String RESET_COLOR = ClientSettings.FORMAT_CODE + "r";
    private final ToggleSetting animalsEffects;
    private static final String LESS_DAMAGE_LABEL;
    private final ToggleSetting mobsDistance;
    private final SliderSetting playersMaxDistance;
    private final ToggleSetting renderMobs;
    private final Color backgroundColor = new Color(20, 20, 20, 128);
    private final ToggleSetting playersDistance;
    private final ConcurrentHashMap<Integer, String> nameCache;
    private int nameCacheTickCounter = 0;
    private final ToggleSetting playersEffects;
    private static final String EQUAL_DAMAGE_LABEL;
    private static final String MORE_DAMAGE_LABEL;
    private final ToggleSetting calculateEffects;
    private final Enchantment[] displayedEnchantments;
    private final SliderSetting scale;
    private final SliderSetting animalsMaxDistance;
    private final ToggleSetting animalsHealth;
    private final ToggleSetting mobsEffects;
    private final Color enemyBackgroundColor = new Color(115, 0, 4, 128);
    private final ToggleSetting equipment;
    private final ToggleSetting hideBots;
    private final ToggleSetting renderPlayers;
    private final ToggleSetting playersHealth;
    private final ToggleSetting autoScale;
    private final ToggleSetting mobsHealth;
    private final ToggleSetting animalsDistance;
    private final SliderSetting mobsMaxDistance;
    private final SliderSetting opacity;

    private void drawScaledString(FontRenderer fontRenderer, String string, int n, int n2, double d, double d2) {
        int n3 = ((int)d2 & 0xFF) << 24 | 0xFFFFFF;
        double d3 = 1.0 / d;
        boolean bl = GL11.glIsEnabled((int)2896);
        if (bl) {
            OpenGlBackendHolder.backend.disableCapability(2896);
            OpenGlBackendHolder.backend.scale(d, d, d);
            fontRenderer.drawStringWithShadow(string, (double)n, (double)n2, n3);
            OpenGlBackendHolder.backend.scale(d3, d3, d3);
            OpenGlBackendHolder.backend.enableCapability(2896);
            return;
        }
        OpenGlBackendHolder.backend.scale(d, d, d);
        fontRenderer.drawStringWithShadow(string, (double)n, (double)n2, n3);
        OpenGlBackendHolder.backend.scale(d3, d3, d3);
    }

    private void renderEquipment(RenderManager renderManager, EntityRenderer entityRenderer, FontRenderer fontRenderer, EntityLivingBase entityLivingBase, float f, float f2, RenderEntityContext renderEntityContext, double d, MatrixStack matrixStack, @Nullable ItemStack itemStack, @Nullable ItemStack itemStack2, @Nullable ArrayList<ItemStack> arrayList) {
        double d2 = 1.1;
        double d3 = 1.0 / d2;
        if (ForgeVersion.MC_1_16_5.d()) {
            OpenGlBackendHolder.backend.rotate(f + 180.0f, 0.0f, -1.0f, 0.0f);
            OpenGlBackendHolder.backend.rotate(f2, -1.0f, 0.0f, 0.0f);
        }
        OpenGlBackendHolder.backend.scale(d2, d2, d2);
        boolean bl = ForgeVersion.MC_1_21_10.v();
        if (bl) {
            EntityPlayer entityPlayer;
            NameTagsNameState nameTagsNameState;
            if (entityLivingBase.isInstance(MappedClasses.Yl) && (nameTagsNameState = NameTagsRenderStateTracker.INSTANCE.getOrSchedule(entityPlayer = new EntityPlayer(entityLivingBase.getObject()))) != null) {
                int n = 0;
                if (itemStack != null && itemStack.P() > 1 && itemStack.t() > 1) {
                    n = itemStack.t();
                }
                double[] dArray = new double[(arrayList != null ? arrayList.size() : 0) + 1 + (itemStack2 != null ? 1 : 0)];
                dArray[0] = this.U(itemStack);
                if (arrayList != null) {
                    for (int i = 0; i < arrayList.size(); ++i) {
                        dArray[i + 1] = this.U(arrayList.get(i));
                    }
                }
                if (itemStack2 != null) {
                    dArray[dArray.length - 1] = this.U(itemStack2);
                }
                nameTagsNameState.render(-nameTagsNameState.getWidth() / 2.0, -26.0, n, dArray, matrixStack, renderManager, true);
            }
        } else {
            ItemStack itemStack3;
            Object object;
            int n = itemStack != null ? -8 : -16;
            int n2 = -26;
            if (itemStack2 != null) {
                n -= 8;
            }
            boolean bl2 = false;
            if (arrayList != null) {
                Iterator<ItemStack> iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    itemStack3 = iterator.next();
                    if (itemStack3 == null) continue;
                    n -= 8;
                    bl2 = true;
                }
            }
            GlStateManager.disableLighting();
            if (itemStack != null) {
                if (bl2) {
                    // empty if block
                }
                if (((Wrapper)(object = itemStack.getItem())).isNotNull()) {
                    ItemIconRenderer.renderItemStack(itemStack, (Item)object, n, n2, 16, 16, (float)d, 1.0f, true);
                    this.drawItemOverlay(fontRenderer, itemStack, (Item)object, n, n2, d);
                    if (itemStack.P() > 1) {
                        fontRenderer.drawStringWithShadow(String.valueOf(itemStack.t()), (double)(n + 8), (double)(n2 + 8), -1);
                    }
                }
            }
            if (arrayList != null) {
                for (int i = arrayList.size() - 1; i > -1; --i) {
                    itemStack3 = arrayList.get(i);
                    if (itemStack3 == null) continue;
                    ItemStack itemStack4 = ItemStack.S(itemStack3.getItem());
                    Item item = itemStack4.getItem();
                    ItemIconRenderer.renderItemStack(itemStack4, item, n += 16, n2, 16, 16, (float)d, 1.0f, true);
                    this.drawItemOverlay(fontRenderer, itemStack3, item, n, n2, d);
                }
            }
            if (itemStack2 != null) {
                n += 16;
                Item item = itemStack2.getItem();
                if (item.isNotNull()) {
                    ItemIconRenderer.renderItemStack(itemStack2, item, n, n2, 16, 16, (float)d, 1.0f, true);
                    this.drawItemOverlay(fontRenderer, itemStack2, item, n, n2, d);
                    if (itemStack2.P() > 1) {
                        fontRenderer.drawStringWithShadow(String.valueOf(itemStack2.t()), (double)(n + 8), (double)(n2 + 8), -1);
                    }
                }
            }
        }
        OpenGlBackendHolder.backend.scale(d3, d3, d3);
    }

    private static int lambda$onRenderWorldLast$0(OnlineRadarPreviewState onlineRadarPreviewState, OnlineRadarPreviewState onlineRadarPreviewState2) {
        return Double.compare(((RenderEntityContext)onlineRadarPreviewState2.getValue()).getDistance(), ((RenderEntityContext)onlineRadarPreviewState.getValue()).getDistance());
    }

    @Listen(skipCanceled=true)
    public void S(EventPreRenderLiving eventPreRenderLiving) {
        World world = eventPreRenderLiving.getWorld();
        if (world.isNull()) {
            return;
        }
        EntityPlayerSP entityPlayerSP = eventPreRenderLiving.getThePlayer();
        Entity entity = eventPreRenderLiving.getEntity();
        if (!this.isCurrentWorldEntity(world, entity) || this.shouldRenderContext(new EntityLivingBase(entity), world, entityPlayerSP) == null) {
            return;
        }
        eventPreRenderLiving.setCancelled(true);
    }

    static {
        EQUAL_DAMAGE_LABEL = ClientSettings.FORMAT_CODE + "e=";
        LESS_DAMAGE_LABEL = ClientSettings.FORMAT_CODE + "c-";
        MORE_DAMAGE_LABEL = ClientSettings.FORMAT_CODE + "a+";
    }

    private void renderNameTag(RenderManager renderManager, EntityRenderer entityRenderer, FontRenderer fontRenderer, EntityPlayerSP entityPlayerSP, float f, float f2, GameSettings gameSettings, EntityLivingBase entityLivingBase, RenderEntityContext renderEntityContext, double d, double d2, double d3, float f3, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, MatrixStack matrixStack) {
        RenderItemFontBridge renderItemFontBridge;
        Object object;
        Object object2;
        boolean bl6;
        ArrayList<ItemStack> arrayList;
        ItemStack object3;
        ItemStack itemStack;
        boolean bl7;
        double d4;
        block32: {
            d4 = (Double)this.opacity.getValue();
            if (renderEntityContext.canViewerSee()) {
                d4 = 1.0;
            }
            bl7 = entityLivingBase.isInstance(MappedClasses.Yl);
            itemStack = null;
            object3 = null;
            arrayList = new ArrayList();
            bl6 = false;
            if (bl3 && bl7) {
                try {
                    if (entityLivingBase == null || !entityLivingBase.isNotNull()) break block32;
                    try {
                        itemStack = renderEntityContext.getHeldItem();
                    }
                    catch (Exception exception) {
                        itemStack = null;
                    }
                    if (ForgeVersion.MC_1_12_2.d()) {
                        try {
                            ItemStack itemStack2 = entityLivingBase.i(EnumHand.offHand());
                            object3 = itemStack2 != null && itemStack2.isNotNull() ? itemStack2 : null;
                        }
                        catch (Exception exception) {
                            object3 = null;
                        }
                    }
                    try {
                        arrayList = this.collectEquipment(entityLivingBase);
                    }
                    catch (Exception exception) {
                        arrayList = new ArrayList();
                    }
                    bl6 = itemStack != null || object3 != null || !arrayList.isEmpty();
                }
                catch (Exception exception) {
                    itemStack = null;
                    object3 = null;
                    arrayList = new ArrayList();
                    bl6 = false;
                }
            }
        }
        if (!this.nameCache.containsKey(entityLivingBase.S())) {
            this.nameCache.put(entityLivingBase.S(), this.buildNameString(entityLivingBase, renderEntityContext, bl2, bl, bl4, bl5));
        }
        object2 = this.nameCache.get(entityLivingBase.S());
        MutableColor mutableColor = new MutableColor(renderEntityContext.isSneaking() ? this.enemyBackgroundColor : this.backgroundColor);
        MutableColor mutableColor2 = new MutableColor(mutableColor);
        int n = this.computeNameColor(entityLivingBase, renderEntityContext, mutableColor, mutableColor2, d4);
        mutableColor.withAlpha((int)((double)mutableColor.getAlpha() * d4));
        mutableColor2.withAlpha((int)((double)mutableColor2.getAlpha() * d4));
        MutableColor mutableColor3 = new MutableColor(n);
        mutableColor3.withAlpha((int)((double)mutableColor3.getAlpha() * d4));
        n = mutableColor3.l();
        float f4 = (float)(0.03333335 * (Double)this.scale.getValue());
        if (this.autoScale.getEffectiveValue().booleanValue()) {
            float f5 = f3;
            float f6 = (double)f5 / 5.0 <= 2.0 ? 2.0f : (float)((double)f5 / 5.0);
            f4 = (float)(0.01666666753590107 * ((double)f6 * (Double)this.scale.getValue()));
        }
        int n2 = fontRenderer.getStringWidth((String)object2) / 2;
        int n3 = -(fontRenderer.FONT_HEIGHT((String)object2) - 1);
        if (ForgeVersion.MC_1_16_5.d()) {
            RenderUtil.f(renderManager);
            if (gameSettings.x() == 0) {
                OpenGlBackendHolder.backend.translate(d, d2 + (double)renderEntityContext.getHeight() + 0.2, d3);
                OpenGlBackendHolder.backend.setNormal(0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(-f, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(-f2, -1.0f, 0.0f, 0.0f);
            } else {
                object = entityRenderer.l();
                double d5 = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosX() - ((ActiveRenderInfo)object).o().getX();
                double d6 = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosY() - ((ActiveRenderInfo)object).o().getY();
                double d7 = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosZ() - ((ActiveRenderInfo)object).o().getZ();
                OpenGlBackendHolder.backend.translate(d + d5, d2 + d6 + (double)renderEntityContext.getHeight() + (double)0.4f, d3 + d7);
                OpenGlBackendHolder.backend.setNormal(0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(-f, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(f2, 1.0f, 0.0f, 0.0f);
            }
        } else {
            OpenGlBackendHolder.backend.translate((float)(d + 0.0), (float)(d2 + (double)renderEntityContext.getHeight() + 0.5), (float)d3);
            OpenGlBackendHolder.backend.setNormal(0.0f, 1.0f, 0.0f);
            if (gameSettings.x() == 2) {
                OpenGlBackendHolder.backend.rotate(-f, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(f2, -1.0f, 0.0f, 0.0f);
            } else {
                OpenGlBackendHolder.backend.rotate(-f, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(f2, 1.0f, 0.0f, 0.0f);
            }
        }
        OpenGlBackendHolder.backend.scale(-f4, -f4, f4);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.Y();
        GlStateManager.disableTexture2D();
        RenderUtils.M((double)(-n2) - 2.0, n3, (double)n2 + 2.0, 2.0, 1.0, mutableColor, mutableColor2);
        GlStateManager.enableTexture2D();
        GlStateManager.disableDepth();
        if (ForgeVersion.MC_1_16_5.d() && (matrixStack == null || matrixStack.isNull() || !matrixStack.isInstance(MappedClasses.DQ))) {
            matrixStack = MatrixStack.A();
        }
        if (GuiRenderPrimitives.d()) {
            object = new RenderMatrix4f().setIdentity();
            ((RenderMatrix4f)object).multiply(BufferedGuiRenderPrimitives.viewMatrix);
            ((RenderMatrix4f)object).multiply(BufferedGuiRenderPrimitives.matrixStack.peek());
            if (ForgeVersion.MC_1_20_6.d()) {
                fontRenderer.h((String)object2, -n2, n3 + 2, n, false, (RenderMatrix4f)object, SharedMonsterAttributes.V());
            } else {
                int n4 = 0xF000F0;
                renderItemFontBridge = Minecraft.H$src$Lgg_umbra_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource();
                ScorePlayerTeamTextComponent scorePlayerTeamTextComponent = ScorePlayerTeamTextComponent.B((String)object2);
                fontRenderer.Z(scorePlayerTeamTextComponent, -n2, n3 + 2, n, false, ((RenderMatrix4f)object).toMinecraftMatrix(), renderItemFontBridge, true, 0, n4);
                renderItemFontBridge.q();
            }
        } else if (ForgeVersion.MC_1_16_5.d()) {
            matrixStack.H();
            object = renderManager.getCameraOrientation();
            matrixStack.i((Quaternion)object);
            matrixStack.i(Quaternion.fromEulerAngles(180.0f, 0.0f, 180.0f, true));
            ScorePlayerTeamTextComponent scorePlayerTeamTextComponent = ScorePlayerTeamTextComponent.B((String)object2);
            renderItemFontBridge = RenderItemFontBridge.V(Tessellator.getInstance().getWorldRenderer());
            int n5 = 0xF000F0;
            Matrix4f wrapper = matrixStack.F().getMatrix();
            fontRenderer.Z(scorePlayerTeamTextComponent, -n2, n3 + 2, n, false, wrapper, renderItemFontBridge, true, 0, n5);
            renderItemFontBridge.q();
            matrixStack.U();
        } else {
            fontRenderer.drawString((String)object2, (double)(-n2), (double)(n3 + 2), n);
        }
        boolean bl8 = false;
        if (bl5) {
            List<PotionEffect> list = renderEntityContext.getPotionEffects();
            int n6 = -(list.size() * 10) - 5;
            for (PotionEffect potionEffect : list) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                Minecraft.getTextureManager().bindTexture(GuiContainer.m$src$Lgg_umbra_wrapper_impl_ResourceLocation_$1fc62cj());
                OpenGlBackendHolder.backend.scale(0.5, 0.5, 0.5);
                if (ForgeVersion.MC_1_16_5.d()) {
                    matrixStack.H();
                    matrixStack.i(renderManager.getCameraOrientation());
                }
                PotionEffectIconRenderer.render(potionEffect, n6 + 6, n3 - 30, 18, 18, 1.0f, true);
                fontRenderer.V(RomanNumeralUtil.toRoman(potionEffect.L() + 1), n6 + 6, n3 - 30, -1, matrixStack);
                n6 += 20;
                bl8 = true;
                if (ForgeVersion.MC_1_16_5.d()) {
                    matrixStack.U();
                }
                OpenGlBackendHolder.backend.scale(2.0, 2.0, 2.0);
            }
        }
        if (bl3 && bl7 && bl6) {
            RenderUtil.f(renderManager);
            if (bl8) {
                OpenGlBackendHolder.backend.translate(0.0, -8.0, 0.0);
            }
            this.renderEquipment(renderManager, entityRenderer, fontRenderer, entityLivingBase, f, f2, renderEntityContext, d4, matrixStack, itemStack, (ItemStack)object3, arrayList);
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public String getId() {
        return "nametags";
    }

    public EntityLabels() {
        super("NameTags", -16711936, Category.RENDER, "Renders nametags on entities through walls.");
        this.ignoreInvisibles = ToggleSetting.create(this, "Ignore Invisibles", false, "Determines if we draw a nametag\nfor invisible entities");
        this.autoScale = ToggleSetting.create(this, "Auto Scale", true, "Automatically scales up nametags\nthe further the distance.");
        this.hideBots = ToggleSetting.create(this, "Hide bots", true, "Hides bots if you're using antibot");
        this.renderPlayers = ToggleSetting.create(this, "Render Players", true);
        this.playersHealth = ToggleSetting.create(this, "Health", false);
        this.playersDistance = ToggleSetting.create(this, "Distance", false);
        this.equipment = ToggleSetting.create(this, "Equipment", false);
        this.playersEffects = ToggleSetting.create(this, "Effects", false);
        this.playersMaxDistance = SliderSetting.create(this, "Max Distance", "#", "m", 0.0, 0.0, 250.0, 1.0, "Maximum distance allowed to render.\nUse 0 to render at any distance.");
        this.strengthIndicator = ToggleSetting.create(this, "Strength Indicator", false, "Gives you an indicator of your enemies\npossible damage relative to yours.\n    " + ClientSettings.FORMAT_CODE + "a+ " + RESET_COLOR + "Enemy deals less damage than you\n    " + ClientSettings.FORMAT_CODE + "e= " + RESET_COLOR + "Enemy deals equal damage to you\n    " + ClientSettings.FORMAT_CODE + "c- " + RESET_COLOR + "Enemy deals more damage than you");
        this.calculateEffects = ToggleSetting.create(this, "Calculate Effects", false, "Calculates potion effects to determine\ntotal possible damage. (Strength)");
        this.renderAnimals = ToggleSetting.create(this, "Render Animals", false);
        this.animalsHealth = ToggleSetting.create(this, "Health", false);
        this.animalsDistance = ToggleSetting.create(this, "Distance", false);
        this.animalsEffects = ToggleSetting.create(this, "Effects", false);
        this.animalsMaxDistance = SliderSetting.create(this, "Max Distance", "#", "m", 0.0, 0.0, 250.0, 1.0, "Maximum distance allowed to render.\nUse 0 to render at any distance.");
        this.renderMobs = ToggleSetting.create(this, "Render Mobs", false);
        this.mobsHealth = ToggleSetting.create(this, "Health", false);
        this.mobsDistance = ToggleSetting.create(this, "Distance", false);
        this.mobsEffects = ToggleSetting.create(this, "Effects", false);
        this.mobsMaxDistance = SliderSetting.create(this, "Max Distance", "#", "m", 0.0, 0.0, 250.0, 1.0, "Maximum distance allowed to render.\nUse 0 to render at any distance.");
        this.scale = SliderSetting.create((Object)this, "Scale", "#.#", "", 0.1, 1.0, 1.5, 0.1);
        this.opacity = SliderSetting.create(this, "Opacity", "#.#", "", 0.0, 1.0, 1.0);
        this.numberFormat = new NumberFormat(1);
        this.displayedEnchantments = new Enchantment[]{Enchantment.protection(), Enchantment.unbreaking(), Enchantment.sharpness(), Enchantment.fireAspect(), Enchantment.efficiency(), Enchantment.featherFalling(), Enchantment.power(), Enchantment.flame(), Enchantment.punch(), Enchantment.fortune(), Enchantment.infinity(), Enchantment.thorns(), Enchantment.knockback()};
        this.nameCache = new ConcurrentHashMap();
        this.renderPlayers.addDependentValues(this.playersHealth, this.playersDistance, this.playersEffects, this.playersMaxDistance, this.equipment, this.strengthIndicator);
        this.renderAnimals.addDependentValues(this.animalsHealth, this.animalsDistance, this.animalsEffects, this.animalsMaxDistance);
        this.renderMobs.addDependentValues(this.mobsHealth, this.mobsDistance, this.mobsEffects, this.mobsMaxDistance);
        this.strengthIndicator.addDependentValues(this.calculateEffects);
        this.addValue(this.ignoreInvisibles, this.autoScale, this.scale, this.hideBots, this.renderPlayers, this.playersHealth, this.playersDistance, this.playersEffects, this.playersMaxDistance, this.equipment, this.strengthIndicator, this.calculateEffects, this.renderAnimals, this.animalsHealth, this.animalsDistance, this.animalsEffects, this.animalsMaxDistance, this.renderMobs, this.mobsHealth, this.mobsDistance, this.mobsEffects, this.mobsMaxDistance);
    }

    private String buildNameString(EntityLivingBase entityLivingBase, RenderEntityContext renderEntityContext, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        float f;
        double d;
        float f2;
        double d2;
        double d3;
        String string = renderEntityContext.getTypeName();
        String string2 = ClientSettings.FORMAT_CODE + "a" + ClientSettings.FORMAT_CODE + "r" + string;
        boolean bl5 = entityLivingBase.isInstance(MappedClasses.Yl);
        if (bl5) {
            float f3;
            double d4;
            float f4;
            double d5;
            double d6;
            FriendEntry friendEntry = Umbra.INSTANCE.getFriendManager().findTargetedFriend(renderEntityContext.getName());
            if (friendEntry != null) {
                string2 = friendEntry.getDisplayName();
                if (!Umbra.INSTANCE.getFriendManager().recolorVisuals.getEffectiveValue().booleanValue() && !Umbra.INSTANCE.getFriendManager().recolorVisuals.getEffectiveValue().booleanValue()) {
                    int n;
                    char[] cArray = string.toCharArray();
                    for (int i = n = string.indexOf(string2); i > 0; --i) {
                        char c = cArray[i];
                        if (c != '\u00a7') continue;
                        c = cArray[i + 1];
                        String string3 = String.valueOf(c);
                        string2 = '\u00a7' + string3 + string2;
                        break;
                    }
                }
            }
            if (bl) {
                string2 = ClientSettings.FORMAT_CODE + "a[" + ClientSettings.FORMAT_CODE + "f" + (int)renderEntityContext.getDistance() + ClientSettings.FORMAT_CODE + "a]" + ClientSettings.FORMAT_CODE + "r " + string2;
            }
            if (renderEntityContext.getModelPlayer().isCreativeMode()) {
                string2 = ClientSettings.FORMAT_CODE + "a[C] " + ClientSettings.FORMAT_CODE + "r" + string2;
            }
            String string4 = (d6 = 100.0 * ((d5 = (double)((f4 = entityLivingBase.w$src$F$15l9epb()) / 2.0f)) / (d4 = (double)(entityLivingBase.I$src$F$14vyvep() / 2.0f)))) > 75.0 ? "2" : (d6 > 50.0 ? "e" : (d6 > 25.0 ? "6" : "4"));
            String string5 = this.numberFormat.format(Math.floor((d5 + 0.25) / 0.5) * 0.5);
            if (bl2) {
                string2 = String.format("%s %s%s%s", string2, ClientSettings.FORMAT_CODE, string4, string5);
            }
            if (bl4 && (f3 = renderEntityContext.getHealth()) > 0.0f) {
                String string6 = this.numberFormat.format(Math.floor(((double)f3 + 0.25) / 0.5) * 0.5);
                string2 = String.format("%s %s%s%s", string2, ClientSettings.FORMAT_CODE, "6", string6);
            }
            if (bl3) {
                string2 = String.format("%s %s", string2, renderEntityContext.getNameTag());
            }
            return string2;
        }
        if (bl) {
            string2 = ClientSettings.FORMAT_CODE + "a[" + ClientSettings.FORMAT_CODE + "f" + (int)renderEntityContext.getDistance() + ClientSettings.FORMAT_CODE + "a]" + ClientSettings.FORMAT_CODE + "r " + string2;
        }
        String string7 = (d3 = 100.0 * ((d2 = (double)((f2 = entityLivingBase.w$src$F$15l9epb()) / 2.0f)) / (d = (double)(entityLivingBase.I$src$F$14vyvep() / 2.0f)))) > 75.0 ? "2" : (d3 > 50.0 ? "e" : (d3 > 25.0 ? "6" : "4"));
        String string8 = this.numberFormat.format(Math.floor((d2 + 0.25) / 0.5) * 0.5);
        if (bl2) {
            string2 = String.format("%s %s%s%s", string2, ClientSettings.FORMAT_CODE, string7, string8);
        }
        if (bl4 && (f = renderEntityContext.getHealth()) > 0.0f) {
            String string9 = this.numberFormat.format(Math.floor(((double)f + 0.25) / 0.5) * 0.5);
            string2 = String.format("%s %s%s%s", string2, ClientSettings.FORMAT_CODE, "6", string9);
        }
        if (bl3) {
            string2 = String.format("%s %s", string2, renderEntityContext.getNameTag());
        }
        return string2;
    }

    @Listen
    public void onRender3D(EventRender3D eventRender3D) {
        if (eventRender3D.getWorld().isNull() || eventRender3D.getThePlayer().isNull()) {
            return;
        }
        boolean bl = OpenGlBackendHolder.backend.isCapabilityEnabled(2884);
        EntityPlayerSP entityPlayerSP = eventRender3D.getThePlayer();
        WorldClient worldClient = eventRender3D.getWorld();
        double d = RenderManager.getInterpolatedRenderPosX();
        double d2 = RenderManager.getInterpolatedRenderPosY();
        double d3 = RenderManager.getInterpolatedRenderPosZ();
        double d4 = entityPlayerSP.M() + (entityPlayerSP.z() - entityPlayerSP.M()) * (double)eventRender3D.getTicks() - d;
        double d5 = entityPlayerSP.W() + (entityPlayerSP.N() - entityPlayerSP.W()) * (double)eventRender3D.getTicks() - d2;
        double d6 = entityPlayerSP.m$src$D$fwnne5() + (entityPlayerSP.h() - entityPlayerSP.m$src$D$fwnne5()) * (double)eventRender3D.getTicks() - d3;
        ArrayList<OnlineRadarPreviewState<Object, RenderEntityContext>> arrayList = new ArrayList<OnlineRadarPreviewState<Object, RenderEntityContext>>();
        for (Object e : worldClient.z()) {
            EntityLivingBase entityLivingBase = new EntityLivingBase(new Entity(e));
            RenderEntityContext renderEntityContext = this.shouldRenderContext(entityLivingBase, worldClient, entityPlayerSP);
            if (renderEntityContext == null) continue;
            arrayList.add(OnlineRadarPreviewState.create(entityLivingBase, renderEntityContext));
        }
        arrayList.sort(EntityLabels::lambda$onRenderWorldLast$0);
        GameSettings gameSettings = Minecraft.gameSettings();
        float f = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderPitch() : eventRender3D.getRenderManager().getPlayerViewX();
        float f2 = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderYaw() : eventRender3D.getRenderManager().getPlayerViewY();
        GuiRenderPrimitives.U = true;
        for (OnlineRadarPreviewState onlineRadarPreviewState : arrayList) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)onlineRadarPreviewState.getKey();
            RenderEntityContext renderEntityContext = (RenderEntityContext)onlineRadarPreviewState.getValue();
            double d7 = entityLivingBase.M();
            double d8 = entityLivingBase.W();
            double d9 = entityLivingBase.m$src$D$fwnne5();
            double d10 = d7 + (entityLivingBase.z() - d7) * (double)eventRender3D.getTicks() - d;
            double d11 = d8 + (entityLivingBase.N() - d8) * (double)eventRender3D.getTicks() - d2;
            double d12 = d9 + (entityLivingBase.h() - d9) * (double)eventRender3D.getTicks() - d3;
            float f3 = (float)RotationUtil.y(d10, d11, d12, d4, d5, d6);
            OpenGlBackendHolder.backend.pushMatrix();
            try {
                if (entityLivingBase.isInstance(MappedClasses.Yl) && this.renderPlayers.getEffectiveValue().booleanValue()) {
                    this.renderNameTag(eventRender3D.getRenderManager(), eventRender3D.getEntityRenderer(), eventRender3D.getFontRenderer(), entityPlayerSP, f, f2, gameSettings, entityLivingBase, renderEntityContext, d10, d11, d12, f3, this.playersHealth.getEffectiveValue(), this.playersDistance.getEffectiveValue(), this.equipment.getEffectiveValue(), this.strengthIndicator.getEffectiveValue(), this.playersEffects.getEffectiveValue(), eventRender3D.getMatrixStack());
                } else if (RotationUtil.m(entityLivingBase) && this.renderAnimals.getEffectiveValue().booleanValue()) {
                    this.renderNameTag(eventRender3D.getRenderManager(), eventRender3D.getEntityRenderer(), eventRender3D.getFontRenderer(), entityPlayerSP, f, f2, gameSettings, entityLivingBase, renderEntityContext, d10, d11, d12, f3, this.animalsHealth.getEffectiveValue(), this.animalsDistance.getEffectiveValue(), false, false, this.animalsEffects.getEffectiveValue(), eventRender3D.getMatrixStack());
                } else if (RotationUtil.W(entityLivingBase) && this.renderMobs.getEffectiveValue().booleanValue()) {
                    this.renderNameTag(eventRender3D.getRenderManager(), eventRender3D.getEntityRenderer(), eventRender3D.getFontRenderer(), entityPlayerSP, f, f2, gameSettings, entityLivingBase, renderEntityContext, d10, d11, d12, f3, this.mobsHealth.getEffectiveValue(), this.mobsDistance.getEffectiveValue(), false, false, this.mobsEffects.getEffectiveValue(), eventRender3D.getMatrixStack());
                }
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
            OpenGlBackendHolder.backend.popMatrix();
        }
        if (bl) {
            GlStateManager.L();
        }
        GuiRenderPrimitives.U = false;
    }

    private int computeNameColor(EntityLivingBase entityLivingBase, RenderEntityContext renderEntityContext, MutableColor mutableColor, MutableColor mutableColor2, double d) {
        int n = 0xFFFFFF;
        if (Umbra.INSTANCE.getFriendManager().recolorVisuals.getEffectiveValue().booleanValue()) {
            boolean bl = renderEntityContext.isFriend();
            if (renderEntityContext.isAttackable() || bl) {
                n = -12417292;
                mutableColor2.setRed(36);
                mutableColor2.setGreen(255);
                mutableColor2.setBlue(255);
                mutableColor2.withAlpha((int)(64.0 * d));
            }
            if (renderEntityContext.isEnemy()) {
                n = -12417292;
                mutableColor2.setRed(255);
                mutableColor2.setGreen(29);
                mutableColor2.setBlue(29);
                mutableColor2.withAlpha((int)(128.0 * d));
            }
            if (bl) {
                n = Umbra.INSTANCE.getFriendManager().friendColor.toRgb();
                mutableColor2.setColor(Umbra.INSTANCE.getFriendManager().friendColor.getMutableColor());
            }
        }
        if (Umbra.INSTANCE.getHackManager().getMod(MurderFinder.class).isMurderer(entityLivingBase)) {
            n = -59882;
        }
        if (renderEntityContext.isInvisible()) {
            n = 65530;
        }
        return n;
    }

    private void drawItemOverlay(FontRenderer fontRenderer, ItemStack itemStack, Item item, int n, int n2, double d) {
        double d2;
        double d3;
        int n3;
        int n4;
        try {
            Map<Enchantment, Short> map = EnchantmentUtil.A(itemStack);
            int n5 = 0;
            block2: for (Map.Entry<Enchantment, Short> entry : map.entrySet()) {
                Enchantment enchantment = entry.getKey();
                n4 = entry.getValue().shortValue();
                for (Enchantment enchantment2 : this.displayedEnchantments) {
                    if (!enchantment.equals(enchantment2)) continue;
                    String string = enchantment.getTranslatedName(n4).substring(0, 1).toLowerCase();
                    string = n4 > 99 ? string + "99+" : string + n4;
                    double d4 = 0.7;
                    double d5 = 1.0 / d4;
                    this.drawScaledString(fontRenderer, string, (int)((double)n * d5), (int)((double)(-2 + (n2 + n5)) * d5), d4, d);
                    n5 += 6;
                    continue block2;
                }
            }
        }
        catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        if ((n3 = item.a()) > 0 && (d3 = 1.0 - (d2 = (double)itemStack.L() / (double)n3)) < 1.0) {
            GuiRenderPrimitives.U = false;
            n4 = (int)Math.round(255.0 - d2 * 255.0);
            if (GuiRenderPrimitives.d()) {
                BufferedRenderPrimitives.fillRect(n + 2, n2 + 13, 13.0f, 2.0f, Color.BLACK);
                BufferedRenderPrimitives.fillRect(n + 2, n2 + 13, 12.0f, 1.0f, new Color((255 - n4) / 4, 64, 0, 255));
                BufferedRenderPrimitives.fillRect(n + 2, n2 + 13, (float)(13.0 * d3), 1.0f, RenderUtils.S((float)d3));
            } else {
                GuiRenderPrimitives.y(n + 2, n2 + 13, 13.0f, 2.0f, Color.BLACK);
                GuiRenderPrimitives.y(n + 2, n2 + 13, 12.0f, 1.0f, new Color((255 - n4) / 4, 64, 0, 255));
                GuiRenderPrimitives.C(n + 2, n2 + 13, 13.0 * d3, 1.0, RenderUtils.S((float)d3));
            }
            GuiRenderPrimitives.U = true;
        }
    }

    double U(ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0;
        }
        Item item = itemStack.getItem();
        int n = item.a();
        if (n > 0) {
            return (double)itemStack.L() / (double)n;
        }
        return 0.0;
    }

    private ArrayList<ItemStack> collectEquipment(EntityLivingBase entityLivingBase) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (entityLivingBase == null || entityLivingBase.isNull()) {
            return arrayList;
        }
        if (entityLivingBase.isInstance(MappedClasses.zQ)) {
            for (ItemStack itemStack : new EntityLiving(entityLivingBase.getObject()).I$src$Ljava_util_ArrayList_$15zosdi()) {
                if (itemStack == null || itemStack.isNull()) continue;
                arrayList.add(itemStack);
            }
            return arrayList;
        }
        if (entityLivingBase.isInstance(MappedClasses.Yl)) {
            Object[] objectArray;
            EntityPlayer entityPlayer = new EntityPlayer(entityLivingBase.getObject());
            for (Object object : objectArray = entityPlayer.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().i()) {
                ItemStack itemStack;
                if (object == null || (itemStack = new ItemStack(object)).isNull()) continue;
                arrayList.add(itemStack);
            }
        }
        return arrayList;
    }

    @Listen
    public void onTick(EventPreTick eventPreTick) {
        if (++this.nameCacheTickCounter >= 20) {
            this.nameCacheTickCounter = 0;
            this.nameCache.clear();
        }
    }

    private boolean isCurrentWorldEntity(World world, Entity entity) {
        if (world.isNull() || entity.isNull()) {
            return false;
        }
        Entity entity2 = world.V(entity.S());
        return entity2 != null && entity2.isNotNull() && entity2.getObject() == entity.getObject();
    }

    private RenderEntityContext shouldRenderContext(EntityLivingBase entityLivingBase, World world, EntityPlayerSP entityPlayerSP) {
        if (entityLivingBase.isNull() || entityPlayerSP.isNull() || world.isNull()) {
            return null;
        }
        if (MappedClasses.FT != null && entityLivingBase.isInstance(MappedClasses.FT)) {
            return null;
        }
        if (!entityLivingBase.isInstance(MappedClasses.zm)) {
            return null;
        }
        if (entityPlayerSP.getObject().equals(entityLivingBase.getObject())) {
            return null;
        }
        RenderEntityContext renderEntityContext = RenderEntityContextCache.getOrCreate(entityLivingBase, entityPlayerSP);
        if (renderEntityContext.isSyntheticEntity()) {
            return null;
        }
        if (this.hideBots.getEffectiveValue().booleanValue() && renderEntityContext.isBot()) {
            return null;
        }
        if (this.ignoreInvisibles.getEffectiveValue().booleanValue() && renderEntityContext.isInvisible()) {
            return null;
        }
        if (entityLivingBase.isInstance(MappedClasses.Yl)) {
            if (!this.renderPlayers.getEffectiveValue().booleanValue()) {
                return null;
            }
            if ((Double)this.playersMaxDistance.getValue() != 0.0 && renderEntityContext.getDistance() > (Double)this.playersMaxDistance.getValue()) {
                return null;
            }
        }
        if (RotationUtil.m(entityLivingBase)) {
            if (!this.renderAnimals.getEffectiveValue().booleanValue()) {
                return null;
            }
            if ((Double)this.animalsMaxDistance.getValue() != 0.0 && renderEntityContext.getDistance() > (Double)this.animalsMaxDistance.getValue()) {
                return null;
            }
        }
        if (RotationUtil.W(entityLivingBase)) {
            if (!this.renderMobs.getEffectiveValue().booleanValue()) {
                return null;
            }
            return (Double)this.mobsMaxDistance.getValue() == 0.0 || !(renderEntityContext.getDistance() > (Double)this.mobsMaxDistance.getValue()) ? renderEntityContext : null;
        }
        return renderEntityContext;
    }

    @Listen
    public void w(EventEntityRenderState eventEntityRenderState) {
        World world = eventEntityRenderState.getWorld();
        if (world.isNull()) {
            return;
        }
        EntityPlayerSP entityPlayerSP = eventEntityRenderState.getThePlayer();
        Entity entity = eventEntityRenderState.getEntity();
        if (!this.isCurrentWorldEntity(world, entity) || this.shouldRenderContext(new EntityLivingBase(entity), world, entityPlayerSP) == null) {
            return;
        }
        eventEntityRenderState.getEntityRenderState().Z(new ITextComponent(null));
    }

    public String Q(EntityPlayerSP entityPlayerSP, RenderEntityContext renderEntityContext, EntityPlayer entityPlayer) {
        PotionEffect potionEffect;
        String string = EQUAL_DAMAGE_LABEL;
        ItemStack itemStack = renderEntityContext.getBestWeapon();
        float f = 0.0f;
        if (itemStack != null && itemStack.isNotNull()) {
            Object object2;
            f += ItemStackScoreUtil.I$src$F$dh3k81(itemStack);
            if (entityPlayer.i(PotionRegistry.t) && ((PotionEffect)(object2 = entityPlayer.b(PotionRegistry.t))).k() > 0) {
                f = (float)((double)f * (1.375 * (double)((PotionEffect)object2).L()));
            }
        }
        for (ItemStack object3 : this.collectEquipment(entityPlayer)) {
            f = (float)((double)f + ItemStackScoreUtil.L(object3));
        }
        float f2 = ItemStackScoreUtil.O(entityPlayerSP);
        if (this.calculateEffects.getEffectiveValue().booleanValue() && entityPlayerSP.i(PotionRegistry.t) && (potionEffect = entityPlayerSP.b(PotionRegistry.t)).k() > 0) {
            f2 = (float)((double)f2 * (1.375 * (double)potionEffect.L()));
        }
        for (ItemStack itemStack2 : this.collectEquipment(entityPlayerSP)) {
            f2 = (float)((double)f2 + ItemStackScoreUtil.L(itemStack2));
        }
        if (f2 > f) {
            string = MORE_DAMAGE_LABEL;
        } else if (f2 < f) {
            string = LESS_DAMAGE_LABEL;
        }
        return string;
    }

    private static Exception passthroughException(Exception exception) {
        return exception;
    }
}
