package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.component.value.ColorPaletteSliderComponent;
import gg.umbra.ui.click.component.value.ColorPickerDropdownComponent;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.value.ColorPicker;
import java.awt.Color;

public class ColorPickerDropdownHueSliderComponent
extends ColorPaletteSliderComponent {
    final ColorPickerDropdownComponent dropdownComponent;
    private static final String HANDLE_ICON = "teamdot";

    @Override
    protected void renderHandle() {
        GuiRenderPrimitives.F(HANDLE_ICON, this.G$src$D$1b2f02a() + (double)this.getHandlePositionAnimation().getInterpolatedValue().floatValue(), this.handleBounds.W() + this.handleBounds.R() / 2.0, this.handleBounds.e(), this.handleBounds.R(), this.getHandleColorAnimation().getInterpolatedColor());
    }

    @Override
    public boolean isCustomColor() {
        return false;
    }

    public ColorPickerDropdownHueSliderComponent(ColorPickerDropdownComponent dropdownComponent, String label, ColorPicker colorValue, Color[] paletteColors) {
        super(label, colorValue, paletteColors);
        this.dropdownComponent = dropdownComponent;
    }
}
