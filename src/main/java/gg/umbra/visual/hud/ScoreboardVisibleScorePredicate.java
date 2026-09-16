package gg.umbra.visual.hud;

import com.google.common.base.Predicate;
import gg.umbra.visual.hud.ScoreboardHudModule;
import gg.umbra.wrapper.impl.Score;

class ScoreboardVisibleScorePredicate
implements Predicate<Score> {
    @Override
    public boolean apply(Score score) {
        return score.getOwner() != null && !score.getOwner().startsWith("#");
    }

    ScoreboardVisibleScorePredicate() {
    }
}
