package gg.umbra.wrapper.impl;

public class SPacketSoundEffect
extends Packet {

    public SPacketSoundEffect(Object handle) {
        super(handle);
    }

    public String getParticleName() {
        if (ForgeVersion.MC_1_8_9.d()) {
            return new EnumParticleTypes(SPacketSoundEffect.umbraInstance.getMappings().Cs.getParticleType(this.I)).K();
        }
        return SPacketSoundEffect.umbraInstance.getMappings().Cs.getLegacyParticleName(this.I);
    }

    public double getZ() {
        return SPacketSoundEffect.umbraInstance.getMappings().Cs.getZ(this.I);
    }

    public double getX() {
        return SPacketSoundEffect.umbraInstance.getMappings().Cs.getX(this.I);
    }

    public double getY() {
        return SPacketSoundEffect.umbraInstance.getMappings().Cs.getY(this.I);
    }
}

