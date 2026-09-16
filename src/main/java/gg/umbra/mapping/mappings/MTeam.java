package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MTeam
extends Mapping {
    private static final String IS_SAME_TEAM_METHOD_NAME = "isSameTeam";
    private final MappingMethod isSameTeamMethod;

    public static boolean isSameTeam(MTeam mapping, Object team, Object otherTeam) {
        return mapping.invokeIsSameTeam(team, otherTeam);
    }

    private boolean invokeIsSameTeam(Object team, Object otherTeam) {
        return this.isSameTeamMethod.invokeBoolean(team, otherTeam);
    }

    public MTeam() {
        super(MappedClasses.Yh);
        this.isSameTeamMethod = this.Y(IS_SAME_TEAM_METHOD_NAME, true, Boolean.TYPE, new Class[]{MappedClasses.Yh});
    }
}

