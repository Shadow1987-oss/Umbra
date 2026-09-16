package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.EntityTargetFilterComponent;
import gg.umbra.ui.click.frame.FrameValueDropdownLayer;

public class EntityTargetFilterDropdownCloseHandler
implements GuiClickListener {
    final FrameValueDropdownLayer dropdownLayer;
    final EntityTargetFilterComponent targetFilterComponent;

    public EntityTargetFilterDropdownCloseHandler(FrameValueDropdownLayer dropdownLayer, EntityTargetFilterComponent targetFilterComponent) {
        this.dropdownLayer = dropdownLayer;
        this.targetFilterComponent = targetFilterComponent;
    }

    @Override
    public void onPrimaryClick() {
        this.targetFilterComponent.setExpanded(false);
    }
}
