package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.wrapper.impl.GuiScreen;

public class EventGuiOpen
extends Event {
    private GuiScreen guiScreen;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        if (ClientSettings.pendingSanityReset && Umbra.INSTANCE.getClientSettings().sanityCheck.getEffectiveValue().booleanValue()) {
            ClientSettings.pendingSanityReset = false;
            boolean modulesDisabled = false;
            if (this.guiScreen.isInstance(MappedClasses.u5) || this.guiScreen.isInstance(MappedClasses.D6) || this.guiScreen.isInstance(MappedClasses.F_)) {
                for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
                    if (mod instanceof HudModule || mod.getCategory() == Category.NONE || !mod.isEnabled()) continue;
                    mod.setEnabled(false);
                    modulesDisabled = true;
                }
            }
            if (modulesDisabled) {
                Umbra.INSTANCE.getNotificationManager().showInfo("Sanity Check", "All modules have been disabled!", 5000L);
            }
        }
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public GuiScreen getGuiScreen() {
        return this.guiScreen;
    }

    public Object getGuiScreenObject() {
        return this.guiScreen.getObject();
    }

    public EventGuiOpen(Object guiScreenHandle) {
        this.guiScreen = new GuiScreen(guiScreenHandle);
    }

    public void setGuiScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}

