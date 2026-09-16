package gg.umbra.ui.click.component;

import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.IconTextActionRowComponent;
import java.awt.Point;

class IconTextActionRowForwardClickMouseListener
implements GuiMouseListener {
    final IconTextActionRowComponent row;
    final GuiClickListener clickListener;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.clickListener.onPrimaryClick();
    }

    IconTextActionRowForwardClickMouseListener(IconTextActionRowComponent row, GuiClickListener clickListener) {
        this.row = row;
        this.clickListener = clickListener;
    }
}
