package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.visual.hud.ScoreboardHudModule;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.ScoreObjective;

public class EventScoreboardObjectiveRender
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }


    public EventScoreboardObjectiveRender(Object objectiveOrMatrixStackHandle, Object objectiveOrRenderContextHandle) {
        ScoreboardHudModule scoreboardHudModule = Umbra.INSTANCE.getHackManager().getMod(ScoreboardHudModule.class);
        if (ForgeVersion.MC_1_16_5.d()) {
            scoreboardHudModule.updateObjective(new ScoreObjective(objectiveOrRenderContextHandle));
        } else {
            scoreboardHudModule.updateObjective(new ScoreObjective(objectiveOrMatrixStackHandle));
        }
    }

    public EventScoreboardObjectiveRender(Object objectiveHandle, int x, int y, Object scoreboardHandle) {
        ScoreboardHudModule scoreboardHudModule = Umbra.INSTANCE.getHackManager().getMod(ScoreboardHudModule.class);
        scoreboardHudModule.updateObjective(new ScoreObjective(objectiveHandle));
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        ScoreboardHudModule scoreboardHudModule = Umbra.INSTANCE.getHackManager().getMod(ScoreboardHudModule.class);
        this.setCancelled(scoreboardHudModule.boolean_r());
        return super.fire();
    }
}

