package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MScoreObjective;
import gg.umbra.wrapper.Wrapper;

public class ScoreObjective
extends Wrapper {
    public TextComponent getFormattedDisplayName() {
        return new TextComponent(MScoreObjective.getFormattedDisplayName(ScoreObjective.umbraInstance.getMappingsMapperCompat().scoreObjective, this.I));
    }

    public ITextComponent getDisplayNameComponent() {
        ITextComponent displayName = new ITextComponent(MScoreObjective.getDisplayNameComponent(ScoreObjective.umbraInstance.getMappingsMapperCompat().scoreObjective, this.I));
        return displayName;
    }

    public Scoreboard getScoreboard() {
        return new Scoreboard(MScoreObjective.getScoreboard(ScoreObjective.umbraInstance.getMappingsMapperCompat().scoreObjective, this.I));
    }

    public String getDisplayNameText() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getDisplayNameComponent().getFormattedText();
        }
        return MScoreObjective.getDisplayNameText(ScoreObjective.umbraInstance.getMappingsMapperCompat().scoreObjective, this.I);
    }

    public ScoreObjective(Object object) {
        super(object);
    }

}

