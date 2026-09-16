package gg.umbra.visual.freecam;

import gg.umbra.Umbra;
import gg.umbra.event.EventListener;
import gg.umbra.module.HackModule;

public abstract class FreecamController<T extends HackModule>
implements EventListener {
    protected static final Umbra UMBRA = Umbra.INSTANCE;
    protected final T module;

    public FreecamController(T module) {
        this.module = module;
    }

    public T getModule() {
        return this.module;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}

