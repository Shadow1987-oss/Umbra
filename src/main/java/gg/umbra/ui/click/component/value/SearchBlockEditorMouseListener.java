package gg.umbra.ui.click.component.value;

import gg.umbra.Umbra;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.component.value.SearchBlockEditorComponent;
import gg.umbra.ui.unmap.SearchBlock;
import java.awt.Point;

public class SearchBlockEditorMouseListener
implements GuiMouseListener {
    final SearchBlockEditorComponent editor;
    final SearchBlock searchBlock;
    private static final String DEBUG_MESSAGE = "Clicked tracers";

    public SearchBlockEditorMouseListener(SearchBlockEditorComponent searchBlockEditorComponent, SearchBlock searchBlock) {
        this.editor = searchBlockEditorComponent;
        this.searchBlock = searchBlock;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.searchBlock.H(SearchBlockEditorComponent.getTracersValueCompat(this.editor).getEffectiveValue());
        Umbra.debugLog(DEBUG_MESSAGE);
    }
}
