package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MEntityPlayerSP;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MEntityOtherPlayerMP
extends Mapping {
    private static final String CONSTRUCTOR = "<init>";
    private final MappingMethod constructor;

    public Object create(Object worldHandle, Object gameProfileHandle) {
        return this.constructor.newInstance(worldHandle, gameProfileHandle);
    }

    public MEntityOtherPlayerMP() {
        this(MEntityPlayerSP.r());
    }

    private MEntityOtherPlayerMP(GuiComponent[] legacyComponentState) {
        super(MappedClasses.lG);
        if (legacyComponentState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.Z, MappedClasses.VD);
            } else {
                this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.YU, MappedClasses.VD);
            }
            return;
        }
        this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.YU, MappedClasses.VD);
    }
}
