package gg.umbra.wrapper.impl;

public class SPacketBlockChange
extends Packet {
    public SPacketBlockChange(Object handle) {
        super(handle);
    }

    public int getLegacyX() {
        return SPacketBlockChange.umbraInstance.getMappings().CN.getLegacyX(this.I);
    }

    public BlockState getBlockState() {
        return new BlockState(SPacketBlockChange.umbraInstance.getMappings().CN.getBlockState(this.I));
    }

    public BlockPos getBlockPosition() {
        return new BlockPos(SPacketBlockChange.umbraInstance.getMappings().CN.getBlockPosition(this.I));
    }

    public int getLegacyY() {
        return SPacketBlockChange.umbraInstance.getMappings().CN.getLegacyY(this.I);
    }

    public int getLegacyZ() {
        return SPacketBlockChange.umbraInstance.getMappings().CN.getLegacyZ(this.I);
    }
}
