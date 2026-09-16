package gg.umbra.ui.click.component.value;

import gg.umbra.hacks.exploits.antibot.AntiBotToggleSetting;
import gg.umbra.hacks.exploits.antibot.AntiBotOptionSetting;
import gg.umbra.tools.inventory.HotbarSlotRuleEditorComponent;
import gg.umbra.tools.inventory.HotbarSlotRuleValue;
import gg.umbra.tools.inventory.cleaner.InventoryCleanerProfileValue;
import gg.umbra.tools.inventory.cleaner.ui.InventoryCleanerProfileValueComponent;
import gg.umbra.ui.click.component.DropdownSelectComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.input.BindValueRowComponent;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.value.BindValue;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.EntityTargetFilterValue;
import gg.umbra.value.ListValue;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalLimitEntry;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.value.SnapshotValueAccessor;
import gg.umbra.value.StringMapValue;
import gg.umbra.value.Value;
import gg.umbra.value.ValueSnapshot;

public class ValueComponentFactory {
    public static GuiComponent createValueComponent(Value<?, ?> value, boolean snapshotEditor, ValueComponentMode componentMode) {
        GuiComponent component = value.getBoundComponent();
        if (value instanceof ToggleSetting) {
            component = new BooleanToggleComponent((ToggleSetting)value);
        } else if (value instanceof AntiBotOptionSetting) {
            component = new ColorPickerDropdownComponent((AntiBotOptionSetting)value);
        } else if (value instanceof AntiBotToggleSetting) {
            component = new AntiBotToggleSettingOptionRow((AntiBotToggleSetting)value);
        } else if (value instanceof OptionSetting) {
            component = new DropdownSelectComponent((OptionSetting)value);
            if (componentMode == ValueComponentMode.STANDALONE) {
                ((DropdownSelectComponent)component).setHighlightedStyle(true);
            }
        } else if (value instanceof SliderSetting) {
            component = new NumberSliderComponent((SliderSetting)value);
        } else if (value instanceof RandomRangeSetting) {
            component = new RandomRangeSliderComponent((RandomRangeSetting)value);
        } else if (value instanceof ColorPicker) {
            component = new ColorPickerEditorComponent((ColorPicker)value);
        } else if (value instanceof ListValue) {
            ToggleSetting parentToggle;
            ListValueComponent listValueComponent = new ListValueComponent((ListValue)value);
            if (componentMode == ValueComponentMode.STANDALONE) {
                listValueComponent.setMode(ValueComponentMode.STANDALONE);
            }
            component = listValueComponent;
            ListValue listValue = (ListValue)value;
            if (listValue.getParent() instanceof ToggleSetting && (parentToggle = (ToggleSetting)listValue.getParent()).getTerminalDependentValue() != null && parentToggle.getTerminalDependentValue().equals(listValue)) {
                component = null;
            }
        } else if (value instanceof HotbarSlotRuleValue) {
            if (!snapshotEditor) {
                HotbarSlotRuleValue hotbarSlotRuleValue = (HotbarSlotRuleValue)value;
                if (hotbarSlotRuleValue.getEditor() != null) {
                    component = hotbarSlotRuleValue.getEditor();
                    component.setVisible(true);
                } else {
                    component = new HotbarSlotRuleEditorComponent(hotbarSlotRuleValue);
                }
            }
        } else if (value instanceof EntityTargetFilterValue) {
            component = snapshotEditor ? new EntityTargetFilterPopupComponent((EntityTargetFilterValue)value) : new EntityTargetFilterComponent((EntityTargetFilterValue)value);
        } else if (value instanceof StringMapValue) {
            component = new StringMapValueComponent((StringMapValue)value);
        } else if (value instanceof BindValue) {
            component = new BindValueRowComponent((BindValue)value);
        } else if (value instanceof InventoryCleanerProfileValue) {
            component = new InventoryCleanerProfileValueComponent((InventoryCleanerProfileValue)value);
        }
        return component;
    }


    public static GuiComponent createMainValueComponent(Value<?, ?> value) {
        return ValueComponentFactory.createValueComponent(value, false, ValueComponentMode.MAIN);
    }

    public static Value createSnapshotProxyValue(ValueSnapshot valueSnapshot) {
        Value sourceValue = valueSnapshot.getSourceValue();
        Value proxyValue = sourceValue.copyValueDefinition();
        proxyValue.setDefaultValue(sourceValue.getDefaultValue());
        proxyValue.setValue(valueSnapshot.getValue());
        proxyValue.setAccessor(new SnapshotValueAccessor(valueSnapshot, proxyValue));
        return proxyValue;
    }

    public static Object getComparableListEntryText(Object entry) {
        if (entry instanceof OptionalLimitEntry || entry instanceof ItemLimitData) {
            return entry.toString();
        }
        return null;
    }

    public static GuiComponent createMainValueComponent(Value<?, ?> value, boolean snapshotEditor) {
        return ValueComponentFactory.createValueComponent(value, snapshotEditor, ValueComponentMode.MAIN);
    }
}
