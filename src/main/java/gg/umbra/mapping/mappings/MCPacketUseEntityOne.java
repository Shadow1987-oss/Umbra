package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MCPacketUseEntityOne
extends Mapping {
    private final MappingMethod playerAbilitiesPacketConstructor;

    public MCPacketUseEntityOne() {
        super(MappedClasses.z1);
        this.playerAbilitiesPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{MappedClasses.q9});
    }

    public Object createPlayerAbilitiesPacket(Object playerCapabilities) {
        return this.playerAbilitiesPacketConstructor.newInstance(playerCapabilities);
    }
}

