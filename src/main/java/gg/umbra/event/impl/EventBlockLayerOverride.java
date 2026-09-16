package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.EnumWorldBlockLayer;

public class EventBlockLayerOverride
extends Event {
    private boolean shouldRender;
    private final Block block;
    private static int obfuscationState;
    private static final EventListeners EVENT_LISTENERS;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public Block getBlock() {
        return this.block;
    }

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockRenderDecision(this);
        return true;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventBlockLayerOverride(Object blockHandle) {
        this.block = new Block(blockHandle);
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    public static int getBlockLayerObfuscationState() {
        return obfuscationState;
    }

    public static void setBlockLayerObfuscationState(int state) {
        obfuscationState = state;
    }

    public static int getObfuscationConstant() {
        int state = EventBlockLayerOverride.getBlockLayerObfuscationState();
        return 2;
    }

    public Object getBlockLayer() {
        return this.shouldRender ? EnumWorldBlockLayer.solid().getObject() : EnumWorldBlockLayer.translucent().getObject();
    }

    static {
        EVENT_LISTENERS = new EventListeners();
        EventBlockLayerOverride.setBlockLayerObfuscationState(0);
    }
}

