package gg.umbra.module;

import gg.umbra.Umbra;

public class DelayedModuleToggleTask
implements Runnable {
    private final boolean repeatUntilDisabled;
    private final HackModule module;
    private final long delayMillis;
    private boolean running = true;

    public DelayedModuleToggleTask(HackModule module, long delayMillis, boolean repeatUntilDisabled) {
        this.module = module;
        this.delayMillis = delayMillis;
        this.repeatUntilDisabled = repeatUntilDisabled;
    }

    public boolean isRunning() {
        return this.running;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(this.delayMillis);
                if (this.module.isEnabled()) {
                    this.module.onScheduledAction();
                }
            }
            catch (Exception ignored) {
            }
        } while (!Umbra.INSTANCE.isEnabled() && this.repeatUntilDisabled && this.running);
    }
}
