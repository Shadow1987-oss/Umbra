package gg.umbra.module;

import gg.umbra.event.IEvent;
import gg.umbra.value.SubHackValue;
import java.util.function.Predicate;

public class SubHack<T extends HackModule>
extends HackModule {
    private static boolean opaqueState;
    private final SubHackValue selectionValue;
    private final HackModule parentModule;
    private boolean enabled;

    public boolean isSubModuleEnabled() {
        return this.enabled;
    }

    @Override
    protected Predicate<IEvent> w() {
        return this::lambda$getEventPredicate$0;
    }

    static {
        if (!SubHack.y$src$Z$h2l5ue()) {
            SubHack.W(true);
        }
    }

    public SubHack(HackModule mod, String string) {
        this(mod, string, true);
    }

    private boolean lambda$getEventPredicate$0(IEvent iEvent) {
        return this.isEnabled() && this.isSelectedSubModule();
    }

    public static boolean y$src$Z$h2l5ue() {
        return opaqueState;
    }


    public T getParent() {
        return (T)this.parentModule;
    }

    public SubHack(HackModule mod, String string, boolean enabled) {
        super(string);
        this.parentModule = mod;
        this.enabled = enabled;
        this.selectionValue = new SubHackValue<SubHack>(this);
    }

    public static boolean g$src$Z$gsov5w() {
        boolean bl = SubHack.y$src$Z$h2l5ue();
        return false;
    }

    public SubHackValue getSelectionValue() {
        return this.selectionValue;
    }

    public boolean isSelectedSubModule() {
        return this.selectionValue.isSelected();
    }

    public static void W(boolean bl) {
        opaqueState = bl;
    }

    public boolean isParentEnabled() {
        return super.isEnabled();
    }
}

