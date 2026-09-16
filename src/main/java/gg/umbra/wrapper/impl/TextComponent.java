package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MTextComponent;

public class TextComponent
extends ITextComponent {
    public static TextComponent p(Object object) {
        return new TextComponent(MTextComponent.n(TextComponent.umbraInstance.getMappingsMapperCompat().RA, object));
    }

    public TextComponent(Object object) {
        super(object);
    }

    public String U() {
        return (String)MTextComponent.s(TextComponent.umbraInstance.getMappingsMapperCompat().RA, this.I);
    }

    public Style w() {
        return new Style(MTextComponent.B(TextComponent.umbraInstance.getMappingsMapperCompat().RA, this.I));
    }

    public TextComponent(ScorePlayerTeam team, String playerName) {
        super(new TextComponent(Umbra.INSTANCE.getMappingsMapperCompat().scorePlayerTeam.formatPlayerNameComponent(team.getObject(), ScorePlayerTeamTextComponent.B(playerName).getObject())).getObject());
    }
}
