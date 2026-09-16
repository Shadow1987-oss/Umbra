package gg.umbra.system;

public class RightClickUseControlClaim
extends ModuleControlClaim {
    public void blockUse() {
        this.setClaimed(true);
    }
}
