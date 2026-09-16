package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingFieldBuilder;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MGlStateManager_TextureState
extends Mapping {
    public final MappingField textureNameField;

    public MGlStateManager_TextureState() {
        super(MappedClasses.Zn);
        this.textureNameField = ((MappingFieldBuilder)this.fieldBuilder("textureName", Integer.TYPE)
                .setNameForVersion(ForgeVersion.MC_1_20_6.n(), "binding"))
                .buildField();
    }

    public int getTextureName(Object textureStateHandle) {
        return this.textureNameField.getInt(textureStateHandle);
    }

    public void setTextureName(Object textureStateHandle, int textureName) {
        this.textureNameField.setInt(textureStateHandle, textureName);
    }
}

