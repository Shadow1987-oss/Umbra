package gg.umbra.ui.click.component.input;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.input.BindableInputComponent;

class BindableInputComponentCaptureToggleClickHandler
implements GuiClickListener {
    final BindableInputComponent owner;

    BindableInputComponentCaptureToggleClickHandler(BindableInputComponent bindableInputComponent) {
        this.owner = bindableInputComponent;
    }

    @Override
    public void onPrimaryClick() {
        if (this.owner.getCaptureTask().isCapturing()) {
            this.owner.getBindLabel().setToolTips(null);
            this.owner.w(this.owner.buildTooltipText());
            return;
        }
        if (this.owner.supportsActivationModeConfiguration() && this.owner.isShiftPressed()) {
            this.owner.getBendable().toggleActivationMode();
            Umbra.INSTANCE.saveAndStop();
            return;
        }
        this.owner.getCaptureTask().run();
    }

}

