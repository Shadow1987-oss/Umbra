package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class PlayerDiggingAction
extends Wrapper {
    public static PlayerDiggingAction releaseUseItem() {
        return new PlayerDiggingAction(PlayerDiggingAction.umbraInstance.getMappingsMapperCompat().Ci.getReleaseUseItem());
    }

    public PlayerDiggingAction(Object wrappedObject) {
        super(wrappedObject);
    }
}
