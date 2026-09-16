package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ForgeEvent
extends Wrapper {
    public void setCancelled(boolean bl) {
        ForgeEvent.umbraInstance.getMappingsMapperCompat().forgeEvent.setCanceled(this.getObject(), bl);
    }

    public ForgeEvent(Object object) {
        super(object);
    }
}
