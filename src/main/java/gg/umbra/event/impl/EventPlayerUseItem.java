package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.wrapper.impl.ItemStack;

public class EventPlayerUseItem
extends Event {
    private final ItemStack itemStack;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public EventPlayerUseItem(Object itemStackHandle) {
        this.itemStack = new ItemStack(itemStackHandle);
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }
}
