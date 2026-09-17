package gg.umbra.visual;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.datas.HSBAData;
import gg.umbra.utils.datas.HSBData;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.TileEntity;
import gg.umbra.wrapper.impl.TileEntityChest;
import gg.umbra.wrapper.impl.TileEntityOpenedChest;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ContainerHighlight
extends HackModule {
    private final ColorPicker chestColor;
    private final ToggleSetting renderDropper;
    private final ColorPicker hopperColor;
    private final ToggleSetting renderHopper;
    private final ToggleSetting renderEnderchests;
    private final ToggleSetting renderTrappedChests;
    private final ColorPicker enderChestColor;
    private final ToggleSetting renderChests;
    private final ColorPicker dispenserColor;
    private final ColorPicker trappedChestColor;
    private final ToggleSetting renderFurnace;
    private final ColorPicker shulkerColor;
    private final ToggleSetting renderDispenser;
    private final ToggleSetting renderShulker;
    private final ColorPicker furnaceColor;
    private final ColorPicker dropperColor;
    private final ToggleSetting outlineOpen = ToggleSetting.create(this, "Outline open", true, "Outlines open chests by contrasting color");

    public ContainerHighlight() {
        super("StorageESP", 3465010, Category.RENDER);
        this.renderChests = ToggleSetting.create(this, "Render Chests", true);
        this.renderTrappedChests = ToggleSetting.create(this, "Render Trapped Chests", true);
        this.renderEnderchests = ToggleSetting.create(this, "Render Enderchests", false);
        this.renderHopper = ToggleSetting.create(this, "Render Hopper", false);
        this.renderFurnace = ToggleSetting.create(this, "Render Furnace", false);
        this.renderDispenser = ToggleSetting.create(this, "Render Dispenser", false);
        this.renderDropper = ToggleSetting.create(this, "Render Dropper", false);
        this.renderShulker = ToggleSetting.create(this, "Render Shulker", false);
        this.chestColor = ColorPicker.create(this, "Chest Color", new Color(1, 255, 146, 100));
        this.trappedChestColor = ColorPicker.create(this, "Chest Color", new Color(255, 0, 0, 100));
        this.enderChestColor = ColorPicker.create(this, "Ender Chest Color", new Color(126, 21, 156, 100));
        this.hopperColor = ColorPicker.create(this, "Hopper Color", new Color(138, 138, 138, 255));
        this.furnaceColor = ColorPicker.create(this, "Furnace Color", new Color(90, 90, 90, 255));
        this.dispenserColor = ColorPicker.create(this, "Dispenser Color", new Color(1, 20, 200, 100));
        this.dropperColor = ColorPicker.create(this, "Dropper Color", new Color(70, 200, 200, 100));
        this.shulkerColor = ColorPicker.create(this, "Shulker Color", new Color(255, 255, 255, 100));
        this.renderChests.addDependentValues(this.chestColor);
        this.renderTrappedChests.addDependentValues(this.trappedChestColor);
        this.renderEnderchests.addDependentValues(this.enderChestColor);
        this.renderHopper.addDependentValues(this.hopperColor);
        this.renderFurnace.addDependentValues(this.furnaceColor);
        this.renderDispenser.addDependentValues(this.dispenserColor);
        this.renderDropper.addDependentValues(this.dropperColor);
        this.renderShulker.addDependentValues(this.shulkerColor);
        this.addValue(this.outlineOpen, this.renderChests, this.chestColor, this.renderTrappedChests, this.trappedChestColor, this.renderEnderchests, this.enderChestColor, this.renderHopper, this.hopperColor, this.renderFurnace, this.furnaceColor, this.renderDispenser, this.dispenserColor, this.renderDropper, this.dropperColor);
        this.U(this.renderShulker, ForgeVersion.MC_1_12_2.n());
        this.U(this.shulkerColor, ForgeVersion.MC_1_12_2.n());
    }

    @Override
    public String getId() {
        return "storageesp";
    }

    @Listen
    public void onRender3D(EventRender3D eventRender3D) {
        RenderUtil.d();
        Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        RenderUtils.g();
        boolean bl = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        if (bl) {
            GL11.glBlendFunc((int) 770, (int) 771);
            OpenGlBackendHolder.backend.setLineWidth(1.5f);
            OpenGlBackendHolder.backend.disableCapability(3553);
            OpenGlBackendHolder.backend.enableCapability(2848);
            OpenGlBackendHolder.backend.disableCapability(2929);
            OpenGlBackendHolder.backend.setDepthMask(false);
            double d = RenderManager.getInterpolatedRenderPosX();
            double d2 = RenderManager.getInterpolatedRenderPosY();
            double d3 = RenderManager.getInterpolatedRenderPosZ();
            for (Object e : Minecraft.theWorld().R$src$Ljava_util_List_$1ycbpra()) {
                Color color = null;
                TileEntity tileEntity = null;
                if ((this.renderChests.getEffectiveValue().booleanValue() || this.renderTrappedChests.getEffectiveValue().booleanValue()) && MappedClasses.DZ.isInstance(e)) {
                    TileEntityOpenedChest openedChest = new TileEntityOpenedChest(e);
                    int n = openedChest.getNumPlayersUsing();
                    if (this.renderChests.getEffectiveValue().booleanValue() && n == 0) {
                        color = this.chestColor.getMutableColor();
                        tileEntity = openedChest;
                    }
                    if (this.renderTrappedChests.getEffectiveValue().booleanValue() && n == 1) {
                        color = this.trappedChestColor.getMutableColor();
                        tileEntity = openedChest;
                    }
                } else if (this.renderEnderchests.getEffectiveValue().booleanValue() && MappedClasses.u0.isInstance(e)) {
                    color = this.enderChestColor.getMutableColor();
                    tileEntity = new TileEntityChest(e);
                } else if (this.renderHopper.getEffectiveValue().booleanValue() && MappedClasses.Dx.isInstance(e)) {
                    color = this.hopperColor.getMutableColor();
                } else if (this.renderFurnace.getEffectiveValue().booleanValue() && MappedClasses.li.isInstance(e)) {
                    color = this.furnaceColor.getMutableColor();
                } else if (this.renderDropper.getEffectiveValue().booleanValue() && MappedClasses.YI.equals(e.getClass())) {
                    color = this.dropperColor.getMutableColor();
                } else if (this.renderDispenser.getEffectiveValue().booleanValue() && MappedClasses.lI.equals(e.getClass())) {
                    color = this.dispenserColor.getMutableColor();
                } else if (ForgeVersion.MC_1_12_2.d() && this.renderShulker.getEffectiveValue().booleanValue() && MappedClasses.Dh.isInstance(e)) {
                    color = this.shulkerColor.getMutableColor();
                }
                if (color == null) {
                    continue;
                }
                if (tileEntity == null) {
                    tileEntity = new TileEntity(e);
                }
                color = new Color(((Color) color).getRed(), ((Color) color).getGreen(), ((Color) color).getBlue(), ((Color) color).getAlpha());
                HSBData renderData;
                if (tileEntity instanceof TileEntityOpenedChest) {
                    TileEntityOpenedChest tileEntityOpenedChest = (TileEntityOpenedChest) tileEntity;
                    renderData = this.outlineOpen.getEffectiveValue().booleanValue() ? new HSBAData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color, tileEntityOpenedChest.Y()) : new HSBData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color);
                } else if (tileEntity instanceof TileEntityChest) {
                    TileEntity tileEntity2 = tileEntity;
                    renderData = new HSBAData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color, ((TileEntityChest) tileEntity2).getLidOpenness());
                } else {
                    renderData = new HSBData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color);
                }
                RenderUtil.L(d, d2, d3, renderData);
            }
            OpenGlBackendHolder.backend.setDepthMask(true);
            OpenGlBackendHolder.backend.enableCapability(2929);
            OpenGlBackendHolder.backend.enableCapability(3553);
            OpenGlBackendHolder.backend.disableCapability(2848);
            RenderUtils.f();
            Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
            RenderUtil.Y();
            return;
        }
        OpenGlBackendHolder.backend.enableCapability(3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        OpenGlBackendHolder.backend.setLineWidth(1.5f);
        OpenGlBackendHolder.backend.disableCapability(3553);
        OpenGlBackendHolder.backend.enableCapability(2848);
        OpenGlBackendHolder.backend.disableCapability(2929);
        OpenGlBackendHolder.backend.setDepthMask(false);
        double d = RenderManager.getInterpolatedRenderPosX();
        double d4 = RenderManager.getInterpolatedRenderPosY();
        double d5 = RenderManager.getInterpolatedRenderPosZ();
        for (Object e : Minecraft.theWorld().R$src$Ljava_util_List_$1ycbpra()) {
            Color color = null;
            TileEntity tileEntity = null;
            if ((this.renderChests.getEffectiveValue().booleanValue() || this.renderTrappedChests.getEffectiveValue().booleanValue()) && MappedClasses.DZ.isInstance(e)) {
                TileEntityOpenedChest openedChest = new TileEntityOpenedChest(e);
                int n = openedChest.getNumPlayersUsing();
                if (this.renderChests.getEffectiveValue().booleanValue() && n == 0) {
                    color = this.chestColor.getMutableColor();
                    tileEntity = openedChest;
                }
                if (this.renderTrappedChests.getEffectiveValue().booleanValue() && n == 1) {
                    color = this.trappedChestColor.getMutableColor();
                    tileEntity = openedChest;
                }
            } else if (this.renderEnderchests.getEffectiveValue().booleanValue() && MappedClasses.u0.isInstance(e)) {
                color = this.enderChestColor.getMutableColor();
                tileEntity = new TileEntityChest(e);
            } else if (this.renderHopper.getEffectiveValue().booleanValue() && MappedClasses.Dx.isInstance(e)) {
                color = this.hopperColor.getMutableColor();
            } else if (this.renderFurnace.getEffectiveValue().booleanValue() && MappedClasses.li.isInstance(e)) {
                color = this.furnaceColor.getMutableColor();
            } else if (this.renderDropper.getEffectiveValue().booleanValue() && MappedClasses.YI.equals(e.getClass())) {
                color = this.dropperColor.getMutableColor();
            } else if (this.renderDispenser.getEffectiveValue().booleanValue() && MappedClasses.lI.equals(e.getClass())) {
                color = this.dispenserColor.getMutableColor();
            } else if (ForgeVersion.MC_1_12_2.d() && this.renderShulker.getEffectiveValue().booleanValue() && MappedClasses.Dh.isInstance(e)) {
                color = this.shulkerColor.getMutableColor();
            }
            if (color == null) {
                continue;
            }
            if (tileEntity == null) {
                tileEntity = new TileEntity(e);
            }
            color = new Color(((Color) color).getRed(), ((Color) color).getGreen(), ((Color) color).getBlue(), ((Color) color).getAlpha());
            HSBData renderData;
            if (tileEntity instanceof TileEntityOpenedChest) {
                TileEntityOpenedChest tileEntityOpenedChest = (TileEntityOpenedChest) tileEntity;
                renderData = this.outlineOpen.getEffectiveValue().booleanValue() ? new HSBAData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color, tileEntityOpenedChest.Y()) : new HSBData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color);
            } else if (tileEntity instanceof TileEntityChest) {
                TileEntity tileEntity3 = tileEntity;
                renderData = new HSBAData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color, ((TileEntityChest) tileEntity3).getLidOpenness());
            } else {
                renderData = new HSBData(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ(), -1, color);
            }
            RenderUtil.L(d, d4, d5, renderData);
        }
        OpenGlBackendHolder.backend.setDepthMask(true);
        OpenGlBackendHolder.backend.enableCapability(2929);
        OpenGlBackendHolder.backend.enableCapability(3553);
        OpenGlBackendHolder.backend.disableCapability(2848);
        OpenGlBackendHolder.backend.disableCapability(3042);
        RenderUtils.f();
        Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
        RenderUtil.Y();
    }
}
