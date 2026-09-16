package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.MappingMethodBuilder;
import gg.umbra.mapping.mappings.MSPacketMapChunkBulk;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MEntityPotion
extends Mapping {
    private MappingMethod getItemMethod;
    private MappingField potionDamageField;
    private MappingMethod getPotionMethod;

    public Object getPotionItem(Object potionEntityHandle) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.getItemMethod.invokeObject(potionEntityHandle);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return this.getPotionMethod.invokeObject(potionEntityHandle);
        }
        return this.potionDamageField.getObject(potionEntityHandle);
    }

    public MEntityPotion() {
        this(MSPacketMapChunkBulk.getMappingControlFlowToken());
    }

    private MEntityPotion(String mappingControlFlowToken) {
        super(MappedClasses.Zf);
        if (mappingControlFlowToken != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.getItemMethod = ((MappingMethodBuilder)((MappingMethodBuilder)this
                        .methodBuilder("getItem", MappedClasses.VK)
                        .setOwnerClass(MappedClasses.ub))
                        .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Y4))
                        .buildMethod();
            } else if (ForgeVersion.MC_1_12_2.d()) {
                this.getPotionMethod = this.Y("getPotion", true, MappedClasses.VK);
            } else {
                this.potionDamageField = this.J("potionDamage", true, MappedClasses.VK);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MSPacketMapChunkBulk.setMappingControlFlowToken("JSVoh");
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            this.getPotionMethod = this.Y("getPotion", true, MappedClasses.VK);
        }
        this.potionDamageField = this.J("potionDamage", true, MappedClasses.VK);
        if (GuiComponent.getLegacyComponentState() == null) {
            MSPacketMapChunkBulk.setMappingControlFlowToken("JSVoh");
        }
    }
}
