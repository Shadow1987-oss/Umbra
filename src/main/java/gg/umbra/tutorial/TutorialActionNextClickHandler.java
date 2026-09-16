package gg.umbra.tutorial;

import gg.umbra.tutorial.TutorialPage;
import gg.umbra.ui.click.component.GuiClickListener;

class TutorialActionNextClickHandler
implements GuiClickListener {
    final TutorialPage o;

    @Override
    public void onPrimaryClick() {
        this.o.advanceToNextAction();
    }

    TutorialActionNextClickHandler(TutorialPage bF) {
        this.o = bF;
    }
}
