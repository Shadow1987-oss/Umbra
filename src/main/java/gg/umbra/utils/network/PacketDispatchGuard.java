package gg.umbra.utils.network;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.utils.concurrent.ReadWriteLockHelper;
import gg.umbra.utils.network.PacketDispatchMarkerRegistry;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.NetHandlerPlayClientImpl;
import gg.umbra.wrapper.impl.NetworkManager;
import gg.umbra.wrapper.impl.NetworkPacketHandle;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.PlayerControllerMP;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import gg.umbra.Umbra;

public class PacketDispatchGuard
implements EventListener {
    private final ReadWriteLockHelper K;
    private final Set<Object> s = ConcurrentHashMap.newKeySet();
    private boolean B = false;
    public static PacketDispatchGuard b = new PacketDispatchGuard();

    public boolean o(EventPacketSend eventPacketSend) {
        if (PacketDispatchMarkerRegistry.J(eventPacketSend.getPacket())) {
            return false;
        }
        if (PacketDispatchMarkerRegistry.S(eventPacketSend.getPacket())) {
            return false;
        }
        NetworkManager networkManager = eventPacketSend.getNetworkManager();
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            NetworkPacketHandle networkPacketHandle = networkManager.c();
            if (!networkPacketHandle.isInstance(MappedClasses.F1)) {
                return false;
            }
            this.R(eventPacketSend.getPacket());
            try {
                networkManager.G(eventPacketSend.getPacket());
                return true;
            }
            catch (Exception exception) {}
        } else {
            networkManager.getChannel().eventLoop().execute(() -> this.lambda$invokeWrite$1(eventPacketSend));
        }
        return false;
    }

    private void lambda$invokeRead$0(Packet packet, NetHandlerPlayClientImpl netHandlerPlayClientImpl) {
        this.l(packet, netHandlerPlayClientImpl);
    }

    public static void B(Runnable runnable) {
        PlayerControllerMP playerControllerMP = Minecraft.playerController();
        if (playerControllerMP.isNull()) {
            return;
        }
        NetHandlerPlayClientImpl netHandlerPlayClientImpl = playerControllerMP.n();
        NetworkManager networkManager = netHandlerPlayClientImpl.a();
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            runnable.run();
        } else {
            networkManager.getChannel().eventLoop().execute(runnable);
        }
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        this.J(eventPacketReceive.getPacket());
    }

    public void J(Packet packet) {
        this.s.remove(packet.getObject());
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onPacketSend(EventPacketSend eventPacketSend) {
        this.J(eventPacketSend.getPacket());
    }

    public boolean o(Packet packet) {
        boolean bl = this.s.contains(packet.getObject());
        return bl;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public boolean R(Packet packet) {
        boolean bl = this.s.add(packet.getObject());
        return !bl;
    }

    public static void b(NetworkManager networkManager, Runnable runnable) {
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            runnable.run();
        } else {
            networkManager.getChannel().eventLoop().execute(runnable);
        }
    }

    public PacketDispatchGuard() {
        this.K = new ReadWriteLockHelper();
    }

    private void lambda$invokeWrite$1(EventPacketSend eventPacketSend) {
        this.o(eventPacketSend);
    }

    public void l(Packet packet, NetHandlerPlayClientImpl netHandlerPlayClientImpl) {
        if (netHandlerPlayClientImpl.isNull()) {
            return;
        }
        NetworkManager networkManager = netHandlerPlayClientImpl.a();
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            this.R(packet);
            try {
                packet.processPacket(netHandlerPlayClientImpl);
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
            this.J(packet);
        } else {
            networkManager.getChannel().eventLoop().execute(() -> this.lambda$invokeRead$0(packet, netHandlerPlayClientImpl));
        }
    }
}
