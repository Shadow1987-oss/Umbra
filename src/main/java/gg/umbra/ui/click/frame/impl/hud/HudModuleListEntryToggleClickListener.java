package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.settings.ClientSettings;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.GuiComponentContract;
import gg.umbra.ui.click.component.input.BindValueRowComponent;
import gg.umbra.ui.click.component.value.ValueComponentFactory;
import gg.umbra.value.Value;

public class HudModuleListEntryToggleClickListener
implements GuiClickListener {
    private static final String KEYBIND_LABEL = "Keybind";
    private final HudModule module;
    private final HudModuleListEntry listEntry;

    @Override
    public void onPrimaryClick() {
        HudModuleConfigFrame hudModuleConfigFrame = ClientSettings.getFrame(HudModuleConfigFrame.class);
        if (hudModuleConfigFrame == null) {
            return;
        }
        hudModuleConfigFrame.setSelectedModule(this.module);
        hudModuleConfigFrame.removeMarkedChildren();
        for (Value<?, ?> value : this.module.getValues()) {
            GuiComponent guiComponent = ValueComponentFactory.createMainValueComponent(value);
            if (guiComponent == null) continue;
            if (value.getParent() != null) {
                guiComponent.setDisabledOverlayColor(GuiComponentContract.J.r);
            } else {
                guiComponent.setDisabledOverlayColor(GuiComponentContract.J.i);
            }
            hudModuleConfigFrame.h(guiComponent, new Object[0]);
        }
        if (this.module.shouldShowKeybindSetting()) {
            hudModuleConfigFrame.h(new BindValueRowComponent(KEYBIND_LABEL, this.module.getBind()), new Object[0]);
        }
        this.listEntry.getSettingsButton().setVisible(true);
        hudModuleConfigFrame.setVisible(true);
        hudModuleConfigFrame.U();
        hudModuleConfigFrame.t(hudModuleConfigFrame.L());
        hudModuleConfigFrame.beginOpening();
        hudModuleConfigFrame.l$src$V$1mibm4x();
    }


    public HudModuleListEntryToggleClickListener(HudModuleListEntry hudModuleListEntry, HudModule hudModule) {
        this.listEntry = hudModuleListEntry;
        this.module = hudModule;
    }
}

