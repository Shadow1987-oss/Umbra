package gg.umbra.system;

import gg.umbra.Umbra;
import gg.umbra.hacks.exploits.AutoLadder;
import gg.umbra.hacks.exploits.BlockIn;
import gg.umbra.module.HackModule;
import gg.umbra.tools.MLG;
import gg.umbra.wrapper.impl.EntityPlayerSP;

public class FallRescuePriorityManager {
    public static final FallRescuePriorityManager INSTANCE = new FallRescuePriorityManager();

    private long lastStateKey = Long.MIN_VALUE;
    private Boolean lastClutchCovered;
    private Boolean lastAutoLadderCovered;

    private FallRescuePriorityManager() {
    }

    public boolean shouldStandDown(HackModule self, EntityPlayerSP player) {
        if (player == null || player.isNull() || player.b$src$Z$fqlxe4()) {
            return false;
        }
        if (self instanceof AutoLadder) {
            return this.isCoveredByCached(BlockIn.class, player);
        }
        if (self instanceof MLG) {
            return this.isCoveredByCached(BlockIn.class, player)
                    || this.isCoveredByCached(AutoLadder.class, player);
        }
        return false;
    }

    private boolean isCoveredByCached(Class<? extends HackModule> moduleClass, EntityPlayerSP player) {
        long stateKey = this.computeStateKey(player);
        if (stateKey != this.lastStateKey) {
            this.lastStateKey = stateKey;
            this.lastClutchCovered = null;
            this.lastAutoLadderCovered = null;
        }
        if (moduleClass == BlockIn.class) {
            if (this.lastClutchCovered == null) {
                this.lastClutchCovered = this.isCoveredBy(BlockIn.class, player);
            }
            return this.lastClutchCovered.booleanValue();
        }
        if (moduleClass == AutoLadder.class) {
            if (this.lastAutoLadderCovered == null) {
                this.lastAutoLadderCovered = this.isCoveredBy(AutoLadder.class, player);
            }
            return this.lastAutoLadderCovered.booleanValue();
        }
        return false;
    }

    private long computeStateKey(EntityPlayerSP player) {
        long hash = 1L;
        hash = hash * 31L + Double.doubleToLongBits(player.z());
        hash = hash * 31L + Double.doubleToLongBits(player.N());
        hash = hash * 31L + Double.doubleToLongBits(player.h());
        hash = hash * 31L + Double.doubleToLongBits(player.t());
        hash = hash * 31L + Double.doubleToLongBits(player.q());
        hash = hash * 31L + Double.doubleToLongBits(player.T());
        hash = hash * 31L + Float.floatToIntBits(player.M$src$F$ff28gb());
        return hash;
    }

    private boolean isCoveredBy(Class<? extends HackModule> moduleClass, EntityPlayerSP player) {
        HackModule module = Umbra.INSTANCE.getHackManager().getMod(moduleClass);
        if (module == null || !module.isEnabled()) {
            return false;
        }
        if (module instanceof BlockIn) {
            BlockIn clutch = (BlockIn) module;
            return clutch.isRescueEngaged() || clutch.canHandleFall(player);
        }
        if (module instanceof AutoLadder) {
            AutoLadder autoLadder = (AutoLadder) module;
            return autoLadder.isRescueEngaged() || autoLadder.canHandleFall(player);
        }
        return false;
    }
}
