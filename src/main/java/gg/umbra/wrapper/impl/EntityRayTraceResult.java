package gg.umbra.wrapper.impl;

public class EntityRayTraceResult
extends RayTraceResult {
    public Entity getHitEntity() {
        return new Entity(EntityRayTraceResult.umbraInstance.getMappingsMapperCompat().entityRayTraceResult
                .getEntity(this.I));
    }

    public EntityRayTraceResult(Object rayTraceResultHandle) {
        super(rayTraceResultHandle);
    }

    public static EntityRayTraceResult create(Entity entity, Vec3 hitPosition) {
        Object resultHandle = EntityRayTraceResult.umbraInstance.getMappingsMapperCompat().entityRayTraceResult
                .create(entity.getObject(), hitPosition.getObject());
        return new EntityRayTraceResult(resultHandle);
    }
}
