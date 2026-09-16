package gg.umbra.feature;

/**
 * Stable contract for anything the client can toggle and persist.
 * Display names change between versions; ids must not. Configs and
 * profiles always key by {@link #getId()}, never by {@link #getName()}.
 */
public interface Module {
    /** Stable identity used for configs and profiles. Never a display name. */
    String getId();

    /** Display name shown in the GUI. May change between versions. */
    String getName();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    default void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
