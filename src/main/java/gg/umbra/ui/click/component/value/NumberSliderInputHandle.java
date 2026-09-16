package gg.umbra.ui.click.component.value;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.value.NumberSliderComponent;
import gg.umbra.ui.click.component.value.SliderInputHandle;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.Umbra;

public class NumberSliderInputHandle
extends SliderInputHandle {
    private NumberSliderComponent numberSlider;


    @Override
    public void loadCurrentValueForEditing() {
        this.setText(((Double)this.numberSlider.getSliderSetting().getValue()).toString());
    }

    public NumberSliderInputHandle(NumberSliderComponent numberSlider) {
        this.numberSlider = numberSlider;
        this.actionButton.setVisible(false);
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.75);
        String displayText = this.isEditing() ? this.getText() : (this.hovered ? this.numberSlider.getDecimalFormat().format(this.numberSlider.getSliderSetting().getValue()) : this.numberSlider.getDecimalFormat().format(this.numberSlider.getSliderSetting().getValue()) + " " + this.numberSlider.getUnitSuffix());
        if (!this.isEditing() && !this.hovered && this.numberSlider.getUnitSuffix().length() <= 1) {
            displayText = this.numberSlider.getDecimalFormat().format(this.numberSlider.getSliderSetting().getValue());
        }
        fontRenderer.d(displayText, this.G$src$D$1b2f02a() + (this.getAvailableTextWidth() - fontRenderer.N(displayText)), this.n(), NumberSliderInputHandle.J.Z);
        if (this.isFocused()) {
            this.cursorPosition = displayText.length();
            this.renderCaret(fontRenderer, this.G$src$D$1b2f02a() + this.getAvailableTextWidth(), this.n());
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n() + 5.0 + 2.0, this.A(), 1.0, this.getUnderlineColor());
    }

    @Override
    public void submit() {
        try {
            String normalizedText = this.getText().replace(this.numberSlider.getSliderSetting().getInputFormat().getDecimalFormatSymbols().getDecimalSeparator(), '.');
            double value = Double.parseDouble(normalizedText);
            this.numberSlider.getSliderSetting().setRawValue(value);
        }
        catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        ClientSettings.activeComponent = null;
    }
}
