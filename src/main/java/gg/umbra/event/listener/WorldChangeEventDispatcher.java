package gg.umbra.event.listener;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventWorldChange;
import gg.umbra.wrapper.impl.WorldClient;

public class WorldChangeEventDispatcher
implements EventListener {
    private WorldClient currentWorld;


    @Listen(priority=EventPriority.LOW)
    public void onTick(EventPreTick eventTick) {
        WorldClient world = eventTick.getWorld();
        boolean worldUnavailable = world.isNull();
        if (this.currentWorld == null && !worldUnavailable) {
            new EventWorldChange(this.currentWorld, world).fire();
            this.currentWorld = world;
        } else if (this.currentWorld != null && !worldUnavailable && this.currentWorld.getObject() != world.getObject()) {
            new EventWorldChange(this.currentWorld, world).fire();
            this.currentWorld = world;
        } else if (this.currentWorld != null && worldUnavailable) {
            new EventWorldChange(this.currentWorld, null).fire();
            this.currentWorld = null;
        }
    }
}

