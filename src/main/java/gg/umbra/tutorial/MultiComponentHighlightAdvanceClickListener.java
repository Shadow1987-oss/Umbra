package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.MultiComponentHighlightTutorialAction;
import gg.umbra.tutorial.TutorialPage;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import java.awt.Point;

class MultiComponentHighlightAdvanceClickListener
implements GuiMouseListener {
    final MultiComponentHighlightTutorialAction o;

    @Override
    public void I(Point point) {
    }


    MultiComponentHighlightAdvanceClickListener(MultiComponentHighlightTutorialAction multiComponentHighlightTutorialAction) {
        this.o = multiComponentHighlightTutorialAction;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (mouseClickButton != MouseClickButton.LEFT_CLICK) {
            return;
        }
        TutorialPage tutorialPage = this.o.getPage();
        if (tutorialPage.getCurrentAction() != null && tutorialPage.getCurrentAction().equals(this.o)) {
            Umbra.INSTANCE.getTutorialManager().getCurrentPage().advanceToNextAction();
        }
    }
}

