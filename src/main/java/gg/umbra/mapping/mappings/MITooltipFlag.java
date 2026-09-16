package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MITooltipFlag
extends Mapping {
    private static GuiComponent[] creativeTabsControlFlowState;
    private final MappingField searchTabField;

    public MITooltipFlag() {
        this(MITooltipFlag.getCreativeTabsControlFlowState());
    }

    private MITooltipFlag(GuiComponent[] controlFlowState) {
        super(MappedClasses.zX);
        GuiComponent[] currentControlFlowState = controlFlowState;
        if (ForgeVersion.MC_1_20_6.d()) {
            Class searchTabFieldType = MappedClasses.qB;
            boolean searchTabFieldPublic = true;
            String searchTabFieldName = "SEARCH";
            Class creativeModeTabsClass = MappedClasses.qj;
            MITooltipFlag mapping = this;
            this.searchTabField = mapping.registerStaticFieldForOwner(creativeModeTabsClass, searchTabFieldName, searchTabFieldPublic, searchTabFieldType);
        } else if (ForgeVersion.MC_1_12_2.d()) {
            Class searchTabFieldType = MappedClasses.zX;
            boolean searchTabFieldPublic = true;
            String searchTabFieldName = "SEARCH";
            MITooltipFlag mapping = this;
            this.searchTabField = mapping.registerStaticField(searchTabFieldName, searchTabFieldPublic, searchTabFieldType);
        } else {
            Class searchTabFieldType = MappedClasses.zX;
            boolean searchTabFieldPublic = true;
            String searchTabFieldName = "tabAllSearch";
            MITooltipFlag mapping = this;
            this.searchTabField = mapping.registerStaticField(searchTabFieldName, searchTabFieldPublic, searchTabFieldType);
        }
    }

    public static void setCreativeTabsControlFlowState(GuiComponent[] controlFlowState) {
        creativeTabsControlFlowState = controlFlowState;
    }


    public Object getSearchTab() {
        return this.searchTabField.getObject(null);
    }

    public static GuiComponent[] getCreativeTabsControlFlowState() {
        return creativeTabsControlFlowState;
    }

    static {
        MITooltipFlag.setCreativeTabsControlFlowState(new GuiComponent[2]);
    }
}
