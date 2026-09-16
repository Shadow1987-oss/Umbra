package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.wrapper.Wrapper;

public class MEnumHand
extends Mapping {
    private final MappingField offHandField;
    private final MappingField mainHandField;

    public MEnumHand() {
        super(MappedClasses.Yf);
        this.mainHandField = this.registerStaticField("MAIN_HAND", Wrapper.isNativeAvailable, MappedClasses.Yf);
        this.offHandField = this.registerStaticField("OFF_HAND", Wrapper.isNativeAvailable, MappedClasses.Yf);
    }

    public Object getOffHand() {
        return this.offHandField.getObject(null);
    }

    public Object getMainHand() {
        return this.mainHandField.getObject(null);
    }
}

