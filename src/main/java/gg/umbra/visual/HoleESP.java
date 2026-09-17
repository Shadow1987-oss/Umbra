package gg.umbra.visual;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.BlockUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.render.BufferedRenderPrimitives;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class HoleESP
extends HackModule {
    private final SliderSetting range;
    private final SliderSetting requiredSides;
    private final ToggleSetting safeOnly;
    private final ToggleSetting showUnsafe;
    private final ColorPicker safeColor;
    private final ColorPicker unsafeColor;
    private final SliderSetting maxRender;
    private final SliderSetting scanInterval;
    private final TimerUtil scanTimer;
    private final List<Hole> holes = new ArrayList<Hole>();
    private int lastScanX;
    private int lastScanZ;
    private boolean hasScanned = false;

    public HoleESP() {
        super("HoleESP", -1042134892, Category.RENDER, "Outlines safe 1x1 holes around you.");
        this.range = SliderSetting.create(this, "Range", "#", "", 1.0, 8.0, 32.0, 1.0);
        this.requiredSides = SliderSetting.create(this, "Required sides", "#", "", 1.0, 4.0, 4.0, 1.0, "How many solid walls a hole needs to be highlighted (4 = fully enclosed)");
        this.safeOnly = ToggleSetting.create(this, "Safe only", false, "Only highlight holes that meet the required sides");
        this.showUnsafe = ToggleSetting.create(this, "Show unsafe", true, "Show holes that do not meet the required sides in the unsafe color");
        this.safeColor = ColorPicker.createWithAlpha(this, "Safe Color", new Color(0, 255, 0), 130);
        this.unsafeColor = ColorPicker.createWithAlpha(this, "Unsafe Color", new Color(255, 0, 0), 130);
        this.maxRender = SliderSetting.create(this, "Max render", "#", "", 1.0, 32.0, 128.0, 1.0, "Maximum holes rendered per frame");
        this.scanInterval = SliderSetting.createWithDescription(this, "Scan interval", "#", "ms", 100.0, 250.0, 2000.0, "How often the world is scanned for holes.");
        this.scanTimer = new TimerUtil();
        this.safeOnly.addDependentValues(this.unsafeColor);
        this.addValue(this.range, this.requiredSides, this.safeOnly, this.showUnsafe, this.safeColor, this.unsafeColor, this.maxRender, this.scanInterval);
        this.v(50L, true);
    }

    @Override
    public String getId() {
        return "holeesp";
    }

    @Override
    public void onScheduledAction() {
        if (!this.scanTimer.hasTimeElapsed(((Double) this.scanInterval.getValue()).longValue())) {
            return;
        }
        this.scanTimer.reset();
        EntityPlayerSP player = Minecraft.thePlayer();
        WorldClient world = Minecraft.theWorld();
        if (player.isNull() || world.isNull()) {
            this.holes.clear();
            return;
        }
        int playerX = (int) Math.floor(player.z());
        int playerZ = (int) Math.floor(player.h());
        // Only re-scan once the player has moved far enough; standing still should not re-scan.
        if (this.hasScanned && Math.abs(playerX - this.lastScanX) + Math.abs(playerZ - this.lastScanZ) < 6) {
            return;
        }
        this.hasScanned = true;
        this.lastScanX = playerX;
        this.lastScanZ = playerZ;
        int radius = ((Double) this.range.getValue()).intValue();
        int playerY = (int) Math.floor(player.N());
        ArrayList<Hole> found = new ArrayList<Hole>();
        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dz = -radius; dz <= radius; ++dz) {
                if ((double) (dx * dx + dz * dz) > (double) (radius * radius)) {
                    continue;
                }
                for (int dy = -2; dy <= 2; ++dy) {
                    int x = playerX + dx;
                    int y = playerY + dy;
                    int z = playerZ + dz;
                    if (y < 0) {
                        continue;
                    }
                    Block block = world.getBlockByPos(x, y, z);
                    Block below = world.getBlockByPos(x, y - 1, z);
                    if (block.isNull() || !BlockUtil.u(block) || below.isNull() || !BlockUtil.b(below)) {
                        continue;
                    }
                    int solidSides = 0;
                    if (this.isSolid(world, x + 1, y, z)) {
                        ++solidSides;
                    }
                    if (this.isSolid(world, x - 1, y, z)) {
                        ++solidSides;
                    }
                    if (this.isSolid(world, x, y, z + 1)) {
                        ++solidSides;
                    }
                    if (this.isSolid(world, x, y, z - 1)) {
                        ++solidSides;
                    }
                    found.add(new Hole(x, y, z, solidSides));
                }
            }
        }
        found.sort((first, second) -> Integer.compare(
                (first.x - playerX) * (first.x - playerX) + (first.z - playerZ) * (first.z - playerZ),
                (second.x - playerX) * (second.x - playerX) + (second.z - playerZ) * (second.z - playerZ)));
        this.holes.clear();
        this.holes.addAll(found);
    }

    private boolean isSolid(WorldClient world, int x, int y, int z) {
        Block block = world.getBlockByPos(x, y, z);
        return block.isNotNull() && BlockUtil.b(block);
    }

    @Listen
    public void onRender3D(EventRender3D eventRender3D) {
        if (this.holes.isEmpty()) {
            return;
        }
        RenderUtil.d();
        Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        RenderUtils.g();
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderY = RenderManager.getInterpolatedRenderPosY();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        int max = ((Double) this.maxRender.getValue()).intValue();
        int required = ((Double) this.requiredSides.getValue()).intValue();
        int rendered = 0;
        for (Hole hole : this.holes) {
            if (rendered >= max) {
                break;
            }
            boolean safe = hole.solidSides >= required;
            if (this.safeOnly.getEffectiveValue().booleanValue() && !safe) {
                continue;
            }
            if (!safe && !this.showUnsafe.getEffectiveValue().booleanValue()) {
                continue;
            }
            Color color = safe ? this.safeColor.getMutableColor() : this.unsafeColor.getMutableColor();
            BufferedRenderPrimitives.drawBoxOutline(
                    (double) hole.x + 0.05 - renderX,
                    (double) hole.y - 1.05 - renderY,
                    (double) hole.z + 0.05 - renderZ,
                    (double) hole.x + 0.95 - renderX,
                    (double) hole.y + 0.95 - renderY,
                    (double) hole.z + 0.95 - renderZ,
                    color);
            ++rendered;
        }
        RenderUtils.f();
        Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
        RenderUtil.Y();
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onDisable() {
        this.holes.clear();
    }

    private static final class Hole {
        final int x;
        final int y;
        final int z;
        final int solidSides;

        Hole(int x, int y, int z, int solidSides) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.solidSides = solidSides;
        }
    }
}
