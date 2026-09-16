package gg.umbra.ui.click.component;

import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.component.DropdownSelectComponent;
import gg.umbra.ui.click.frame.PopupFrame;
import java.awt.Point;

public class DropdownPopupCloseClickHandler
implements GuiMouseListener {
    final DropdownSelectComponent<?> owner;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        PopupFrame popupFrame = this.owner.getPopupFrame();
        if (popupFrame != null && !this.owner.t() && !popupFrame.t()) {
            this.owner.togglePopup();
        }
    }


    public DropdownPopupCloseClickHandler(DropdownSelectComponent<?> dropdown) {
        this.owner = dropdown;
    }
}

