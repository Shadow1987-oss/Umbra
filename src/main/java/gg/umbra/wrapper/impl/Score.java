package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MScore;
import gg.umbra.wrapper.Wrapper;

public class Score
extends Wrapper {
    public Score(Object object) {
        super(object);
    }

    public String getOwner() {
        return MScore.getOwner(Score.umbraInstance.getMappingsMapperCompat().score, this.I);
    }

    public int getScore() {
        return MScore.getScore(Score.umbraInstance.getMappingsMapperCompat().score, this.I);
    }
}
