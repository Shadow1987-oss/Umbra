package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTeam;
import gg.umbra.wrapper.Wrapper;

public class Team
extends Wrapper {
    public boolean isSameTeam(Team team) {
        return MTeam.isSameTeam(Team.umbraInstance.getMappingsMapperCompat().qW, this.I, team.getObject());
    }

    public Team(Object wrappedObject) {
        super(wrappedObject);
    }
}
