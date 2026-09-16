package gg.umbra.mapping.mappings;

import gg.umbra.Umbra;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MEntityPlayerSP;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MAbstractClientPlayerBridge
extends Mapping {
    private MappingField clientAvatarStateField;
    private MappingMethod locationSkinMethod;

    public Object getClientAvatarState(Object playerHandle) {
        return this.clientAvatarStateField.getObject(playerHandle);
    }

    public MAbstractClientPlayerBridge() {
        this(MEntityPlayerSP.r());
    }

    private MAbstractClientPlayerBridge(GuiComponent[] guiComponentArray) {
        super(MappedClasses.zt);
        if (guiComponentArray != null) {
            if (ForgeVersion.MC_1_21_10.d() && Umbra.INSTANCE.isFabricMinecraftPresent()) {
                this.locationSkinMethod = this.registerInstanceMethodForOwner(
                        MappedClasses.lB, "getSkin", true, MappedClasses.uZ, new Class[]{});
            } else {
                this.locationSkinMethod = this.Y("getLocationSkin", true, MappedClasses.zC, new Class[]{});
            }
            if (ForgeVersion.MC_1_21_10.d()) {
                this.clientAvatarStateField = this.J("clientAvatarState", true, MappedClasses.zT);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            this.locationSkinMethod = this.registerInstanceMethodForOwner(
                    MappedClasses.lB, "getSkin", true, MappedClasses.uZ, new Class[]{});
        }
        this.locationSkinMethod = this.Y("getLocationSkin", true, MappedClasses.zC, new Class[]{});
        if (ForgeVersion.MC_1_21_10.d()) {
            this.clientAvatarStateField = this.J("clientAvatarState", true, MappedClasses.zT);
        }
    }

    public Object getLocationSkin(Object playerHandle) {
        return this.locationSkinMethod.invokeObject(playerHandle, new Object[0]);
    }
}
