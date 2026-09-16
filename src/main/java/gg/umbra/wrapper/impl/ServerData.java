package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MServerData;
import gg.umbra.wrapper.Wrapper;

public class ServerData
extends Wrapper {
    public ServerData(Object wrappedObject) {
        super(wrappedObject);
    }

    public String getServerIp() {
        return MServerData.getServerIp(ServerData.umbraInstance.getMappingsMapperCompat().ht, this.I);
    }
}
