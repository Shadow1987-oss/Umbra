package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class Blocks
extends Wrapper {
    private static Block ladder;
    private static Block air;
    private static Block powderSnow;
    private static Block stone;

    public Blocks(Object wrappedObject) {
        super(wrappedObject);
    }

    public static Block air() {
        if (air == null) {
            air = new Block(Blocks.umbraInstance.getMappingsMapperCompat().blocks.getAir());
        }
        return air;
    }

    public static Block stone() {
        if (stone == null) {
            stone = new Block(Blocks.umbraInstance.getMappingsMapperCompat().blocks.getStone());
        }
        return stone;
    }

    public static Block ladder() {
        if (ladder == null) {
            ladder = new Block(Blocks.umbraInstance.getMappingsMapperCompat().blocks.getLadder());
        }
        return ladder;
    }

    public static Block powderSnow() {
        if (powderSnow == null) {
            powderSnow = new Block(Blocks.umbraInstance.getMappingsMapperCompat().blocks.getPowderSnow());
        }
        return powderSnow;
    }

}

