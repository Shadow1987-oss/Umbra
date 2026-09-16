package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.value.ColorPaletteSliderComponent;
import gg.umbra.ui.click.component.value.ColorPickerDropdownHueSliderComponent;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.OptionSetting;
import java.awt.Color;

public class ColorPickerDropdownComponent
extends GuiComponent {
    private final ColorPaletteSliderComponent colorSlider;
    private final OptionSetting modeValue;
    private ModeSelection previousSelection;
    private final ColorPicker teamColorPicker;

    @Override
    public double C() {
        return 25.0;
    }

    @Override
    public void F() {
    }

    public ColorPickerDropdownComponent(OptionSetting modeValue) {
        this.modeValue = modeValue;
        this.teamColorPicker = ColorPicker.create(null, "Team Color", new Color(189, 0, 1));
        this.bindValue(modeValue);
        Color[] palette = new Color[]{new Color(189, 0, 1), new Color(253, 63, 63), new Color(215, 162, 50), new Color(254, 254, 62), new Color(0, 191, 4), new Color(64, 253, 62), new Color(65, 255, 254), new Color(0, 190, 189), new Color(1, 1, 187), new Color(61, 64, 255), new Color(254, 63, 255), new Color(190, 0, 190), new Color(255, 255, 255), new Color(190, 190, 190), new Color(63, 63, 63), new Color(17, 17, 17)};
        this.colorSlider = new ColorPickerDropdownHueSliderComponent(this, "Team color", this.teamColorPicker, palette);
        this.colorSlider.setDisabledOverlayColor(this.getDisabledOverlayColor());
        this.colorSlider.setSeparatedSegments(true);
        this.colorSlider.setToolTips(null);
        this.addChildren(this.colorSlider);
    }

    @Override
    public void I() {
    }

    @Override
    public void H() {
        this.onDisable();
        this.colorSlider.K(this.G$src$D$1b2f02a());
        this.colorSlider.S(this.n());
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.7);
        String selectionName = ((ModeSelection)this.modeValue.getValue()).getName().substring(2);
        smoothFontRenderer.d(selectionName, this.G$src$D$1b2f02a() + this.A() - 5.0 - smoothFontRenderer.N(selectionName), this.n() + 5.0, ColorPickerDropdownComponent.J.Z);
    }

    @Override
    public double x() {
        return 110.0;
    }

    private void synchronizeSliderSelection() {
        if (this.previousSelection == null) {
            this.previousSelection = (ModeSelection)this.modeValue.getValue();
            return;
        }
        ModeSelection currentSelection = (ModeSelection)this.modeValue.getValue();
        if (!this.previousSelection.equals(currentSelection)) {
            this.colorSlider.selectPaletteIndex(this.modeValue.getSelectedIndex());
        }
    }

    @Override
    public void u() {
        this.modeValue.setPersistenceSuppressed(true);
        this.colorSlider.getColorPicker().setPersistenceSuppressed(true);
        this.teamColorPicker.setPersistenceSuppressed(true);
        this.synchronizeSliderSelection();
        if (this.modeValue.getSelectedIndex() != this.colorSlider.getSelectedIndex()) {
            this.modeValue.setSelectedIndex(this.colorSlider.getSelectedIndex());
        }
        this.previousSelection = (ModeSelection)this.modeValue.getValue();
        this.teamColorPicker.setPersistenceSuppressed(false);
        this.colorSlider.getColorPicker().setPersistenceSuppressed(false);
        this.modeValue.setPersistenceSuppressed(false);
    }


    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }
}

