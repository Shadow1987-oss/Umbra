package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MResourceLocationKey;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MResourceLocationName
extends Mapping {
    private final MappingMethod completeReportMethod;
    private final MappingMethod friendlyReportMethod;

    public String getCompleteReport(Object reportHandle) {
        return (String)this.completeReportMethod.invokeObject(reportHandle);
    }

    public String getFriendlyReport(Object reportHandle, Object resourceKeyHandle) {
        return (String)this.friendlyReportMethod.invokeObject(reportHandle, resourceKeyHandle);
    }


    public MResourceLocationName() {
        this(MResourceLocationKey.A());
    }

    private MResourceLocationName(boolean controlFlowState) {
        super(MappedClasses.qA);
        if (controlFlowState) {
            GuiComponent.setLegacyComponentState(new GuiComponent[1]);
            this.friendlyReportMethod = null;
            if (ForgeVersion.MC_1_8_9.L()) {
                this.Y("getCompleteReport", true, String.class);
            }
            this.completeReportMethod = null;
            return;
        }
        if (MappedClasses.qJ != null) {
            this.friendlyReportMethod = this.Y(
                    "getFriendlyReport", true, String.class, MappedClasses.qJ);
        } else {
            this.friendlyReportMethod = null;
        }
        if (ForgeVersion.MC_1_8_9.L()) {
            this.completeReportMethod = this.Y("getCompleteReport", true, String.class);
        } else {
            this.completeReportMethod = null;
        }
    }
}
