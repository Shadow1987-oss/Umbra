package gg.umbra.value;

import gg.umbra.ui.click.frame.impl.main.ClickGuiModuleCardRenderState;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.EntityTargetFilterValue;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SubHackValue;
import gg.umbra.value.Value;
import gg.umbra.value.ValueDisplayDescriptor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public final class ModuleValueDisplayFormatter {
    public static List<ClickGuiModuleCardRenderState> buildDescriptorRenderStates(List<ValueDisplayDescriptor> descriptors) {
        List<ValueDisplayDescriptor> displayableDescriptors = ModuleValueDisplayFormatter.filterDisplayableDescriptors(descriptors);
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneDescriptorMode(displayableDescriptors);
        OptionSetting subModuleMode = ModuleValueDisplayFormatter.findUniqueDescriptorSubModuleMode(displayableDescriptors);
        List<ClickGuiModuleCardRenderState> renderStates = ModuleValueDisplayFormatter.buildDescriptorRenderStatesInternal(displayableDescriptors, hasExactlyOneMode, subModuleMode, false);
        int textLength = ModuleValueDisplayFormatter.getTextLength(renderStates);
        if (textLength < 50) {
            return ModuleValueDisplayFormatter.buildDescriptorRenderStatesInternal(displayableDescriptors, hasExactlyOneMode, subModuleMode, true);
        }
        return renderStates;
    }

    private static String formatValue(Value<?, ?> value, boolean hasExactlyOneMode, boolean isPrimaryMode) {
        if (value instanceof ToggleSetting) {
            ToggleSetting booleanValue = (ToggleSetting)value;
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents(booleanValue)) {
                return ModuleValueDisplayFormatter.formatLimitDependents(booleanValue);
            }
            return booleanValue.getDisplayName();
        }
        String displayValue = value.getDisplayValue();
        if ((hasExactlyOneMode || isPrimaryMode) && value instanceof OptionSetting) {
            return displayValue;
        }
        if (displayValue.isEmpty()) {
            return value.getName();
        }
        return displayValue + " " + value.getName();
    }

    private ModuleValueDisplayFormatter() {
    }

    private static List<Value<?, ?>> filterDisplayableValues(List<Value<?, ?>> values) {
        HashSet<Value> dependentValues = new HashSet<Value>();
        HashSet<Object> colorOnlyToggles = new HashSet<Object>();
        for (Value<?, ?> value : values) {
            if (!(value instanceof ToggleSetting)) continue;
            ToggleSetting booleanValue = (ToggleSetting)value;
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents(booleanValue)) {
                for (Value dependentValue : booleanValue.getDependentValues()) {
                    dependentValues.add(dependentValue);
                }
                continue;
            }
            if (!ModuleValueDisplayFormatter.hasOnlyColorDependents(booleanValue)) continue;
            colorOnlyToggles.add(value);
        }
        ArrayList<Value<?, ?>> displayableValues = new ArrayList<Value<?, ?>>();
        for (Value<?, ?> value : values) {
            if (dependentValues.contains(value) || colorOnlyToggles.contains(value) || !ModuleValueDisplayFormatter.shouldDisplayValue(value)) continue;
            displayableValues.add(value);
        }
        return displayableValues;
    }

    private static boolean hasOnlyColorDependents(ToggleSetting booleanValue) {
        List<Value> dependentValues = booleanValue.getDependentValues();
        if (dependentValues.isEmpty()) {
            return false;
        }
        for (Value value : dependentValues) {
            if (value instanceof ColorPicker) continue;
            return false;
        }
        return true;
    }

    private static boolean hasNonEmptyLimitDependent(ToggleSetting booleanValue) {
        for (Value value : booleanValue.getDependentValues()) {
            ItemFilterList limitValue;
            if (!(value instanceof ItemFilterList) || ((List)(limitValue = (ItemFilterList)value).getValue()).isEmpty()) continue;
            return true;
        }
        return false;
    }

    private static String formatDescriptor(ValueDisplayDescriptor descriptor, boolean hasExactlyOneMode, boolean isPrimaryMode, boolean useFullName) {
        Value<?, ?> value = descriptor.getValue();
        if (value instanceof ToggleSetting) {
            ToggleSetting booleanValue = (ToggleSetting)value;
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents(booleanValue)) {
                return ModuleValueDisplayFormatter.formatLimitDependents(booleanValue);
            }
            return useFullName ? descriptor.getFullName() : descriptor.getDisplayName();
        }
        String displayValue = value.getDisplayValue();
        if ((hasExactlyOneMode || isPrimaryMode) && value instanceof OptionSetting) {
            return displayValue;
        }
        String displayName = useFullName ? descriptor.getFullName() : descriptor.getDisplayName();
        if (displayValue.isEmpty()) {
            return displayName;
        }
        return displayValue + " " + displayName;
    }

    private static boolean hasExactlyOneOptionSetting(List<Value<?, ?>> values) {
        int modeCount = 0;
        for (Value<?, ?> value : values) {
            if (!(value instanceof OptionSetting) || ++modeCount <= 1) continue;
            return false;
        }
        return modeCount == 1;
    }


    private static boolean hasExactlyOneDescriptorMode(List<ValueDisplayDescriptor> descriptors) {
        int modeCount = 0;
        for (ValueDisplayDescriptor descriptor : descriptors) {
            if (!(descriptor.getValue() instanceof OptionSetting) || ++modeCount <= 1) continue;
            return false;
        }
        return modeCount == 1;
    }

    public static String formatDescriptorSummary(List<ValueDisplayDescriptor> descriptors) {
        List<ValueDisplayDescriptor> displayableDescriptors = ModuleValueDisplayFormatter.filterDisplayableDescriptors(descriptors);
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneDescriptorMode(displayableDescriptors);
        OptionSetting modeValue = ModuleValueDisplayFormatter.findUniqueDescriptorSubModuleMode(displayableDescriptors);
        if (modeValue != null) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            boolean addedValue = false;
            Iterator<ValueDisplayDescriptor> iterator = displayableDescriptors.iterator();
            while (iterator.hasNext()) {
                ValueDisplayDescriptor descriptor = iterator.next();
                boolean isPrimaryMode = descriptor.getValue() == modeValue;
                String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, isPrimaryMode, false);
                if (formattedValue.isEmpty()) continue;
                stringJoiner.add(formattedValue);
                addedValue = true;
            }
            if (!addedValue) {
                return "";
            }
            String summary = stringJoiner.toString();
            if (summary.length() < 50) {
                StringJoiner fullNameSummary = new StringJoiner(", ");
                Iterator<ValueDisplayDescriptor> iterator2 = displayableDescriptors.iterator();
                while (iterator2.hasNext()) {
                    ValueDisplayDescriptor descriptor = iterator2.next();
                    boolean isPrimaryMode = descriptor.getValue() == modeValue;
                    String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, isPrimaryMode, true);
                    if (formattedValue.isEmpty()) continue;
                    fullNameSummary.add(formattedValue);
                }
                return fullNameSummary.toString();
            }
            return summary;
        }
        StringJoiner stringJoiner = new StringJoiner(", ");
        boolean addedValue = false;
        for (ValueDisplayDescriptor descriptor : displayableDescriptors) {
            String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, false, false);
            if (formattedValue.isEmpty()) continue;
            stringJoiner.add(formattedValue);
            addedValue = true;
        }
        if (!addedValue) {
            return "";
        }
        String summary = stringJoiner.toString();
        if (summary.length() < 50) {
            StringJoiner fullNameSummary = new StringJoiner(", ");
            for (ValueDisplayDescriptor descriptor : displayableDescriptors) {
                String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, false, true);
                if (formattedValue.isEmpty()) continue;
                fullNameSummary.add(formattedValue);
            }
            return fullNameSummary.toString();
        }
        return summary;
    }

    public static List<ClickGuiModuleCardRenderState> buildValueRenderStates(List<Value<?, ?>> values) {
        List<Value<?, ?>> displayableValues = ModuleValueDisplayFormatter.filterDisplayableValues(values);
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneOptionSetting(displayableValues);
        OptionSetting subModuleMode = ModuleValueDisplayFormatter.findUniqueSubModuleMode(displayableValues);
        return ModuleValueDisplayFormatter.buildValueRenderStatesInternal(displayableValues, hasExactlyOneMode, subModuleMode);
    }

    private static boolean containsSubModuleMode(OptionSetting modeValue) {
        for (ModeSelection modeSelection : modeValue.getModes()) {
            if (!(modeSelection instanceof SubHackValue)) continue;
            return true;
        }
        return false;
    }

    public static String formatValueSummary(List<Value<?, ?>> values) {
        List<Value<?, ?>> displayableValues = ModuleValueDisplayFormatter.filterDisplayableValues(values);
        StringJoiner stringJoiner = new StringJoiner(", ");
        boolean hasExactlyOneMode = ModuleValueDisplayFormatter.hasExactlyOneOptionSetting(displayableValues);
        OptionSetting modeValue = ModuleValueDisplayFormatter.findUniqueSubModuleMode(displayableValues);
        Iterator<Value<?, ?>> iterator = displayableValues.iterator();
        while (iterator.hasNext()) {
            Value<?, ?> value = iterator.next();
            String formattedValue = ModuleValueDisplayFormatter.formatValue(value, hasExactlyOneMode, value == modeValue);
            if (formattedValue.isEmpty()) continue;
            stringJoiner.add(formattedValue);
        }
        return stringJoiner.toString();
    }

    private static OptionSetting findUniqueSubModuleMode(List<Value<?, ?>> values) {
        OptionSetting modeValue = null;
        for (Value<?, ?> value : values) {
            if (!(value instanceof OptionSetting) || !ModuleValueDisplayFormatter.containsSubModuleMode((OptionSetting)value)) continue;
            if (modeValue != null) {
                return null;
            }
            modeValue = (OptionSetting)value;
        }
        return modeValue;
    }

    private static List<ClickGuiModuleCardRenderState> buildDescriptorRenderStatesInternal(List<ValueDisplayDescriptor> descriptors, boolean hasExactlyOneMode, OptionSetting modeValue, boolean useFullNames) {
        ArrayList<ClickGuiModuleCardRenderState> renderStates = new ArrayList<ClickGuiModuleCardRenderState>();
        boolean firstValue = true;
        boolean previousValueHasColor = false;
        if (modeValue != null) {
            for (ValueDisplayDescriptor descriptor : descriptors) {
                Color color;
                if (descriptor.getValue() != modeValue) continue;
                String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, true, useFullNames);
                if (formattedValue.isEmpty() && modeValue.getDisplayColor() == null) break;
                firstValue = false;
                if (!formattedValue.isEmpty()) {
                    renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
                }
                if ((color = modeValue.getDisplayColor()) == null) break;
                renderStates.add(ClickGuiModuleCardRenderState.b(color));
                previousValueHasColor = true;
                break;
            }
        }
        for (ValueDisplayDescriptor descriptor : descriptors) {
            Color color;
            Value<?, ?> value = descriptor.getValue();
            if (value == modeValue) continue;
            String formattedValue = ModuleValueDisplayFormatter.formatDescriptor(descriptor, hasExactlyOneMode, false, useFullNames);
            if (formattedValue.isEmpty() && value.getDisplayColor() == null) continue;
            if (!firstValue) {
                renderStates.add(ClickGuiModuleCardRenderState.j(previousValueHasColor ? " " : ", "));
            }
            firstValue = false;
            if (!formattedValue.isEmpty()) {
                renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
            }
            previousValueHasColor = (color = value.getDisplayColor()) != null;
            if (!previousValueHasColor) continue;
            renderStates.add(ClickGuiModuleCardRenderState.b(color));
        }
        return renderStates;
    }

    private static int getTextLength(List<ClickGuiModuleCardRenderState> renderStates) {
        int textLength = 0;
        for (ClickGuiModuleCardRenderState renderState : renderStates) {
            if (!renderState.n$src$Z$1c2q0zn()) continue;
            textLength += renderState.n().length();
        }
        return textLength;
    }

    private static OptionSetting findUniqueDescriptorSubModuleMode(List<ValueDisplayDescriptor> descriptors) {
        OptionSetting modeValue = null;
        for (ValueDisplayDescriptor descriptor : descriptors) {
            Value<?, ?> value = descriptor.getValue();
            if (!(value instanceof OptionSetting) || !ModuleValueDisplayFormatter.containsSubModuleMode((OptionSetting)value)) continue;
            if (modeValue != null) {
                return null;
            }
            modeValue = (OptionSetting)value;
        }
        return modeValue;
    }

    private static List<ClickGuiModuleCardRenderState> buildValueRenderStatesInternal(List<Value<?, ?>> values, boolean hasExactlyOneMode, OptionSetting modeValue) {
        ArrayList<ClickGuiModuleCardRenderState> renderStates = new ArrayList<ClickGuiModuleCardRenderState>();
        boolean firstValue = true;
        boolean previousValueHasColor = false;
        if (modeValue != null) {
            String formattedValue = ModuleValueDisplayFormatter.formatValue(modeValue, hasExactlyOneMode, true);
            if (!formattedValue.isEmpty() || modeValue.getDisplayColor() != null) {
                Color color;
                firstValue = false;
                if (!formattedValue.isEmpty()) {
                    renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
                }
                if ((color = modeValue.getDisplayColor()) != null) {
                    renderStates.add(ClickGuiModuleCardRenderState.b(color));
                    previousValueHasColor = true;
                }
            }
        }
        for (Value<?, ?> value : values) {
            Color color;
            String formattedValue;
            if (value == modeValue || (formattedValue = ModuleValueDisplayFormatter.formatValue(value, hasExactlyOneMode, false)).isEmpty() && value.getDisplayColor() == null) continue;
            if (!firstValue) {
                renderStates.add(ClickGuiModuleCardRenderState.j(previousValueHasColor ? " " : ", "));
            }
            firstValue = false;
            if (!formattedValue.isEmpty()) {
                renderStates.add(ClickGuiModuleCardRenderState.j(formattedValue));
            }
            previousValueHasColor = (color = value.getDisplayColor()) != null;
            if (!previousValueHasColor) continue;
            renderStates.add(ClickGuiModuleCardRenderState.b(color));
        }
        return renderStates;
    }

    private static boolean hasOnlyLimitDependents(ToggleSetting booleanValue) {
        List<Value> dependentValues = booleanValue.getDependentValues();
        if (dependentValues.isEmpty()) {
            return false;
        }
        for (Value value : dependentValues) {
            if (value instanceof ItemFilterList) continue;
            return false;
        }
        return true;
    }

    private static List<ValueDisplayDescriptor> filterDisplayableDescriptors(List<ValueDisplayDescriptor> descriptors) {
        ArrayList<ValueDisplayDescriptor> displayableDescriptors = new ArrayList<ValueDisplayDescriptor>();
        for (ValueDisplayDescriptor descriptor : descriptors) {
            if (!ModuleValueDisplayFormatter.shouldDisplayValue(descriptor.getValue())) continue;
            displayableDescriptors.add(descriptor);
        }
        return displayableDescriptors;
    }

    private static boolean shouldDisplayValue(Value<?, ?> value) {
        if (value instanceof EntityTargetFilterValue) {
            return false;
        }
        if (!value.areConditionsMet()) {
            return false;
        }
        if (value instanceof ToggleSetting) {
            if (ModuleValueDisplayFormatter.hasOnlyLimitDependents((ToggleSetting)value)) {
                return ((ToggleSetting)value).getEffectiveValue() != false && ModuleValueDisplayFormatter.hasNonEmptyLimitDependent((ToggleSetting)value);
            }
            return ((ToggleSetting)value).getEffectiveValue();
        }
        return !value.getDisplayValue().isEmpty() || value.getDisplayColor() != null;
    }

    private static String formatLimitDependents(ToggleSetting booleanValue) {
        StringJoiner summary = new StringJoiner(", ");
        HashSet<Value> seenValues = new HashSet<Value>();
        for (Value value : booleanValue.getDependentValues()) {
            ItemFilterList limitValue;
            int entryCount;
            if (!(value instanceof ItemFilterList) || !seenValues.add(value) || (entryCount = ((List)(limitValue = (ItemFilterList)value).getValue()).size()) == 0) continue;
            summary.add(entryCount + " " + limitValue.getName());
        }
        String formattedSummary = summary.toString();
        return formattedSummary.isEmpty() ? "" : formattedSummary;
    }
}
