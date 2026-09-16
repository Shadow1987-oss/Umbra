package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.NetHandlerPlayClientImpl;
import gg.umbra.wrapper.impl.NetworkManager;
import gg.umbra.wrapper.impl.Packet;
import org.jetbrains.annotations.Nullable;

public class EventPacketReceive
extends Event {
    @Nullable
    private NetworkManager networkManager;
    private final Object packetHandle;
    @Nullable
    private Packet packet;
    private final Object networkManagerHandle;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }


    public Packet getPacket() {
        if (this.packet == null) {
            this.packet = new Packet(this.packetHandle);
        }
        return this.packet;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if (!this.getNetworkManager().c().isInstance(MappedClasses.F1)) {
            return false;
        }
        return super.fire();
    }

    public Object getPacketInstance() {
        return this.packet == null ? this.packetHandle : this.packet.getObject();
    }

    public NetworkManager getNetworkManager() {
        if (this.networkManager == null) {
            this.networkManager = new NetworkManager(this.networkManagerHandle);
        }
        return this.networkManager;
    }

    public NetHandlerPlayClientImpl getNetHandler() {
        return new NetHandlerPlayClientImpl(this.getNetworkManager().c());
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public EventPacketReceive(Object networkManagerHandle, Object packetHandle) {
        this.networkManagerHandle = networkManagerHandle;
        this.packetHandle = packetHandle;
    }
}

