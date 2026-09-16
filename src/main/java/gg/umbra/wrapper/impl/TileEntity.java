package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class TileEntity
extends Wrapper {
    private static boolean usesLegacyCoordinates;


    public TileEntity(Object wrappedObject) {
        super(wrappedObject);
    }

    public BlockPos getBlockPos() {
        return new BlockPos(TileEntity.umbraInstance.getMappingsMapperCompat().tileEntity.getBlockPos(this.I));
    }

    public int getX() {
        if (usesLegacyCoordinates) {
            return TileEntity.umbraInstance.getMappingsMapperCompat().tileEntity.getX(this.I);
        }
        return this.getBlockPos().getX();
    }

    public static boolean setUsesLegacyCoordinates(boolean legacyCoordinates) {
        usesLegacyCoordinates = legacyCoordinates;
        return usesLegacyCoordinates;
    }

    public int getY() {
        if (usesLegacyCoordinates) {
            return TileEntity.umbraInstance.getMappingsMapperCompat().tileEntity.getY(this.I);
        }
        return this.getBlockPos().getY();
    }

    public int getZ() {
        if (usesLegacyCoordinates) {
            return TileEntity.umbraInstance.getMappingsMapperCompat().tileEntity.getZ(this.I);
        }
        return this.getBlockPos().getZ();
    }
}

