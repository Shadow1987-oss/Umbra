package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.TutorialNextPromptPanel;
import gg.umbra.ui.click.component.GuiClickListener;

public class TutorialNextPromptStartNextClickHandler
implements GuiClickListener {
    final TutorialNextPromptPanel V;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getTutorialManager().startNextPage();
    }

    public TutorialNextPromptStartNextClickHandler(TutorialNextPromptPanel tutorialNextPromptPanel) {
        this.V = tutorialNextPromptPanel;
    }
}
