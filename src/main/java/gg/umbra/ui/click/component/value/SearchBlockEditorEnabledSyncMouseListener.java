package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.component.value.SearchBlockEditorComponent;
import gg.umbra.ui.unmap.SearchBlock;
import java.awt.Point;

public class SearchBlockEditorEnabledSyncMouseListener
implements GuiMouseListener {
    final SearchBlock searchBlock;
    final SearchBlockEditorComponent editor;

    public SearchBlockEditorEnabledSyncMouseListener(SearchBlockEditorComponent searchBlockEditorComponent, SearchBlock searchBlock) {
        this.editor = searchBlockEditorComponent;
        this.searchBlock = searchBlock;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.searchBlock.M(SearchBlockEditorComponent.getEnabledValueCompat(this.editor).getEffectiveValueCompat());
    }
}
