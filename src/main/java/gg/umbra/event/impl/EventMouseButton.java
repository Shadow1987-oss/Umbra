package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.config.Profile;
import gg.umbra.event.EventListeners;
import gg.umbra.module.HackModule;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.KeyBinding;

public class EventMouseButton
extends EventKeyInputBase {
    public static int RIGHT_BUTTON;
    private static final EventListeners EVENT_LISTENERS;
    public static int LEFT_BUTTON;


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EventMouseButton(int button, boolean down) {
        super(button, down);
    }

    public boolean isKeybinding(KeyBinding keyBinding) {
        int platformKeyCode = ClientSettings.getPlatformKeyCode(keyBinding);
        int mouseButtonCode = ForgeVersion.MC_1_16_5.d() ? platformKeyCode : 100 + platformKeyCode;
        return this.getKey() == mouseButtonCode;
    }

    static {
        LEFT_BUTTON = 0;
        RIGHT_BUTTON = 1;
        EVENT_LISTENERS = new EventListeners();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public int getButton() {
        return super.getKey();
    }

    @Override
    public boolean fire() {
        int inputCode = -100 + this.getButton();
        if (this.getButtonState()) {
            for (Profile profile : Umbra.INSTANCE.getProfilesManager().getProfiles()) {
                if (!profile.activateIfMatched(inputCode)) continue;
                return this.isCanceled();
            }
        }
        for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
            if (mod.getBind().getBoundInputs().isEmpty()) continue;
            mod.getBind().handleInput(inputCode, this.getButtonState());
        }
        return super.fire();
    }

    public boolean getButtonState() {
        return super.isDown();
    }
}

