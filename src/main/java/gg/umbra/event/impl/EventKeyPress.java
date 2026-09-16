package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.config.Profile;
import gg.umbra.event.EventDispatcher;
import gg.umbra.event.EventListeners;
import gg.umbra.event.impl.EventKeyInputBase;
import gg.umbra.input.KeyboardInput;
import gg.umbra.module.HackModule;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;

public class EventKeyPress
extends EventKeyInputBase {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventKeyPress(int key, boolean down) {
        super(key, down);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if (!gg.umbra.settings.ClientSettings.INSTANCE.isInputEnabled() && KeyboardInput.isKeyDown(9) && KeyboardInput.isKeyDown(114)) {
            boolean timingEnabled = EventDispatcher.timingEnabled = !EventDispatcher.timingEnabled;
        }
        if (!gg.umbra.settings.ClientSettings.INSTANCE.isInputEnabled() && this.getKey() != 27) {
            boolean handled = gg.umbra.settings.ClientSettings.INSTANCE.handleSearchShortcut(this);
            return handled;
        }
        if (this.getKey() > 0 && this.isDown() && Minecraft.currentScreen().getObject() == null) {
            for (Profile profile : Umbra.INSTANCE.getProfilesManager().getProfiles()) {
                if (!profile.activateIfMatched(this.getKey())) continue;
            }
        }
        for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
            mod.u(this);
        }
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public boolean isKeybinding(KeyBinding keyBinding) {
        return this.getKey() == ClientSettings.getPlatformKeyCode(keyBinding);
    }

}

