package gg.umbra.ui.click.component.value;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.value.FloatingValueDropdownLayer;
import gg.umbra.ui.click.component.value.SearchBlockEditorComponent;
import gg.umbra.ui.click.component.value.SearchBlockListAddInputComponent;
import gg.umbra.ui.click.component.value.SearchBlockListComponent;
import gg.umbra.ui.click.component.value.SearchBlockListDropdownCloseHandler;
import gg.umbra.ui.click.component.value.SearchBlockRemoveHandler;
import gg.umbra.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;
import gg.umbra.ui.unmap.SearchBlock;

public class SearchBlockListDropdownLayer
extends FloatingValueDropdownLayer<SearchBlockListComponent> {
    public SearchBlockListDropdownLayer(SearchBlockListComponent searchBlockListComponent) {
        super(searchBlockListComponent);
        this.Y(new PublicProfilesFrameHeaderActionComponent(this, "allowedicon", searchBlockListComponent.getTitle()).Q(new SearchBlockListDropdownCloseHandler(this, searchBlockListComponent)));
        this.refreshContents();
    }

    @Override
    public void refreshContents() {
        this.removeMarkedChildren();
        SearchBlockListAddInputComponent searchBlockListAddInputComponent = new SearchBlockListAddInputComponent("Block name / ID");
        this.h(searchBlockListAddInputComponent, new Object[0]);
        for (SearchBlock searchBlock : Umbra.INSTANCE.getSearch().getSearchBlocks()) {
            SearchBlockEditorComponent searchBlockEditorComponent = new SearchBlockEditorComponent(searchBlock);
            searchBlockEditorComponent.setRemoveClickListener(new SearchBlockRemoveHandler(this, searchBlock));
            this.h(searchBlockEditorComponent, new Object[0]);
        }
    }
}
