package gg.umbra.tutorial;

import gg.umbra.Umbra;
import gg.umbra.tutorial.SingleComponentHighlightTutorialAction;
import gg.umbra.tutorial.TutorialPage;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import java.awt.Point;

public class SingleComponentHighlightAdvanceClickListener
implements GuiMouseListener {
    final SingleComponentHighlightTutorialAction H;

    @Override
    public void I(Point point) {
    }


    public SingleComponentHighlightAdvanceClickListener(SingleComponentHighlightTutorialAction singleComponentHighlightTutorialAction) {
        this.H = singleComponentHighlightTutorialAction;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (mouseClickButton != MouseClickButton.LEFT_CLICK) {
            return;
        }
        TutorialPage tutorialPage = this.H.getPage();
        if (tutorialPage.getCurrentAction() != null && tutorialPage.getCurrentAction().equals(this.H)) {
            Umbra.INSTANCE.getTutorialManager().getCurrentPage().advanceToNextAction();
        }
    }
}

