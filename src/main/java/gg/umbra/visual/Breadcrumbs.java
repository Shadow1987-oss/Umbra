package gg.umbra.visual;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.render.BufferedRenderPrimitives;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Breadcrumbs
extends HackModule {
    private final SliderSetting maxPoints;
    private final SliderSetting lineWidth;
    private final SliderSetting recordInterval;
    private final ToggleSetting clearOnDisable;
    private final ColorPicker color;
    private final TimerUtil recordTimer;
    private final List<double[]> points = new ArrayList<double[]>();
    private WorldClient lastWorld;

    public Breadcrumbs() {
        super("Breadcrumbs", 1174203485, Category.RENDER, "Draws a line where you have walked.");
        this.maxPoints = SliderSetting.create(this, "Max points", "#", "", 10.0, 200.0, 1000.0, 10.0);
        this.lineWidth = SliderSetting.create(this, "Line width", "#.##", "", 0.5, 1.5, 5.0, 0.1);
        this.recordInterval = SliderSetting.create(this, "Record interval", "#", "ms", 10.0, 50.0, 500.0, 10.0);
        this.clearOnDisable = ToggleSetting.create(this, "Clear on disable", true);
        this.color = ColorPicker.createWithAlpha(this, "Color", new Color(0, 150, 255), 200);
        this.recordTimer = new TimerUtil();
        this.addValue(this.maxPoints, this.lineWidth, this.recordInterval, this.clearOnDisable, this.color);
        this.v(50L, true);
    }

    @Override
    public String getId() {
        return "breadcrumbs";
    }

    @Override
    public void onScheduledAction() {
        if (!this.recordTimer.hasTimeElapsed(((Double) this.recordInterval.getValue()).longValue())) {
            return;
        }
        this.recordTimer.reset();
        EntityPlayerSP player = Minecraft.thePlayer();
        WorldClient world = Minecraft.theWorld();
        if (player.isNull() || world.isNull()) {
            return;
        }
        if (this.lastWorld != null && !this.lastWorld.equals(world)) {
            this.points.clear();
        }
        this.lastWorld = world;
        double[] last = this.points.isEmpty() ? null : this.points.get(this.points.size() - 1);
        double x = player.z();
        double y = player.N();
        double z = player.h();
        if (last == null || Math.abs(last[0] - x) > 0.05 || Math.abs(last[1] - y) > 0.05 || Math.abs(last[2] - z) > 0.05) {
            this.points.add(new double[]{x, y, z});
        }
        int max = ((Double) this.maxPoints.getValue()).intValue();
        if (this.points.size() > max) {
            this.points.subList(0, this.points.size() - max).clear();
        }
    }

    @Listen
    public void onRender3D(EventRender3D eventRender3D) {
        if (this.points.size() < 2) {
            return;
        }
        RenderUtil.d();
        Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        RenderUtils.g();
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderY = RenderManager.getInterpolatedRenderPosY();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        float width = ((Double) this.lineWidth.getValue()).floatValue();
        Color color = this.color.getMutableColor();
        for (int i = 0; i < this.points.size() - 1; ++i) {
            double[] current = this.points.get(i);
            double[] next = this.points.get(i + 1);
            BufferedRenderPrimitives.drawLine3D(
                    current[0] - renderX, current[1] - renderY, current[2] - renderZ,
                    next[0] - renderX, next[1] - renderY, next[2] - renderZ,
                    width, color);
        }
        RenderUtils.f();
        Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
        RenderUtil.Y();
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onDisable() {
        if (this.clearOnDisable.getEffectiveValue().booleanValue()) {
            this.points.clear();
        }
    }
}
