package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.settings.ClientSettings;
import gg.umbra.tools.inventory.cleaner.ui.MaterialFilterSelectionList;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class MaterialFilterSelectionListClosePopupMouseListener
implements GuiMouseListener {
    final AnchoredPopupFrame popup;

    MaterialFilterSelectionListClosePopupMouseListener(MaterialFilterSelectionList materialFilterSelectionList, AnchoredPopupFrame anchoredPopupFrame) {
        this.popup = anchoredPopupFrame;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!this.popup.w$src$Z$e457mb() && !this.popup.D$src$Lgg_umbra_ui_click_component_GuiComponent_$srx612().w$src$Z$e457mb()) {
            ClientSettings.removePopup(this.popup);
        }
    }

}

