package gg.umbra.wrapper.impl;

public class TileEntityMobSpawner
extends TileEntity {
    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return new MobSpawnerBaseLogic(TileEntityMobSpawner.umbraInstance.getMappingsMapperCompat().mobSpawnerTileEntity.getSpawnerBaseLogic(this.I));
    }

    public TileEntityMobSpawner(Object wrappedObject) {
        super(wrappedObject);
    }
}
