package gg.umbra.movement;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPreLocalPlayerTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.wrapper.impl.Minecraft;

public class PlayerMovementTaskManager
implements EventListener {
    private static String controlFlowMarker;
    public static PlayerMovementTaskManager INSTANCE;
    private PlayerMovementTask activeTask;

    private static RuntimeException propagateRuntimeException(RuntimeException exception) {
        return exception;
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public void cancel(PlayerMovementTask task) {
        if (this.activeTask != null && this.activeTask.equals(task)) {
            this.activeTask.setCompleted(true);
        }
    }

    private PlayerMovementTaskManager() {
        INSTANCE = this;
    }

    public boolean e$src$Z$17ayaq9() {
        return this.activeTask != null && !this.activeTask.isCompleted();
    }

    @Listen(priority=EventPriority.HIGH)
    public void onPreLocalPlayerTick(EventPreLocalPlayerTick event) {
        if (this.activeTask == null && Minecraft.thePlayer().isNotNull()) {
            return;
        }
        this.activeTask.updateCompletion();
        if (this.activeTask.isCompleted()) {
            if (this.activeTask.shouldRestoreInputOnCompletion()) {
                MovementInputHelper.restorePhysicalInput();
            } else {
                MovementInputHelper.releaseMovementKeys();
            }
            this.activeTask = null;
        }
    }

    public PlayerMovementTask getActiveTask() {
        return this.activeTask;
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    @Listen
    public void onTick(EventPreTick eventPreTick) {
        if (this.activeTask != null && Minecraft.thePlayer().isNotNull()) {
            try {
                this.activeTask.applyMovementInput(eventPreTick);
            }
            catch (NullPointerException nullPointerException) {
                Umbra.logThrowable(nullPointerException);
            }
        }
    }

    public void submit(PlayerMovementTask task) {
        this.activeTask = task;
    }

    static {
        INSTANCE = new PlayerMovementTaskManager();
        PlayerMovementTaskManager.setControlFlowMarker(null);
    }
}
