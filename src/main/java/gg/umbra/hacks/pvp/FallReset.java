package gg.umbra.hacks.pvp;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.event.impl.EventPostPlayerTick;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.MathUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.PotionRegistry;
import gg.umbra.wrapper.impl.SPacketEntityVelocity;
import java.util.Random;

public class FallReset
extends HackModule {
    private final RandomRangeSetting accuracy;
    public final SliderSetting chance;
    private boolean jumping;
    private boolean shouldJump;
    private final ToggleSetting waterCheck;
    private double motionZ;
    private double motionX;
    private boolean waitingForReset;
    private double motionY;
    public final ToggleSetting onlyWhenTargeting;
    private final Random random = new Random();

    public FallReset() {
        super("JumpReset", -13463640, Category.COMBAT, "Reduces knockback taken by jumping when hit");
        this.onlyWhenTargeting = ToggleSetting.create(this, "Only when targeting", false, "Only reduce knockback while being face to face with opponent");
        this.accuracy = RandomRangeSetting.createWithDescription(this, "Accuracy", "#", "%", 0.0, 40.0, 60.0, 100.0, 1.0, "If you will jump, this is the chance that you will actually land a perfect jump reset on time");
        this.chance = SliderSetting.createWithDescription(this, "Chance", "#", "%", 0.0, 40.0, 100.0, "Chance of reducing knockback");
        this.waterCheck = ToggleSetting.create(this, "Water check", false, "Won't reduce knockback if in water");
        this.addValue(this.chance, this.accuracy, this.onlyWhenTargeting, this.waterCheck);
        this.chance.setMaximumFractionDigits(0);
    }

    @Override
    public String getId() {
        return "jumpreset";
    }

    private boolean shouldReduce() {
        if (this.jumping) {
            return false;
        }
        if (Minecraft.thePlayer().i(PotionRegistry.Z)) {
            return false;
        }
        if (this.isInWater()) {
            return false;
        }
        if (this.onlyWhenTargeting.getEffectiveValue().booleanValue()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            boolean facingYaw = RotationUtil.H(player);
            boolean facingPitch = RotationUtil.F(player);
            if (!facingYaw || !facingPitch) {
                return false;
            }
        }
        int roll = MathUtil.randomExclusiveUpper(this.random, 0, 100);
        return roll >= 100.0 - (Double) this.chance.getValue();
    }

    @Listen
    public void onPacketReceive(EventPacketReceive event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        Packet packet = event.getPacket();
        Packet.n(packet, this::handleVelocityPacket);
    }

    @Listen
    public void onPlayerTick(EventPostPlayerTick event) {
        if (this.jumping) {
            KeyBinding jumpKey = Minecraft.gameSettings().O();
            ClientSettings.setPhysicalKeyState(jumpKey, false);
            Minecraft.gameSettings().O().setPressed(false);
            if (ForgeVersion.MC_1_21_4.v()) {
                Minecraft.thePlayer().movementInput().V(false);
            }
            this.jumping = false;
        }
    }

    @Listen
    public void onTick(EventPreTick event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        if (!this.waitingForReset) {
            return;
        }
        double posX = MathUtil.roundToScale(player.t(), 3);
        double posY = MathUtil.roundToScale(player.q(), 3);
        double posZ = MathUtil.roundToScale(player.T(), 3);
        double expectedX = MathUtil.roundToScale(this.motionX, 3);
        double expectedY = MathUtil.roundToScale(this.motionY, 3);
        double expectedZ = MathUtil.roundToScale(this.motionZ, 3);
        if (posX == expectedX && posY == expectedY && posZ == expectedZ) {
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.waitingForReset = false;
            if (!Minecraft.gameSettings().O().isKeyDown()) {
                this.shouldJump = true;
            }
        }
    }

    public boolean isInWater() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return true;
        }
        return this.waterCheck.getEffectiveValue() && player.h$src$Z$ftwoya();
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (this.shouldJump) {
            float roll = MathUtil.randomExclusiveUpper(this.random, 0, 100);
            double accuracyChance = this.accuracy.getRandomRangeSetting();
            if (roll < 100.0 - accuracyChance) {
                return;
            }
            KeyBinding jumpKey = Minecraft.gameSettings().O();
            ClientSettings.setPhysicalKeyState(jumpKey, true);
            jumpKey.setPressed(true);
            if (ForgeVersion.MC_1_21_4.v()) {
                Minecraft.thePlayer().movementInput().V(true);
            }
            this.jumping = true;
            this.shouldJump = false;
        }
    }

    private void handleVelocityPacket(Packet packet) {
        if (!packet.isInstance(MappedClasses.YX)) {
            return;
        }
        SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet);
        if (velocityPacket.getEntityId() != Minecraft.thePlayer().S()) {
            return;
        }
        boolean unsuitableVelocity = velocityPacket.getMotionX() == 0 && velocityPacket.getMotionZ() == 0
                || velocityPacket.getMotionY() < 0;
        if (!unsuitableVelocity && this.shouldReduce()) {
            this.motionX = velocityPacket.getMotionX() / 8000.0;
            this.motionY = velocityPacket.getMotionY() / 8000.0;
            this.motionZ = velocityPacket.getMotionZ() / 8000.0;
            this.waitingForReset = true;
        }
    }

    @Override
    public String getDetailedSuffix() {
        return this.chance.getDisplayValue() + "%";
    }
}
