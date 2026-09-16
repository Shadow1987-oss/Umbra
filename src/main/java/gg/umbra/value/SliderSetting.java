package gg.umbra.value;

import gg.umbra.Umbra;
import gg.umbra.utils.MathUtil;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.Value;
import java.text.DecimalFormat;

public class SliderSetting
extends Value<Double, SliderSetting> {
    private static final String DEFAULT_FORMAT_PATTERN;
    private final String unitSuffix;
    private static int[] legacyState;
    private final DecimalFormat displayFormat;
    private double increment = 0.01;
    private final String formatPattern;
    private final double minimum;
    private final double maximum;
    private final DecimalFormat inputFormat;
    private double absoluteClampLimit = 999999.0;


    public String getUnitSuffix() {
        return this.unitSuffix;
    }

    public SliderSetting setMaximumFractionDigits(int digits) {
        this.displayFormat.setMaximumFractionDigits(digits);
        return this;
    }

    public static SliderSetting create(Object owner, String name, String legacyLabel, String formatPattern, String unitSuffix, double minimum, double defaultValue, double maximum) {
        return new SliderSetting(owner, name, defaultValue, minimum, maximum, formatPattern, unitSuffix);
    }

    public static SliderSetting create(Object owner, String name, String formatPattern, String unitSuffix, double minimum, double defaultValue, double maximum, double increment) {
        SliderSetting numberValue = new SliderSetting(owner, name, defaultValue, minimum, maximum, formatPattern, unitSuffix);
        numberValue.increment = increment;
        return numberValue;
    }

    public DecimalFormat getInputFormat() {
        return this.inputFormat;
    }

    public double getMinimum() {
        return this.minimum;
    }

    @Override
    public void parse(String serializedValue) {
        if (serializedValue.isEmpty()) {
            return;
        }
        this.setRawValue(Double.parseDouble(serializedValue));
    }

    public static SliderSetting create(Object owner, String name, String formatPattern, String unitSuffix, double minimum, double defaultValue, double maximum, double increment, String description) {
        SliderSetting numberValue = new SliderSetting(owner, name, defaultValue, minimum, maximum, formatPattern, unitSuffix);
        numberValue.setDescription(description);
        numberValue.increment = increment;
        return numberValue;
    }

    public static SliderSetting create(Object owner, String name, String formatPattern, String unitSuffix, double minimum, double defaultValue, double maximum) {
        return new SliderSetting(owner, name, defaultValue, minimum, maximum, formatPattern, unitSuffix);
    }

    @Override
    public String getDisplayValue() {
        String displayValue = String.valueOf(this.getValue());
        displayValue = this.displayFormat.format(this.getValue());
        return displayValue;
    }

    @Override
    public void setValue(Double value) {
        if (value == null) {
            value = this.getDefaultValue();
            if (value == null) {
                value = super.getValue();
            }
            if (value == null) {
                value = this.minimum;
            }
        }
        if (value > this.absoluteClampLimit) {
            value = this.absoluteClampLimit;
        } else if (value < -this.absoluteClampLimit) {
            value = -this.absoluteClampLimit;
        }
        super.setValue(MathUtil.roundToIncrement(value, this.increment));
        this.notifyChanged();
        if (this.getOwner() instanceof ColorPicker) {
            ((ColorPicker)this.getOwner()).syncCompositeValue();
        }
    }

    public static SliderSetting createWithDescription(Object owner, String name, String formatPattern, String unitSuffix, double minimum, double defaultValue, double maximum, String description) {
        return (SliderSetting)new SliderSetting(owner, name, defaultValue, minimum, maximum, formatPattern, unitSuffix).setDescription(description);
    }

    public void setRawValue(Double value) {
        super.setValue(value);
        this.notifyChanged();
    }

    public double getIncrement() {
        return this.increment;
    }

    @Override
    public Double getValue() {
        Double current = super.getValue();
        // During profile-apply, the backing value can transiently be null;
            // Numeric consumers unbox this value directly, so fall back to the non-null
        // default rather than NPE on unboxing.
        return current != null ? current : this.getDefaultValue();
    }

    public void setRoundedDefaultValue(Double value) {
        double roundedValue = (double)Math.round(value * 100.0) / 100.0;
        super.setDefaultValue(roundedValue);
    }

    public static void setNumberLegacyState(int[] state) {
        legacyState = state;
    }

    public double getMaximum() {
        return this.maximum;
    }

    public void setAbsoluteClampLimit(double limit) {
        this.absoluteClampLimit = limit;
    }

    static {
        SliderSetting.setNumberLegacyState(null);
        DEFAULT_FORMAT_PATTERN = "#.##";
    }

    private DecimalFormat createDecimalFormat() {
        DecimalFormat decimalFormat;
        try {
            decimalFormat = new DecimalFormat(this.formatPattern);
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
            decimalFormat = new DecimalFormat(DEFAULT_FORMAT_PATTERN);
        }
        decimalFormat.setMinimumIntegerDigits(1);
        return decimalFormat;
    }

    public static int[] getNumberLegacyState() {
        return legacyState;
    }

    public SliderSetting copyDefinition() {
        return new SliderSetting(null, this.getName(), (Double)this.getValue(), this.minimum, this.maximum, this.formatPattern, this.unitSuffix);
    }

    @Override
    public SliderSetting copyValueDefinition() {
        return this.copyDefinition();
    }

    public SliderSetting(Object owner, String name, double defaultValue, double minimum, double maximum, String formatPattern, String unitSuffix) {
        super(owner, name, defaultValue);
        this.minimum = minimum;
        this.maximum = maximum;
        if (!unitSuffix.isEmpty()) {
            unitSuffix = " " + unitSuffix;
        }
        this.unitSuffix = unitSuffix;
        this.formatPattern = formatPattern;
        this.inputFormat = this.createDecimalFormat();
        this.displayFormat = this.createDecimalFormat();
    }
}
