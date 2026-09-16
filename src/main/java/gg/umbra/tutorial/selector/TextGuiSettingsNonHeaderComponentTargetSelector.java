package gg.umbra.tutorial.selector;

import gg.umbra.tutorial.TutorialTargetSelector;
import gg.umbra.tutorial.page.TextGuiTutorialPage;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.frame.SettingsFrameHeaderComponent;

public class TextGuiSettingsNonHeaderComponentTargetSelector
extends TutorialTargetSelector<GuiComponent> {
    private final TextGuiTutorialPage tutorialPage;

    public TextGuiSettingsNonHeaderComponentTargetSelector(TextGuiTutorialPage textGuiTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = textGuiTutorialPage;
    }

    @Override
    public boolean matches(GuiComponent guiComponent) {
        boolean bl = !(guiComponent instanceof SettingsFrameHeaderComponent);
        return bl;
    }

}

