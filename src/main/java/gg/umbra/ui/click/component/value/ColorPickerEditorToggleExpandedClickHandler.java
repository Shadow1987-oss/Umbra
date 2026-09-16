package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.ColorPickerEditorComponent;

public class ColorPickerEditorToggleExpandedClickHandler
implements GuiClickListener {
    final ColorPickerEditorComponent editor;

    public ColorPickerEditorToggleExpandedClickHandler(ColorPickerEditorComponent colorValueEditorComponent) {
        this.editor = colorValueEditorComponent;
    }

    @Override
    public void onPrimaryClick() {
        ColorPickerEditorComponent.setCollapsedCompat(this.editor, !ColorPickerEditorComponent.isCollapsedCompat(this.editor));
        this.editor.getParentFrameComponent().l$src$V$1mibm4x();
    }

}

