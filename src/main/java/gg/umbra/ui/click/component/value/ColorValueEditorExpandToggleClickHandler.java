package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.ClientSettingsColorPickerEditorComponent;

class ColorPickerEditorExpandToggleClickHandler
implements GuiClickListener {
    final ClientSettingsColorPickerEditorComponent editor;

    @Override
    public void onPrimaryClick() {
        ClientSettingsColorPickerEditorComponent.setCollapsedCompat(this.editor, !ClientSettingsColorPickerEditorComponent.isCollapsedCompat(this.editor));
        this.editor.getParentFrameComponent().l$src$V$1mibm4x();
    }

    ColorPickerEditorExpandToggleClickHandler(ClientSettingsColorPickerEditorComponent clientSettingsColorPickerEditorComponent) {
        this.editor = clientSettingsColorPickerEditorComponent;
    }

}

