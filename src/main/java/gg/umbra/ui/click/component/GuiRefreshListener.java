package gg.umbra.ui.click.component;

public interface GuiRefreshListener {
    default public void onRefresh() {
    }

    default public void onIndexedRefresh(String text, int index) {
    }
}
