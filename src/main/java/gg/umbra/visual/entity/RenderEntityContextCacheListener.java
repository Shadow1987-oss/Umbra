package gg.umbra.visual.entity;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventWorldChange;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.visual.entity.RenderEntityContext;
import gg.umbra.visual.entity.RenderEntityContextCache;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.WorldClient;
import java.util.LinkedHashSet;

public class RenderEntityContextCacheListener
implements EventListener {
    private long tickCounter;

    @Listen
    public void onWorldChange(EventWorldChange event) {
        RenderEntityContextCache.clear();
    }

    @Listen(priority=EventPriority.LOWEST)
    public void onTick(EventPreTick event) {
        ++this.tickCounter;
        if (this.tickCounter % 10L == 0L) {
            RenderEntityContextCache.clearNameCaches();
        }
        EntityPlayerSP viewer = event.getThePlayer();
        if (viewer.isNull()) {
            return;
        }
        WorldClient world = event.getWorld();
        if (world.isNull()) {
            return;
        }
        LinkedHashSet<Integer> staleEntityIds = new LinkedHashSet<>();
        for (RenderEntityContext context : RenderEntityContextCache.getContexts()) {
            Entity entity = world.V(context.getEntityId());
            if (entity.isNull() || !entity.isInstance(MappedClasses.zm)) {
                staleEntityIds.add(context.getEntityId());
                continue;
            }
            EntityLivingBase livingEntity = new EntityLivingBase(entity.getObject());
            context.update(livingEntity, viewer);
        }
        for (Integer entityId : staleEntityIds) {
            RenderEntityContextCache.remove(entityId);
        }
    }

}

