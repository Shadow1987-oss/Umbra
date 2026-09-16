package gg.umbra.mapping.mappings;

import gg.umbra.Umbra;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MGlStateManager_BlendState
extends Mapping {
    public MappingField blendEnabledStateField;

    public Object getBlendEnabledState(Object blendStateHandle) {
        return this.blendEnabledStateField.getObject(blendStateHandle);
    }


    public MGlStateManager_BlendState() {
        this(MEntityRenderer.n());
    }

    private MGlStateManager_BlendState(int initializationState) {
        super(MappedClasses.Yk);
        if (initializationState != 0) {
            if (ForgeVersion.MC_1_20_6.d()) {
                // Umbra: BlendState.mode is the enabled-state field on 1.20.6+
                // (and on 26.1.x/26.2 the field is the same).
                this.blendEnabledStateField = this.fieldBuilder("mode", MappedClasses.U).buildField();
            } else if (Umbra.INSTANCE.isVanillaMinecraftPresent()) {
                this.blendEnabledStateField = this.J("blend", true, MappedClasses.U);
            } else {
                this.blendEnabledStateField = this.J("field_179213_a", Wrapper.isNativeAvailable, MappedClasses.U);
            }
            return;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            this.blendEnabledStateField = this.fieldBuilder("mode", MappedClasses.U).buildField();
        } else if (Umbra.INSTANCE.isVanillaMinecraftPresent()) {
            this.blendEnabledStateField = this.fieldBuilder("mode", MappedClasses.U).buildField();
        } else {
            this.blendEnabledStateField = this.J("field_179213_a", Wrapper.isNativeAvailable, MappedClasses.U);
        }
    }
}
