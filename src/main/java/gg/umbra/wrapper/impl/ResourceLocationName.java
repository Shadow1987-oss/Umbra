package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ResourceLocationName
extends Wrapper {
    public String getFriendlyReport(ResourceLocationKey resourceKey) {
        return ResourceLocationName.umbraInstance.getMappingsMapperCompat().resourceLocationName
                .getFriendlyReport(this.I, resourceKey.getObject());
    }

    public String getCompleteReport() {
        return ResourceLocationName.umbraInstance.getMappingsMapperCompat().resourceLocationName
                .getCompleteReport(this.I);
    }

    public ResourceLocationName(Object reportHandle) {
        super(reportHandle);
    }
}
