package gg.umbra.system;

import gg.umbra.module.HackModule;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModuleControlClaim {
    private final AtomicBoolean claimed;
    private final Map<HackModule, Integer> priorities = new HashMap<>();
    private final AtomicBoolean pendingOwnerSet = new AtomicBoolean();
    protected HackModule owner;
    protected HackModule pendingOwner;
    private final boolean priorityAware;

    public ModuleControlClaim() {
        this(false);
    }

    public ModuleControlClaim(boolean priorityAware) {
        this.claimed = new AtomicBoolean();
        this.priorityAware = priorityAware;
    }

    public void setPriority(HackModule module, int priority) {
        this.priorities.put(module, priority);
    }

    protected boolean isCurrentOwner(HackModule module) {
        return this.owner != null && this.owner.equals(module);
    }

    public void setClaimed(boolean claimed) {
        this.claimed.set(claimed);
    }

    private void setPendingOwner(HackModule module) {
        this.pendingOwnerSet.set(true);
        this.pendingOwner = module;
    }

    public boolean hasPendingOwner() {
        return this.pendingOwnerSet.get();
    }

    protected boolean release(HackModule module) {
        if (this.priorityAware) {
            if (this.isPendingOwner(module)) {
                this.clearPendingOwner();
                return true;
            }
            if (!this.isCurrentOwner(module)) {
                return false;
            }
        }
        this.owner = null;
        this.claimed.set(false);
        return true;
    }

    public void clearPendingOwner() {
        this.pendingOwnerSet.set(false);
        this.pendingOwner = null;
    }

    protected boolean hasHigherPriorityThanCurrent(HackModule module) {
        int requestedPriority = this.priorities.getOrDefault(module, 0);
        int currentPriority = this.priorities.getOrDefault(this.owner, 0);
        return requestedPriority > currentPriority;
    }

    protected boolean tryClaim(HackModule module) {
        if (this.priorityAware) {
            if (this.claimed.get() || this.hasPendingOwner() && !this.isPendingOwner(module)) {
                return false;
            }
            this.clearPendingOwner();
        }
        this.owner = module;
        this.claimed.set(true);
        return true;
    }

    public boolean isClaimed() {
        return this.claimed.get();
    }

    protected boolean isPendingOwner(HackModule module) {
        return this.pendingOwner != null && this.pendingOwner.equals(module);
    }

    public void clearClaimed() {
        this.claimed.set(false);
    }

    public void markClaimed() {
        this.claimed.set(true);
    }

    public boolean tryAcquire(HackModule module) {
        if (this.tryClaim(module)) {
            return true;
        }
        if (this.priorityAware && this.isClaimed() && !this.isCurrentOwner(module)) {
            if (this.hasPendingOwner() && !this.isPendingOwner(module)) {
                return false;
            }
            if (!this.hasHigherPriorityThanCurrent(module)) {
                return false;
            }
            this.setPendingOwner(module);
        }
        return false;
    }
}
