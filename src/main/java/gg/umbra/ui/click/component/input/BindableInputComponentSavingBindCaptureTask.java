package gg.umbra.ui.click.component.input;

import gg.umbra.Umbra;
import gg.umbra.input.BindCaptureTask;
import gg.umbra.ui.click.component.input.BindableInputComponent;
import gg.umbra.unmap.Bendable;

public class BindableInputComponentSavingBindCaptureTask
extends BindCaptureTask {
    final BindableInputComponent owner;

    public BindableInputComponentSavingBindCaptureTask(BindableInputComponent bindableInputComponent, Bendable bendable) {
        super(bendable);
        this.owner = bindableInputComponent;
    }

    public void saveSettings() {
        Umbra.INSTANCE.saveAndStop();
    }

    @Override
    public void onCaptureComplete() {
        this.saveSettings();
    }
}
