package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class PlayerEventNameFormat
extends Wrapper {
    public String getUrl() {
        return PlayerEventNameFormat.umbraInstance.getMappingsMapperCompat().resourcePackSendPacket.getUrl(this.I);
    }

    public void setUrl(String url) {
        PlayerEventNameFormat.umbraInstance.getMappingsMapperCompat().resourcePackSendPacket.setUrl(this.I, url);
    }

    public PlayerEventNameFormat(Object handle) {
        super(handle);
    }

    public String getHash() {
        return PlayerEventNameFormat.umbraInstance.getMappingsMapperCompat().resourcePackSendPacket.getHash(this.I);
    }
}
