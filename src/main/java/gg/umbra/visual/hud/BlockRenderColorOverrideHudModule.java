package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventBlockRenderColorOverride;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.utils.MutableColor;
import gg.umbra.value.ColorPicker;
import java.awt.Color;

public class BlockRenderColorOverrideHudModule
extends HudModule {
    private final ColorPicker colorValue = ColorPicker.create(this, "Color", new Color(255, 0, 0, 127));

    @Listen
    public void onBlockRenderColorOverride(EventBlockRenderColorOverride event) {
        MutableColor mutableColor = this.colorValue.getMutableColor();
        EventBlockRenderColorOverride.setColor((float)((Color)mutableColor).getRed() / 255.0f, (float)((Color)mutableColor).getGreen() / 255.0f, (float)((Color)mutableColor).getBlue() / 255.0f, (float)((Color)mutableColor).getAlpha() / 255.0f);
    }

    @Override
    public String getId() {
        return "hitcolor";
    }

    public BlockRenderColorOverrideHudModule() {
        super("Hit Color", HudModuleGroup.GAME, "hit_color_mod");
        this.setSuffix("Changes the color of damaged entities");
        this.addValue(this.colorValue);
    }
}

