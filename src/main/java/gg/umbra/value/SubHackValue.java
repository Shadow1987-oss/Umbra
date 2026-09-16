package gg.umbra.value;

import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.unmap.ModeOption;

public class SubHackValue<T extends SubHack>
extends ModeOption {
    private final T instance;

    public SubHackValue(String name, T instance) {
        super(name);
        this.instance = instance;
    }

    public SubHackValue(T instance) {
        this(((HackModule)instance).getName(), instance);
    }

    public T getInstance() {
        return this.instance;
    }
}
