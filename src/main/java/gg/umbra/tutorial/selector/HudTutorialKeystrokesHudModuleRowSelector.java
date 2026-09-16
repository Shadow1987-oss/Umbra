package gg.umbra.tutorial.selector;

import gg.umbra.tutorial.TutorialTargetSelector;
import gg.umbra.tutorial.page.HudTutorialPage;
import gg.umbra.ui.click.frame.impl.hud.HudModuleListEntry;

public class HudTutorialKeystrokesHudModuleRowSelector
extends TutorialTargetSelector<HudModuleListEntry> {
    private final HudTutorialPage tutorialPage;
    private static final String targetModuleName = "Keystrokes";

    public HudTutorialKeystrokesHudModuleRowSelector(HudTutorialPage hudTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = hudTutorialPage;
    }

    private boolean matchesTargetName(HudModuleListEntry hudModuleListEntry) {
        return hudModuleListEntry.getModule().getName().equals(targetModuleName);
    }

    @Override
    public boolean matches(HudModuleListEntry hudModuleListEntry) {
        return this.matchesTargetName(hudModuleListEntry);
    }
}
