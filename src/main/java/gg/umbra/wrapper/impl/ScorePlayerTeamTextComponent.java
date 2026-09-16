package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MStringTextComponent;

public class ScorePlayerTeamTextComponent
extends MutableTextComponent {
    public ScorePlayerTeamTextComponent(Object object) {
        super(object);
    }


    public static ScorePlayerTeamTextComponent P(String string) {
        if (ForgeVersion.MC_1_20_6.v()) {
            Umbra.notifyNativeStackTrace();
        }
        return new ScorePlayerTeamTextComponent(MStringTextComponent.s(ScorePlayerTeamTextComponent.umbraInstance.getMappings().Dh, string));
    }

    public String Y() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.v()) {
            Umbra.notifyNativeStackTrace();
        }
        return ScorePlayerTeamTextComponent.umbraInstance.getMappings().Dh.W(this.getObject());
    }

    public static ScorePlayerTeamTextComponent B(String string) {
        return new ScorePlayerTeamTextComponent(ScorePlayerTeamTextComponent.umbraInstance.getMappings().Dh.i(string));
    }
}

