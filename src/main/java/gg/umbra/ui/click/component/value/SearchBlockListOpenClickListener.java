package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.SearchBlockListComponent;

public class SearchBlockListOpenClickListener
implements GuiClickListener {
    final SearchBlockListComponent component;

    @Override
    public void onPrimaryClick() {
        SearchBlockListComponent.openEditorCompat(this.component);
    }

    public SearchBlockListOpenClickListener(SearchBlockListComponent component) {
        this.component = component;
    }
}
