package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.TutorialWelcomePanel;
import gg.umbra.ui.click.component.GuiClickListener;

class TutorialWelcomeStartClickHandler
implements GuiClickListener {
    final TutorialWelcomePanel F;

    TutorialWelcomeStartClickHandler(TutorialWelcomePanel tutorialWelcomePanel) {
        this.F = tutorialWelcomePanel;
    }

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getTutorialManager().startTutorial();
    }
}
