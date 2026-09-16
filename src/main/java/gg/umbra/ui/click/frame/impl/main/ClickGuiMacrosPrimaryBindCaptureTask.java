package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.Umbra;
import gg.umbra.input.BindCaptureTask;
import gg.umbra.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.umbra.unmap.Bendable;

class ClickGuiMacrosPrimaryBindCaptureTask
extends BindCaptureTask {
    final ClickGuiMacrosSettingsPanel settingsPanel;

    private void completeCapture() {
        this.settingsPanel.setBindCaptureTask(null);
        Umbra.INSTANCE.saveAndStop();
    }

    @Override
    public void onCaptureComplete() {
        this.completeCapture();
    }

    ClickGuiMacrosPrimaryBindCaptureTask(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, Bendable bendable) {
        super(bendable);
        this.settingsPanel = clickGuiMacrosSettingsPanel;
    }
}
