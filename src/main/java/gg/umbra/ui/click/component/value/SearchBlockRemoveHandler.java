package gg.umbra.ui.click.component.value;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.SearchBlockListDropdownLayer;
import gg.umbra.ui.unmap.SearchBlock;

class SearchBlockRemoveHandler
implements GuiClickListener {
    final SearchBlock searchBlock;
    final SearchBlockListDropdownLayer dropdownLayer;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getSearch().removeSearchBlock(this.searchBlock);
        this.dropdownLayer.refreshContents();
    }

    SearchBlockRemoveHandler(SearchBlockListDropdownLayer searchBlockListDropdownLayer, SearchBlock searchBlock) {
        this.dropdownLayer = searchBlockListDropdownLayer;
        this.searchBlock = searchBlock;
    }
}
