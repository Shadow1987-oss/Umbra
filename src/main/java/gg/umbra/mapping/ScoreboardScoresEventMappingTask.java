package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventScoreboardScores;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class ScoreboardScoresEventMappingTask
extends JavassistMappingTask {
    private static final String SCORES_RETURN_EXPRESSION = "$event.getScores();";

    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().scoreboard.listPlayerScoresMethod, EventScoreboardScores.class);
        eventInjectionSpec.setReturnExpression(SCORES_RETURN_EXPRESSION);
        this.registerEventInjection(eventInjectionSpec);
    }

    public ScoreboardScoresEventMappingTask() {
        super(MappedClasses.F6);
    }
}
