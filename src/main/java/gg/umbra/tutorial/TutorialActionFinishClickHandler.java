package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.TutorialPage;
import gg.umbra.ui.click.component.GuiClickListener;

class TutorialActionFinishClickHandler
implements GuiClickListener {
    final TutorialPage w;

    TutorialActionFinishClickHandler(TutorialPage tutorialPage) {
        this.w = tutorialPage;
    }

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getTutorialManager().completeCurrentPage();
    }
}
