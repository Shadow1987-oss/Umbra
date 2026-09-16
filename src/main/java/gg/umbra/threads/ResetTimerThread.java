package gg.umbra.threads;

import gg.umbra.hacks.exploits.Step;
import gg.umbra.wrapper.impl.Minecraft;

public class ResetTimerThread
extends Thread {
    final Step step;

    public ResetTimerThread(Step step) {
        this.step = step;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        Minecraft.a_jo_2_T().setTimerSpeed(1.0f);
    }
}

