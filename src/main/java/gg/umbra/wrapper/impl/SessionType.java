package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SessionType
extends Wrapper {
    public SessionType mojang() {
        return new SessionType(SessionType.umbraInstance.getMappingsMapperCompat().sessionType.getMojang());
    }

    public SessionType msa() {
        return new SessionType(SessionType.umbraInstance.getMappingsMapperCompat().sessionType.getMsa());
    }

    public SessionType legacy() {
        return new SessionType(SessionType.umbraInstance.getMappingsMapperCompat().sessionType.getLegacy());
    }

    public SessionType(Object wrappedObject) {
        super(wrappedObject);
    }
}
