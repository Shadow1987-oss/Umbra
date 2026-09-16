package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.tools.inventory.cleaner.ui.MaterialFilterSelectionList;
import gg.umbra.tools.inventory.cleaner.ui.MaterialFilterSelectionRow;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import java.awt.Point;

class MaterialFilterSelectionRemoveClickHandler
implements GuiMouseListener {
    final MaterialFilterSelectionList list;
    final MaterialFilterSelectionRow row;

    MaterialFilterSelectionRemoveClickHandler(MaterialFilterSelectionList materialFilterSelectionList, MaterialFilterSelectionRow materialFilterSelectionRow) {
        this.list = materialFilterSelectionList;
        this.row = materialFilterSelectionRow;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.list.removeSelectionRow(this.row);
    }
}
