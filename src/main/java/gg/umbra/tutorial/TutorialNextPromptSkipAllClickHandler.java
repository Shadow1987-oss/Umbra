package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.TutorialNextPromptPanel;
import gg.umbra.tutorial.TutorialState;
import gg.umbra.ui.click.component.GuiClickListener;

public class TutorialNextPromptSkipAllClickHandler
implements GuiClickListener {
    final TutorialNextPromptPanel T;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getTutorialManager().setState(TutorialState.COMPLETED_ALL);
    }

    public TutorialNextPromptSkipAllClickHandler(TutorialNextPromptPanel tutorialNextPromptPanel) {
        this.T = tutorialNextPromptPanel;
    }
}
