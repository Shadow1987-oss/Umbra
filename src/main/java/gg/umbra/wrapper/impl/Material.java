package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class Material
extends Wrapper {
    private static Material air;
    private static Material water;
    private static Material vine;

    public boolean isReplaceable() {
        return Material.umbraInstance.getMappingsMapperCompat().Cn.isReplaceable(this.I);
    }


    public static Material vine() {
        if (vine == null) {
            vine = new Material(Material.umbraInstance.getMappingsMapperCompat().Cn.getVine());
        }
        return vine;
    }

    public boolean isToolNotRequired() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return false;
        }
        return Material.umbraInstance.getMappingsMapperCompat().Cn.isToolNotRequired(this.I);
    }

    public static Material air() {
        if (air == null) {
            air = new Material(Material.umbraInstance.getMappingsMapperCompat().Cn.getAir());
        }
        return air;
    }

    public static Material water() {
        if (water == null) {
            water = new Material(Material.umbraInstance.getMappingsMapperCompat().Cn.getWater());
        }
        return water;
    }

    public static Material fire() {
        return new Material(Material.umbraInstance.getMappingsMapperCompat().Cn.getFire());
    }

    public boolean isLiquid() {
        return Material.umbraInstance.getMappingsMapperCompat().Cn.isLiquid(this.I);
    }

    public Material(Object object) {
        super(object);
    }

    public boolean blocksMovement() {
        return Material.umbraInstance.getMappingsMapperCompat().Cn.blocksMovement(this.I);
    }

    public boolean isSolid() {
        return Material.umbraInstance.getMappingsMapperCompat().Cn.isSolid(this.I);
    }
}

