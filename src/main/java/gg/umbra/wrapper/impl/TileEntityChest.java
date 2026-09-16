package gg.umbra.wrapper.impl;

public class TileEntityChest
extends TileEntity {
    public float getLidOpenness() {
        if (ForgeVersion.MC_1_17.d()) {
            return TileEntityChest.umbraInstance.getMappingsMapperCompat().chestTileEntity.getOpenness(this.getObject(), 0.0f);
        }
        return TileEntityChest.umbraInstance.getMappingsMapperCompat().chestTileEntity.getStoredOpenness(this.getObject());
    }

    public TileEntityChest(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getOpenCount() {
        return TileEntityChest.umbraInstance.getMappingsMapperCompat().chestTileEntity.getOpenCount(this.getObject());
    }

}

