package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.Umbra;
import gg.umbra.input.BindCaptureTask;
import gg.umbra.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.umbra.unmap.Bendable;

class ClickGuiMacrosSecondaryBindCaptureTask
extends BindCaptureTask {
    final ClickGuiMacrosSettingsPanel settingsPanel;

    ClickGuiMacrosSecondaryBindCaptureTask(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, Bendable bendable) {
        super(bendable);
        this.settingsPanel = clickGuiMacrosSettingsPanel;
    }


    private void completeCapture() {
        this.settingsPanel.setBindCaptureTask(null);
        if (this.settingsPanel.getMacro().hasValidBinding()) {
            this.settingsPanel.showFullSettings();
        } else {
            this.settingsPanel.updateViewVisibility();
        }
        Umbra.INSTANCE.saveAndStop();
    }

    @Override
    public void onCaptureComplete() {
        this.completeCapture();
    }
}
