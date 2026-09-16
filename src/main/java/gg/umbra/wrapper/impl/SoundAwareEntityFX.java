package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MSoundAwareEntityFX;

public class SoundAwareEntityFX
extends EntityFX {
    public EnumParticleTypes Z$src$Lgg_umbra_wrapper_impl_EnumParticleTypes_$1aa3947() {
        return new EnumParticleTypes(MSoundAwareEntityFX.b(SoundAwareEntityFX.umbraInstance.getMappings().R4, this.I));
    }

    public void O(Entity entity) {
        MSoundAwareEntityFX.u(SoundAwareEntityFX.umbraInstance.getMappings().R4, this.I, entity.getObject());
    }

    public Entity M$src$Lgg_umbra_wrapper_impl_Entity_$1791qxt() {
        return new Entity(MSoundAwareEntityFX.q(SoundAwareEntityFX.umbraInstance.getMappings().R4, this.I));
    }

    public SoundAwareEntityFX(Object object) {
        super(object);
    }
}

