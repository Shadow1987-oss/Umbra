package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.TutorialState;
import gg.umbra.tutorial.TutorialWelcomePanel;
import gg.umbra.ui.click.component.GuiClickListener;

class TutorialWelcomeSkipAllClickHandler
implements GuiClickListener {
    final TutorialWelcomePanel F;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getTutorialManager().setState(TutorialState.COMPLETED_ALL);
    }

    TutorialWelcomeSkipAllClickHandler(TutorialWelcomePanel tutorialWelcomePanel) {
        this.F = tutorialWelcomePanel;
    }
}
