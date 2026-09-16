package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MEntity;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MEnderPearlProjectileBridge
extends Mapping {
    private MappingMethod getOwnerMethod;

    public MEnderPearlProjectileBridge() {
        this(MEntity.P());
    }

    private MEnderPearlProjectileBridge(int entityControlFlowState) {
        super(MappedClasses.lv);
        if (entityControlFlowState != 0) {
            if (ForgeVersion.MC_1_21_4.d()) {
                this.getOwnerMethod = this.Y("getOwner", true, MappedClasses.zc);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            this.getOwnerMethod = this.registerInstanceMethodForOwner(
                    MappedClasses.YV, "getOwner", true, MappedClasses.zc);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.getOwnerMethod = this.Y("getOwner", true, MappedClasses.zc);
        }
    }

    public Object getOwner(Object projectileHandle) {
        return this.getOwnerMethod.invokeObject(projectileHandle);
    }

}
