package gg.umbra.combat;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.event.impl.EventPreAttack;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.SPacketAnimation;
import gg.umbra.wrapper.impl.SPacketEntityStatus;
import gg.umbra.wrapper.impl.SPacketEntityVelocity;
import gg.umbra.wrapper.impl.UseEntityPacketBridge;
import java.util.ArrayList;
import java.util.List;

public class AttackPacketTimingTracker
implements EventListener {
    private final List<Long> hitDelays = new ArrayList<Long>();
    public static final AttackPacketTimingTracker INSTANCE = new AttackPacketTimingTracker();
    private long lastHitTime;
    private static boolean initialized;
    private int targetId;
    private long lastAttackTime;

    public static Entity getEntityFromStatusPacket(SPacketEntityStatus packet) {
        if (packet.getLogicOpcode() == 2) {
            return Minecraft.theWorld().V(packet.getEntityId());
        }
        return null;
    }

    @Listen
    public void onPreAttack(EventPreAttack eventPreAttack) {
        this.targetId = eventPreAttack.getTarget().S();
    }

    private void recordHitDelay() {
        if (!initialized) {
            return;
        }
        long delay = System.currentTimeMillis() - this.lastHitTime;
        if (delay >= 500L) {
            return;
        }
        this.hitDelays.add(delay);
        if (this.hitDelays.size() == 20) {
            this.hitDelays.remove(0);
        }
    }

    public static boolean isTrackingEnabled() {
        return true;
    }

    public List<Long> getHitDelays() {
        return this.hitDelays;
    }

    public static Entity getVelocityPacketEntity(Packet packet) {
        if (packet.isInstance(MappedClasses.YX)) {
            SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet);
            return Minecraft.theWorld().V(velocityPacket.getEntityId());
        }
        return null;
    }

    public static Entity getDamagePacketEntity(Packet packet) {
        if (packet.isInstance(MappedClasses.lU)) {
            return AttackPacketTimingTracker.getEntityFromStatusPacket(new SPacketEntityStatus(packet));
        }
        if (packet.isInstance(MappedClasses.ZQ)) {
            return AttackPacketTimingTracker.getEntityFromAnimationPacket(new SPacketAnimation(packet));
        }
        return null;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public long getLastHitTime() {
        return this.lastHitTime;
    }

    @Listen
    public void onPacketSend(EventPacketSend eventPacketSend) {
        Packet packet = eventPacketSend.getPacket();
        if (UseEntityPacketBridge.isUseEntityPacket(packet)) {
            this.recordAttack(new UseEntityPacketBridge(packet));
        }
    }

    @Listen
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        Entity entity = AttackPacketTimingTracker.getDamagePacketEntity(eventPacketReceive.getPacket());
        if (entity != null && entity.isNotNull() && entity.S() == this.targetId) {
            this.recordHitDelay();
        }
    }

    public int getExpectedHurtTimeTicks() {
        return (int)Math.floor((double)this.getAverageHitDelay() / 50.0);
    }

    public long getLastAttackTime() {
        return this.lastAttackTime;
    }

    public static void setInitialized(boolean initializedState) {
        initialized = initializedState;
    }

    public static Entity getEntityFromAnimationPacket(SPacketAnimation packet) {
        if (packet.getAnimationType() == 1) {
            return Minecraft.theWorld().V(packet.getEntityId());
        }
        return null;
    }

    public long getAverageHitDelay() {
        long totalDelay = 0L;
        if (!this.hitDelays.isEmpty()) {
            for (long delay : this.hitDelays) {
                totalDelay += delay;
            }
            totalDelay /= (long)this.hitDelays.size();
        }
        return totalDelay;
    }

    private void recordAttack(UseEntityPacketBridge attackPacket) {
        Entity entity;
        if (attackPacket.isAttack() && (entity = Minecraft.theWorld().V(attackPacket.getEntityId())).isInstance(MappedClasses.zm)) {
            EntityLivingBase target = new EntityLivingBase(entity);
            if (target.c$src$I$15a9iwo() == 0 && System.currentTimeMillis() - this.lastHitTime > 400L) {
                if (System.currentTimeMillis() - this.lastAttackTime > this.getAverageHitDelay() * 2L) {
                    this.lastHitTime = System.currentTimeMillis();
                }
            }
            this.lastAttackTime = System.currentTimeMillis();
        }
    }


    public int getTargetId() {
        return this.targetId;
    }

    static {
        AttackPacketTimingTracker.setInitialized(false);
    }
}

