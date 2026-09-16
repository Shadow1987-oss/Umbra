package gg.umbra.ui.click.component.value;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.SearchBlockListComponent;
import gg.umbra.ui.unmap.SearchBlock;

public class SearchBlockRemoveClickListener
implements GuiClickListener {
    final SearchBlock searchBlock;
    final Runnable afterRemove;
    final SearchBlockListComponent owner;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getSearch().removeSearchBlock(this.searchBlock);
        this.afterRemove.run();
    }

    public SearchBlockRemoveClickListener(SearchBlockListComponent owner, SearchBlock searchBlock, Runnable afterRemove) {
        this.owner = owner;
        this.searchBlock = searchBlock;
        this.afterRemove = afterRemove;
    }
}
