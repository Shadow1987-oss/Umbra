package gg.umbra.settings;

import com.google.common.collect.ImmutableList;
import gg.umbra.Umbra;
import gg.umbra.input.BindSet;
import gg.umbra.module.HackModule;
import gg.umbra.unmap.ModBendable;
import java.util.Collections;
import java.util.List;

public class ClientSettingsGuiBindBendable
extends ModBendable {

    public ClientSettingsGuiBindBendable(HackModule mod) {
        super(mod);
    }

    @Override
    public boolean usesOwnKeybindStorage() {
        return false;
    }

    @Override
    public boolean supportsActivationMode() {
        return false;
    }

    @Override
    public List<Integer> getBoundInputs() {
        return ImmutableList.copyOf(((BindSet)Umbra.INSTANCE.getPublicProfileSettings().guiBind.getValue()).getBoundInputs());
    }

    @Override
    public void setBoundInputs(List<Integer> inputCodes) {
        ((BindSet)Umbra.INSTANCE.getPublicProfileSettings().guiBind.getValue()).setBoundInputs(inputCodes);
        if (!this.hasValidBinding()) {
            this.setBoundInputs(Collections.singletonList(161));
        }
    }
}

