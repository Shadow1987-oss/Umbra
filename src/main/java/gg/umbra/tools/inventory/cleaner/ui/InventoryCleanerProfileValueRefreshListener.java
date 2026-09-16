package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.ProfileChangeEvent;
import gg.umbra.settings.ClientSettings;
import gg.umbra.tools.inventory.cleaner.ui.InventoryCleanerProfileValueComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.module.ModuleComponent;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrame;

public class InventoryCleanerProfileValueRefreshListener
implements EventListener {
    @Listen
    public void H(ProfileChangeEvent profileChangeEvent) {
        for (Frame frame : ClientSettings.mainStack.Y()) {
            if (!(frame instanceof ModuleCategoryFrame)) continue;
            ModuleCategoryFrame moduleCategoryFrame = (ModuleCategoryFrame)frame;
            for (GuiComponent guiComponent : moduleCategoryFrame.f()) {
                if (!(guiComponent instanceof ModuleComponent)) continue;
                ModuleComponent moduleComponent = (ModuleComponent)guiComponent;
                for (GuiComponent guiComponent2 : moduleComponent.getValueComponents()) {
                    if (!(guiComponent2 instanceof InventoryCleanerProfileValueComponent)) continue;
                    InventoryCleanerProfileValueComponent inventoryCleanerProfileValueComponent = (InventoryCleanerProfileValueComponent)guiComponent2;
                    inventoryCleanerProfileValueComponent.populate();
                }
            }
        }
        ClientSettings.getFrame(ClientSettingsSearchFrame.class).resetSearchAsync();
    }

}

