package gg.umbra.wrapper.impl;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.mappings.MPacket;
import gg.umbra.hacks.exploits.backtrack.BacktrackPacketQueueEntry;
import gg.umbra.wrapper.Wrapper;

import java.util.function.Consumer;

public class Packet
extends Wrapper {
    private static boolean z;

    public static void d(boolean bl) {
        z = bl;
    }

    public Packet(Object handle) {
        super(handle);
    }


    public boolean hasPriority() {
        return MPacket.hasPriority(Packet.umbraInstance.getMappings().packet, this.I);
    }

    public static void n(Packet packet, Consumer<Packet> consumer) {
        if (ForgeVersion.MC_1_21_4.d() && packet.isInstance(MappedClasses.ue)) {
            BacktrackPacketQueueEntry backtrackPacketQueueEntry = new BacktrackPacketQueueEntry(packet);
            for (Packet packet2 : backtrackPacketQueueEntry.getPackets()) {
                consumer.accept(packet2);
            }
        } else {
            consumer.accept(packet);
        }
    }

    public static boolean A() {
        return z;
    }

    public void processPacket(NetworkPacketHandle networkPacketHandle) {
        MPacket.processPacket(Packet.umbraInstance.getMappings().packet, this.I, networkPacketHandle.getObject());
    }

    public static boolean h() {
        boolean bl = Packet.A();
        return !bl;
    }

    static {
        if (!Packet.h()) {
            Packet.d(true);
        }
    }
}

