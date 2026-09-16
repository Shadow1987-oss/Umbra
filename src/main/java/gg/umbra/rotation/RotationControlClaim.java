package gg.umbra.rotation;

import gg.umbra.module.HackModule;
import gg.umbra.system.ModuleControlClaim;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.RotationManager;

public class RotationControlClaim
extends ModuleControlClaim {
    public boolean isClaimed() {
        return super.isClaimed();
    }

    public boolean isBlockedFor(HackModule mod) {
        boolean blocked = false;
        if (this.isClaimed() && !this.isCurrentOwner(mod) && !this.hasHigherPriorityThanCurrent(mod)) {
            blocked = true;
        }
        if (this.hasPendingOwner() && !this.isPendingOwner(mod) && this.hasHigherPriorityThanCurrent(this.pendingOwner)) {
            blocked = true;
        }
        return blocked;
    }

    public RotationControlClaim() {
        super(true);
    }

    public boolean release(HackModule mod) {
        return super.release(mod);
    }

    public boolean isOwnedBy(HackModule mod) {
        return this.isClaimed() && this.isCurrentOwner(mod) && !this.hasPendingOwner();
    }

    public boolean acquire(HackModule mod) {
        return this.acquire(mod, false);
    }


    public boolean acquire(HackModule mod, boolean allowPreemption) {
        if (allowPreemption && this.isClaimed() && !this.isCurrentOwner(mod) && RotationManager.INSTANCE.hasAdaptiveController()) {
            AdaptiveRotationController controller = (AdaptiveRotationController)RotationManager.INSTANCE.getActiveController();
            if (this.hasHigherPriorityThanCurrent(mod) || controller.isRelativeMode()) {
                HackModule previousOwner = this.owner;
                this.clearPendingOwner();
                this.release(previousOwner);
            }
        }
        return this.tryAcquire(mod);
    }
}
