package gg.umbra.hacks.pvp;

import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.system.SecondaryActionControlClaim;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.utils.MathUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.utils.network.PacketDispatchGuard;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.NetHandlerPlayClientImpl;
import gg.umbra.wrapper.impl.Packet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class PacketKnockback
extends HackModule {
    private long delayMillis;
    private long releaseTime;
    private final RandomRangeSetting airDelay;
    private final Queue<EventPacketReceive> heldPackets;
    private final SliderSetting chance;
    private final RandomRangeSetting groundDelay;
    private long lastHitTime;
    private Entity target;
    private final PacketDispatchGuard dispatchGuard = PacketDispatchGuard.b;
    private final ToggleSetting waterCheck;
    private int hitCount = 0;
    private SecondaryActionControlClaim controlClaim;

    public PacketKnockback() {
        super("PacketKnockback", 0, Category.UTILITY, "Delays incoming knockback packets");
        this.heldPackets = new LinkedList<EventPacketReceive>();
        this.chance = SliderSetting.createWithDescription(this, "Chance", "#", "%", 0.0, 40.0, 100.0, "Chance of delaying knockback");
        this.airDelay = RandomRangeSetting.create(this, "Air delay", "#", "", 0.0, 50.0, 100.0, 500.0);
        this.groundDelay = RandomRangeSetting.create(this, "Ground delay", "#", "", 0.0, 200.0, 250.0, 500.0);
        this.waterCheck = ToggleSetting.create(this, "Water check", false, "Won't delay knockback if in water");
        this.controlClaim = SharedModuleControlClaims.secondaryAction;
        this.addValue(this.chance, this.airDelay, this.groundDelay, this.waterCheck);
        this.chance.setMaximumFractionDigits(0);
        this.controlClaim.setPriority(this, 5);
    }

    @Override
    public String getId() {
        return "packetknockback";
    }

    private boolean rollChance() {
        int roll = MathUtil.randomExclusiveUpper(new Random(), 0, 100);
        return (double) roll >= 100.0 - (Double) this.chance.getValue();
    }

    @Override
    public String getDetailedSuffix() {
        if (!this.heldPackets.isEmpty()) {
            return "\u00a7cHolding";
        }
        return this.groundDelay.getDisplayValue() + "ms";
    }

    private boolean isInWater() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return true;
        }
        return this.waterCheck.getEffectiveValue() && player.h$src$Z$ftwoya();
    }

    private boolean shouldDelay() {
        return !this.isInWater() && this.rollChance();
    }

    public EntityLivingBase findTargetInRange(double fov, double range) {
        EntityLivingBase target = RotationUtil.u(range, fov / 2.0);
        if (target == null) {
            return null;
        }
        if (RotationUtil.o(Minecraft.thePlayer(), target, range, 90.0, true)) {
            return target;
        }
        return null;
    }

    @Listen(priority = EventPriority.LOWEST)
    public void onPacketReceive(EventPacketReceive event) {
        if (event.getWorld().isNotNull() && event.getThePlayer().isNotNull()) {
            Packet packet = event.getPacket();
            if (this.dispatchGuard.R(packet)) {
                return;
            }
            boolean isVelocity = this.containsPlayerVelocity(packet, event.getThePlayer());
            if (isVelocity) {
                if (this.target == null || !this.shouldDelay()) {
                    isVelocity = false;
                } else {
                    this.lastHitTime = System.currentTimeMillis();
                }
            }
            if (isVelocity && this.heldPackets.isEmpty()) {
                this.delayMillis = this.hitCount < 3 ? (long) this.airDelay.getRandomRangeSetting() : (long) this.groundDelay.getRandomRangeSetting();
                if (this.delayMillis > 0L) {
                    this.releaseTime = System.currentTimeMillis() + this.delayMillis;
                    this.heldPackets.add(event);
                    event.setCancelled(true);
                    this.controlClaim.markClaimed();
                }
            } else if (!this.heldPackets.isEmpty()) {
                this.heldPackets.add(event);
                event.setCancelled(true);
                this.controlClaim.markClaimed();
            }
            if (this.heldPackets.isEmpty()) {
                this.dispatchGuard.J(packet);
                this.controlClaim.clearClaimed();
            }
        }
    }

    private void flushHeldPackets() {
        if (this.heldPackets.isEmpty()) {
            return;
        }
        if (System.currentTimeMillis() >= this.releaseTime) {
            NetHandlerPlayClientImpl networkHandler = Minecraft.thePlayer().sendQueue();
            for (EventPacketReceive heldEvent : this.heldPackets) {
                this.dispatchGuard.l(heldEvent.getPacket(), networkHandler);
            }
            this.heldPackets.clear();
        }
    }

    private boolean containsPlayerVelocity(Packet packet, EntityPlayerSP player) {
        boolean[] found = new boolean[]{false};
        Packet.n(packet, resolvedPacket -> this.checkVelocityPacket(found, player, resolvedPacket));
        return found[0];
    }

    private void flushOnTick() {
        this.flushHeldPackets();
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        PacketDispatchGuard.B(this::flushOnTick);
        if (event.getWorld().isNotNull() && event.getThePlayer().isNotNull()) {
            this.target = this.findTargetInRange(90.0, 5.0);
            this.hitCount = event.getThePlayer().b$src$Z$fqlxe4() ? ++this.hitCount : 0;
        }
    }

    private void checkVelocityPacket(boolean[] found, EntityPlayerSP player, Packet packet) {
        Entity affectedEntity = AttackPacketTimingTracker.getVelocityPacketEntity(packet);
        if (!found[0] && affectedEntity != null && affectedEntity.equals(player)
                && System.currentTimeMillis() - this.lastHitTime > 475L) {
            found[0] = true;
        }
    }
}
