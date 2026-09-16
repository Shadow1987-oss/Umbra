package gg.umbra.ui.click.component.value;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.value.RandomRangeSliderComponent;
import gg.umbra.ui.click.component.value.RangeEndpoint;
import gg.umbra.ui.click.component.value.SliderInputHandle;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.Umbra;

public class RandomRangeSliderInputHandle
extends SliderInputHandle {
    private RangeEndpoint endpoint;
    private RandomRangeSliderComponent rangeSlider;

    private static Exception passthroughException(Exception exception) {
        return exception;
    }

    public RandomRangeSliderInputHandle(RandomRangeSliderComponent rangeSlider, RangeEndpoint endpoint) {
        this.rangeSlider = rangeSlider;
        this.endpoint = endpoint;
        this.actionButton.setVisible(false);
    }

    @Override
    public void H() {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.75);
        String displayText = this.isEditing() ? this.getText() : this.getFormattedEndpointValue();
        fontRenderer.d(displayText, this.G$src$D$1b2f02a() + (this.getAvailableTextWidth() - fontRenderer.N(displayText)), this.n(), RandomRangeSliderInputHandle.J.Z);
        if (this.isFocused()) {
            this.cursorPosition = displayText.length();
            this.renderCaret(fontRenderer, this.G$src$D$1b2f02a() + this.getAvailableTextWidth(), this.n());
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n() + 5.0 + 2.0, this.A(), 1.0, this.getUnderlineColor());
    }

    @Override
    public void submit() {
        try {
            String normalizedText = this.getText().replace(this.rangeSlider.getRandomRangeSetting().getEndpointFormat().getDecimalFormatSymbols().getDecimalSeparator(), '.');
            double value = Double.parseDouble(normalizedText);
            switch (this.endpoint) {
                case MINIMUM: {
                    if (value > this.rangeSlider.getRandomRangeSetting().getMaximumValue()) {
                        this.rangeSlider.getRandomRangeSetting().setMinimumValue(this.rangeSlider.getRandomRangeSetting().getMaximumValue());
                        this.rangeSlider.getRandomRangeSetting().setMaximumValue(value);
                        break;
                    }
                    this.rangeSlider.getRandomRangeSetting().setMinimumValue(value);
                    break;
                }
                case MAXIMUM: {
                    if (value < this.rangeSlider.getRandomRangeSetting().getMinimumValue()) {
                        this.rangeSlider.getRandomRangeSetting().setMaximumValue(this.rangeSlider.getRandomRangeSetting().getMinimumValue());
                        this.rangeSlider.getRandomRangeSetting().setMinimumValue(value);
                        break;
                    }
                    this.rangeSlider.getRandomRangeSetting().setMaximumValue(value);
                }
            }
        }
        catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        this.rangeSlider.getRandomRangeSetting().setRange(new double[]{this.rangeSlider.getRandomRangeSetting().getMinimumValue(), this.rangeSlider.getRandomRangeSetting().getMaximumValue()});
        ClientSettings.activeComponent = null;
    }

    @Override
    public void loadCurrentValueForEditing() {
        this.setText(this.getFormattedEndpointValue());
    }

    public String getFormattedEndpointValue() {
        switch (this.endpoint) {
            case MINIMUM: {
                return this.rangeSlider.getRandomRangeSetting().getFormattedMinimum();
            }
            case MAXIMUM: {
                return this.rangeSlider.getRandomRangeSetting().getFormattedMaximum();
            }
        }
        return null;
    }
}
