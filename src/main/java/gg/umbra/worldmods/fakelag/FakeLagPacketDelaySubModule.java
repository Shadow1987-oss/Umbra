package gg.umbra.worldmods.fakelag;

import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.DelayedPacketSendEntry;
import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.system.PrimaryActionControlClaim;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.worldmods.FakeLag;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.utils.RayTraceUtil;
import gg.umbra.utils.concurrent.ReadWriteLockHelper;
import gg.umbra.utils.datas.PlayerLocationSnapshot;
import gg.umbra.utils.network.PacketDispatchGuard;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.C03PacketPlayer;
import gg.umbra.wrapper.impl.CPacketPlayerBlockPlacement;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.UseEntityPacketBridge;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FakeLagPacketDelaySubModule
extends SubHack<FakeLag> {
    public final SliderSetting transmissionOffset = SliderSetting.create((Object)this, "Transmission offset", "#", "", 0.0, 5.0, 50.0, 1.0);
    private final Map<DelayedPacketSendEntry, Long> delayedPackets = new LinkedHashMap<DelayedPacketSendEntry, Long>();
    private final PrimaryActionControlClaim controlClaim;
    private final PacketDispatchGuard dispatchGuard = PacketDispatchGuard.b;
    private long lastAttackTime;
    private Entity targetEntity;
    private PlayerLocationSnapshot lastPositionSnapshot;
    private long lastBlockPlaceTime;
    private long blockPlaceCooldownUntil;
    private final ReadWriteLockHelper lock = new ReadWriteLockHelper();

    private void dispatchDelayedPacket(DelayedPacketSendEntry delayedPacketSendEntry) {
        this.dispatchGuard.o(delayedPacketSendEntry.getEvent());
    }


    private void flushAll() {
        this.lock.lockWrite();
        try {
            Iterator<Map.Entry<DelayedPacketSendEntry, Long>> iterator = this.delayedPackets.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<DelayedPacketSendEntry, Long> entry = iterator.next();
                this.dispatchGuard.o(entry.getKey().getEvent());
                iterator.remove();
            }
        } finally {
            this.lock.unlockWrite();
        }
    }

    private void delaySend(EventPacketSend eventPacketSend) {
        this.lock.lockWrite();
        try {
            long offset = ((Double)this.transmissionOffset.getValue()).longValue();
            if (offset > 0L) {
                long accumulated = 0L;
                for (Map.Entry<DelayedPacketSendEntry, Long> entry : this.delayedPackets.entrySet()) {
                    DelayedPacketSendEntry delayedPacketSendEntry = entry.getKey();
                    accumulated += offset;
                    if (delayedPacketSendEntry.isOffsetApplied()) continue;
                    entry.setValue(System.currentTimeMillis() + accumulated);
                    delayedPacketSendEntry.setOffsetApplied(true);
                }
                if (eventPacketSend != null) {
                    this.delayedPackets.put(new DelayedPacketSendEntry(eventPacketSend), System.currentTimeMillis() + accumulated + offset);
                    eventPacketSend.setCancelled(true);
                }
            } else {
                this.delayedPackets.keySet().forEach(this::dispatchDelayedPacket);
                this.delayedPackets.clear();
            }
        } finally {
            this.lock.unlockWrite();
        }
    }

    private void processPacket(EventPacketSend eventPacketSend) {
        Packet packet = eventPacketSend.getPacket();
        if (UseEntityPacketBridge.isUseEntityPacket(packet)) {
            UseEntityPacketBridge useEntityPacket = new UseEntityPacketBridge(packet);
            if (useEntityPacket.isAttack()) {
                Entity attackedEntity = Minecraft.theWorld().V(useEntityPacket.getEntityId());
                if (attackedEntity.isInstance(MappedClasses.zm)) {
                    EntityLivingBase livingTarget = new EntityLivingBase(attackedEntity);
                    if (livingTarget.c$src$I$15a9iwo() <= AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks()) {
                        if (System.currentTimeMillis() - this.lastAttackTime > 250L) {
                            if (this.releasePending()) {
                                return;
                            }
                        } else {
                            this.lastAttackTime = System.currentTimeMillis();
                        }
                    } else if (livingTarget.c$src$I$15a9iwo() <= AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1 && this.releasePending()) {
                        return;
                    }
                }
            }
        } else {
            if (packet.isInstance(MappedClasses.F9) || packet.isInstance(MappedClasses.u7) || packet.isInstance(MappedClasses.DN) || packet.isInstance(MappedClasses.q6)) {
                this.delaySend(eventPacketSend);
                return;
            }
            if (packet.isInstance(MappedClasses.YB)) {
                CPacketPlayerBlockPlacement placementPacket = new CPacketPlayerBlockPlacement(packet);
                ItemStack placedStack = placementPacket.getItemStack();
                if (placedStack.isNotNull()) {
                    if (!ItemStackScoreUtil.h(placedStack.getItem())) {
                        this.delaySend(eventPacketSend);
                        this.blockPlaceCooldownUntil = System.currentTimeMillis() + 500L;
                        return;
                    }
                    if (System.currentTimeMillis() - this.lastBlockPlaceTime >= 500L) {
                        this.delaySend(eventPacketSend);
                        this.lastBlockPlaceTime = System.currentTimeMillis();
                        return;
                    }
                }
            } else if (this.blockPlaceCooldownUntil > System.currentTimeMillis() || Minecraft.thePlayer().l$src$Z$1io4duf() && !Minecraft.thePlayer().o$src$Z$1iprrmi() || !Minecraft.thePlayer().b$src$Z$fqlxe4() && Minecraft.thePlayer().M$src$F$ff28gb() > 3.0f) {
                this.delaySend(eventPacketSend);
                return;
            }
        }
        if (this.targetEntity != null && this.targetEntity.isNotNull() && this.targetEntity.isInstance(MappedClasses.zm)) {
            if (packet.isInstance(MappedClasses.qD)) {
                C03PacketPlayer movementPacket = new C03PacketPlayer(packet);
                if (this.lastPositionSnapshot != null && movementPacket.hasPosition()) {
                    PlayerLocationSnapshot targetPosition = new PlayerLocationSnapshot(this.targetEntity.z(), this.targetEntity.N(), this.targetEntity.h());
                    PlayerLocationSnapshot packetPosition = new PlayerLocationSnapshot(movementPacket.getX(), movementPacket.getY(), movementPacket.getZ());
                    if (PlayerLocationSnapshot.rayIntersectionDistance(this.lastPositionSnapshot, targetPosition) < PlayerLocationSnapshot.rayIntersectionDistance(packetPosition, targetPosition) - 0.03) {
                        this.delaySend(eventPacketSend);
                        return;
                    }
                }
            }
        } else {
            this.delaySend(eventPacketSend);
            return;
        }
        long baseDelay = ((Double)((FakeLag)this.getParent()).delay.getValue()).longValue();
        long sendTime = System.currentTimeMillis() + baseDelay;
        long lastPositionPacketTime = System.currentTimeMillis();
        this.lock.lockRead();
        try {
            for (Map.Entry<DelayedPacketSendEntry, Long> entry : this.delayedPackets.entrySet()) {
                C03PacketPlayer movementPacket;
                DelayedPacketSendEntry delayedPacket = entry.getKey();
                Packet queuedPacket = delayedPacket.getEvent().getPacket();
                if (!queuedPacket.isInstance(MappedClasses.qD) || !(movementPacket = new C03PacketPlayer(queuedPacket)).hasPosition()) continue;
                lastPositionPacketTime = entry.getValue();
            }
        } finally {
            this.lock.unlockRead();
        }
        sendTime = Math.min(lastPositionPacketTime + 90L, sendTime);
        this.lock.lockWrite();
        try {
            this.delayedPackets.put(new DelayedPacketSendEntry(eventPacketSend), sendTime);
        } finally {
            this.lock.unlockWrite();
        }
        eventPacketSend.setCancelled(true);
    }

    @Listen(priority=EventPriority.NORMAL)
    public void onPacketSend(EventPacketSend eventPacketSend) {
        if (this.controlClaim.isClaimed()) {
            this.flushAll();
            return;
        }
        Packet packet = eventPacketSend.getPacket();
        if (eventPacketSend.isCanceled()) {
            return;
        }
        if (eventPacketSend.wasModified()) {
            return;
        }
        if (Minecraft.thePlayer().isNull() || this.dispatchGuard.R(packet) || packet.isInstance(MappedClasses.VP)) {
            return;
        }
        this.processPacket(eventPacketSend);
    }

    @Listen
    public void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
        this.targetEntity = null;
        RayTraceResult rayTraceResult = RayTraceUtil.F(10.0, 0.0f, true);
        if (rayTraceResult != null && rayTraceResult.isNotNull()) {
            this.targetEntity = rayTraceResult.getEntity();
        }
        this.lock.lockWrite();
        try {
            if (!this.delayedPackets.isEmpty()) {
                this.flushExpired();
            }
        } finally {
            this.lock.unlockWrite();
        }
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void capturePosition(EventPacketSend eventPacketSend) {
        C03PacketPlayer movementPacket;
        Packet packet = eventPacketSend.getPacket();
        if (packet.isInstance(MappedClasses.qD) && (movementPacket = new C03PacketPlayer(packet)).hasPosition()) {
            this.lastPositionSnapshot = new PlayerLocationSnapshot(movementPacket.getX(), movementPacket.getY(), movementPacket.getZ());
        }
    }

    private boolean releasePending() {
        this.lock.lockWrite();
        try {
            Iterator<Map.Entry<DelayedPacketSendEntry, Long>> iterator = this.delayedPackets.entrySet().iterator();
            if (!iterator.hasNext()) {
                return false;
            }
            DelayedPacketSendEntry delayedPacket = iterator.next().getKey();
            this.dispatchGuard.o(delayedPacket.getEvent());
            iterator.remove();
            return true;
        } finally {
            this.lock.unlockWrite();
        }
    }

    public FakeLagPacketDelaySubModule(HackModule parent, String name) {
        super(parent, name);
        this.controlClaim = SharedModuleControlClaims.primaryAction;
        this.addValue(this.transmissionOffset);
    }

    @Override
    public void onBeforeDisable() {
        this.flushAll();
    }

    @Override
    public String getDetailedSuffix() {
        return "Dynamic " + ((FakeLag)this.getParent()).delay.getDisplayValue() + "ms";
    }

    private void flushExpired() {
        Iterator<Map.Entry<DelayedPacketSendEntry, Long>> iterator = this.delayedPackets.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<DelayedPacketSendEntry, Long> entry = iterator.next();
            if (System.currentTimeMillis() < entry.getValue()) break;
            this.dispatchGuard.o(entry.getKey().getEvent());
            iterator.remove();
        }
    }
}
