package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.TutorialFinishedPanel;
import gg.umbra.tutorial.TutorialState;
import gg.umbra.ui.click.component.GuiClickListener;

public class TutorialFinishedAcknowledgeClickHandler
implements GuiClickListener {
    final TutorialFinishedPanel Z;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getTutorialManager().setState(TutorialState.FINISHED);
    }

    public TutorialFinishedAcknowledgeClickHandler(TutorialFinishedPanel tutorialFinishedPanel) {
        this.Z = tutorialFinishedPanel;
    }
}
