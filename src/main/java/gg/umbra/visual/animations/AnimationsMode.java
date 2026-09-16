package gg.umbra.visual.animations;

import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.visual.Animations;

public abstract class AnimationsMode
extends SubHack<Animations> {
    public boolean shouldBlock() {
        return false;
    }

    public abstract boolean isBlocking();

    public AnimationsMode(HackModule parent, String name) {
        super(parent, name);
    }
}
