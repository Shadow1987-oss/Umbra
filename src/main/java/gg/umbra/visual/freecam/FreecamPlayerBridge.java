package gg.umbra.visual.freecam;

import gg.umbra.mapping.mappings.MFreecamPlayerBridge;
import gg.umbra.wrapper.Wrapper;

public class FreecamPlayerBridge
extends Wrapper {
    public void setCloakZ(double z) {
        MFreecamPlayerBridge.A(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I, z);
    }

    public double getPreviousCloakZ() {
        return MFreecamPlayerBridge.Z(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }

    public void setPreviousCloakZ(double z) {
        MFreecamPlayerBridge.d(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I, z);
    }

    public double getPreviousCloakY() {
        return MFreecamPlayerBridge.m(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }

    public double getCloakY() {
        return MFreecamPlayerBridge.Y(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }

    public double getPreviousCloakX() {
        return MFreecamPlayerBridge.C(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }

    public void setCloakX(double x) {
        MFreecamPlayerBridge.c(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I, x);
    }

    public double getCloakX() {
        return MFreecamPlayerBridge.T(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }

    public void setPreviousCloakY(double y) {
        MFreecamPlayerBridge.E(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I, y);
    }

    public void setCloakY(double y) {
        MFreecamPlayerBridge.n(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I, y);
    }

    public void resetBobbingState() {
        MFreecamPlayerBridge.m$src$V$n8prsk(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }

    public void setPreviousCloakX(double x) {
        MFreecamPlayerBridge.y(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I, x);
    }

    public FreecamPlayerBridge(Object object) {
        super(object);
    }

    public double getCloakZ() {
        return MFreecamPlayerBridge.l(FreecamPlayerBridge.umbraInstance.getMappings().w, this.I);
    }
}
