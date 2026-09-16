package gg.umbra.hacks.pvp.aimassist;

import gg.umbra.Umbra;

public class AimAssistRotationWorkerThread
extends Thread {
    private final AimAssistRotationSubModule rotationModule;

    public AimAssistRotationWorkerThread(AimAssistRotationSubModule rotationModule) {
        this.rotationModule = rotationModule;
    }

    @Override
    public void run() {
        while (!Umbra.INSTANCE.isEnabled()) {
            try {
                Thread.sleep(1L);
                if (!this.rotationModule.isEnabled() || !this.rotationModule.isSelectedSubModule()) continue;
                AimAssistRotationSubModule.runWorkerTick(this.rotationModule);
            }
            catch (Exception ignored) {}
        }
    }
}

