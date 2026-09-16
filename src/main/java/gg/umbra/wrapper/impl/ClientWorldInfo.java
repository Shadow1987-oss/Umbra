package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ClientWorldInfo
extends Wrapper {
    public void setDayTime(long dayTime) {
        ClientWorldInfo.umbraInstance.getMappingsMapperCompat().clientWorldInfo.setDayTime(this.I, dayTime);
    }

    public ClientWorldInfo(Object wrappedObject) {
        super(wrappedObject);
    }

    public void setGameTime(long gameTime) {
        ClientWorldInfo.umbraInstance.getMappingsMapperCompat().clientWorldInfo.setGameTime(this.I, gameTime);
    }
}
