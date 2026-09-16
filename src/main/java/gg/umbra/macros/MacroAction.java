package gg.umbra.macros;

public interface MacroAction {
    boolean isFinished();

    default void cancel() {
    }

    default void tick() {
    }

    default void inheritState(MacroAction previousAction) {
    }
}
