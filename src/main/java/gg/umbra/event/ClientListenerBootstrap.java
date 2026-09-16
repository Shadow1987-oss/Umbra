package gg.umbra.event;

import gg.umbra.event.EventDispatcher;
import gg.umbra.event.EventListener;
import gg.umbra.event.forge.ForgeClientChatReceivedEvent;
import gg.umbra.event.listener.ClientListenerBootstrapEventListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ClientListenerBootstrap {
    private final List<EventListener> listeners = Arrays.asList(new ForgeClientChatReceivedEvent(), new ClientListenerBootstrapEventListener());

    public void registerListeners() {
        for (EventListener listener : this.listeners) {
            EventDispatcher.getInstance().registerListener(listener, new Predicate[0]);
        }
    }

    public void unregisterListeners() {
        for (EventListener listener : this.listeners) {
            EventDispatcher.getInstance().unregisterListener(listener);
        }
    }
}
