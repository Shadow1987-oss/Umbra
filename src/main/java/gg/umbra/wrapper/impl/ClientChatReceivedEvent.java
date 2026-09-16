package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ClientChatReceivedEvent
extends Wrapper {
    public ClientChatReceivedEvent(Object object) {
        super(object);
    }

    public ITextComponent P() {
        return new ITextComponent(ClientChatReceivedEvent.umbraInstance.getMappingsMapperCompat().qP.V(this.I));
    }

    public void t(ITextComponent iTextComponent) {
        ClientChatReceivedEvent.umbraInstance.getMappingsMapperCompat().qP.i(this.I, iTextComponent.getObject());
    }
}

