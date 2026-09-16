package gg.umbra.worldmods;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventBlockFluidRender;
import gg.umbra.event.impl.EventBlockLayerOverride;
import gg.umbra.event.impl.EventBlockLayerRender;
import gg.umbra.event.impl.EventBlockModelRender;
import gg.umbra.event.impl.EventBlockRenderBounds;
import gg.umbra.event.impl.EventBlockRenderColorOpacity;
import gg.umbra.event.impl.EventBlockShouldRender;
import gg.umbra.event.impl.EventChunkRenderRebuild;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventRenderWorldPassExecutorDrain;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumWorldBlockLayer;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class OreHighlight
extends HackModule {
    private final SliderSetting opacityValue;
    private boolean needsReload;
    private double lastOpacity = 0.0;
    private float savedGamma = 1.0f;
    private final List<Integer> blockIds;
    private final OptionalItemFilter blocksValue = OptionalItemFilter.create(this, "xray-blocks", "Xray Blocks", OptionalItemFilter.ALLOW_LIST_COLOR, "Gold Ore", "Iron Ore", "Diamond Ore", "Emerald Ore", "Lapis Lazuli Ore", "Gold Block", "Iron Block", "Diamond Block", "Emerald Block");
    private final ToggleSetting caveOptionSetting;

    public OreHighlight() {
        super("Xray", 0, Category.WORLD, "Renders whitelisted blocks through walls.");
        this.opacityValue = SliderSetting.create((Object) this, "Opacity", "#", "", 0.0, 60.0, 255.0, 1.0);
        this.caveOptionSetting = ToggleSetting.create(this, "Cave Mode", false, "Only shows ores that are exposed to air.");
        this.blockIds = new ArrayList<Integer>();
        this.addValue(this.opacityValue, this.caveOptionSetting, this.blocksValue);
        this.opacityValue.addChangeListener(this::onOpacityChanged);
    }

    @Override
    public String getId() {
        return "xray";
    }

    public void onChunkRenderRebuild(EventChunkRenderRebuild eventChunkRenderRebuild) {
        if (!this.isEnabled()) {
            return;
        }
        eventChunkRenderRebuild.setCancelled(true);
    }

    private void onOpacityChanged(SliderSetting numberValue) {
        if (this.isEnabled()) {
            this.refreshWorldForOpacity();
        }
    }

    public void onBlockFluidRender(EventBlockFluidRender eventBlockFluidRender) {
        if (!this.isEnabled()) {
            return;
        }
        eventBlockFluidRender.setCancelled(true);
    }

    public void onBlockModelRender(EventBlockModelRender eventBlockModelRender) {
        if (!this.isEnabled()) {
            return;
        }
        eventBlockModelRender.setCancelled(true);
    }

    public void onBlockRenderDecision(EventBlockLayerOverride eventBlockLayerOverride) {
        if (!this.isEnabled()) {
            return;
        }
        eventBlockLayerOverride.setCancelled(true);
        if (this.isTargetBlock(eventBlockLayerOverride.getBlock())) {
            eventBlockLayerOverride.setShouldRender(true);
        }
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    private void refreshWorldForOpacity() {
        if (Minecraft.thePlayer().isNull() || (Double) this.opacityValue.getValue() == this.lastOpacity) {
            return;
        }
        int radius = 4000;
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        int playerX = (int) entityPlayerSP.z();
        int playerZ = (int) entityPlayerSP.h();
        Minecraft.theWorld().Z(playerX - radius, 0, playerZ - radius, playerX + radius, 300, playerZ + radius);
        this.lastOpacity = (Double) this.opacityValue.getValue();
    }

    private void reloadRenderers() {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        Minecraft.O().loadRenderers();
    }

    @Override
    public void onEnable() {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!Umbra.INSTANCE.getPrimaryMappingTaskSet().Q()) {
            Umbra.INSTANCE.getPrimaryMappingTaskSet().Y();
        }
        this.needsReload = true;
        this.savedGamma = Minecraft.gameSettings().b();
        Minecraft.gameSettings().y(10.0f);
        this.refreshWorldForOpacity();
    }

    @Override
    public void onDisable() {
        EventRenderWorldPassExecutorDrain.EXECUTOR.execute(this::reloadRenderers);
        Minecraft.gameSettings().y(this.savedGamma);
    }

    public void onBlockSideRender(EventBlockShouldRender eventBlockShouldRender) {
        if (!this.isEnabled()) {
            return;
        }
        if (this.isTargetBlock(eventBlockShouldRender.getBlock())) {
            eventBlockShouldRender.setCancelled(this.caveOptionSetting.getEffectiveValue() == false);
        }
    }

    public void onAmbientOcclusion(EventBlockRenderBounds eventBlockRenderBounds) {
        if (!this.isEnabled()) {
            return;
        }
        eventBlockRenderBounds.getRenderBlocks().setRenderAllFaces(
                this.isTargetBlock(eventBlockRenderBounds.getBlock()));
    }

    @Listen
    public void onTick(EventPreTick eventPreTick) {
        this.blockIds.clear();
        for (String string : this.blocksValue.getEnabledValues()) {
            Block block = Block.t(string.replace(" ", "_").toLowerCase());
            if (block == null || this.blockIds.contains(Block.R(block))) {
                continue;
            }
            this.blockIds.add(Block.R(block));
        }
    }

    @Listen
    public void onRenderWorldPassComplete(EventPreRenderTick eventPreRenderTick) {
        if (!this.needsReload) {
            return;
        }
        this.needsReload = false;
        this.reloadRenderers();
    }

    public void onBlockRenderColorOpacity(EventBlockRenderColorOpacity eventBlockRenderColorOpacity) {
        eventBlockRenderColorOpacity.setOpacity(((Double) this.opacityValue.getValue()).intValue());
    }

    public int getOpacity() {
        return ((Double) this.opacityValue.getValue()).intValue();
    }

    public boolean isTargetBlock(Block block) {
        return this.blockIds.contains(Block.R(block));
    }

    public void onBlockRenderLayer(EventBlockLayerRender eventBlockLayerRender) {
        if (this.isTargetBlock(eventBlockLayerRender.getBlock())) {
            eventBlockLayerRender.setCancelled(true);
            if (eventBlockLayerRender.getEnumWorldBlockLayer().equals(EnumWorldBlockLayer.translucent())) {
                eventBlockLayerRender.setShouldRender(true);
            } else {
                eventBlockLayerRender.setShouldRender(false);
            }
        }
    }
}
