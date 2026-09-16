package gg.umbra.settings;

import gg.umbra.Umbra;
import gg.umbra.module.MinecraftVersionConstraint;
import gg.umbra.module.HackModule;
import gg.umbra.settings.textgui.TextGuiModuleWidthComparator;
import gg.umbra.unmap.ModeOption;
import gg.umbra.utils.NameComparator;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.ModuleNameSuggestionProvider;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.value.StringValue;
import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextGuiSettings
extends ConfigSettingsModule {
    public final ToggleSetting smoothFont;
    public final ModeOption basicSuffixMode;
    public final ToggleSetting rescale;
    public final OptionSetting colorMode;
    public final OptionSetting suffixMode;
    public final ModeOption sortByLength;
    public final OptionSetting sortMode;
    public final ToggleSetting addCustomText;
    public final ModeOption matchGuiColor;
    public final ModeOption extendedSuffixMode;
    public final SliderSetting scale;
    public final ToggleSetting clickDisable;
    public final ModeOption moduleColor;
    public final ColorPicker textGuiColor;
    public final StringValue customText;
    public final ModeOption customColor;
    public final ToggleSetting shadow;
    public final ToggleSetting hideModules;
    public final ModeOption noSuffix;
    public static TextGuiSettings INSTANCE;
    public final OptionalItemFilter hiddenModules;
    public final ToggleSetting customTextColorEnabled;
    public final ModeOption alphabeticalSort = new ModeOption("Alphabetical");
    public final ToggleSetting watermark;
    public final ColorPicker customTextColor;
    public final ToggleSetting renderBackground;
    public final ToggleSetting animations;

    public int getSuffixModeIndex() {
        return this.suffixMode.getValue() == this.basicSuffixMode ? 0 : (this.suffixMode.getValue() == this.extendedSuffixMode ? 1 : 2);
    }

    public String getEnabledModuleNames() {
        CopyOnWriteArrayList<HackModule> copyOnWriteArrayList = new CopyOnWriteArrayList<HackModule>(Umbra.INSTANCE.getHackManager().collectMods());
        if (this.sortMode.getValue() == this.alphabeticalSort) {
            copyOnWriteArrayList.sort(new NameComparator());
        } else if (this.sortMode.getValue() == this.sortByLength) {
            copyOnWriteArrayList.sort(new TextGuiModuleWidthComparator());
        }
        String string = "  ";
        for (HackModule mod : copyOnWriteArrayList) {
            if (!mod.isEnabled() || mod.getGuiColor() == 0) continue;
            string = string + mod.getName() + ", ";
        }
        if (string.length() > 2) {
            string = string.substring(0, string.length() - 2);
        }
        return string;
    }

    public TextGuiSettings() {
        super("Text GUI");
        this.sortByLength = new ModeOption("Length");
        this.sortMode = OptionSetting.create((Object)this, "Sort", this.sortByLength, this.sortByLength, this.alphabeticalSort);
        this.moduleColor = new ModeOption("Module color");
        this.matchGuiColor = new ModeOption("Match GUI color");
        this.customColor = new ModeOption("Custom color");
        this.colorMode = OptionSetting.create((Object)this, "Color Mode", this.moduleColor, this.moduleColor, this.matchGuiColor, this.customColor);
        this.textGuiColor = ColorPicker.create(this, "Text GUI color", new Color(206, 7, 7));
        this.clickDisable = ToggleSetting.create(this, "Click disable", false, "Click modules in text gui to toggle them");
        this.shadow = ToggleSetting.create(this, "Shadow", true, "Renders shadowed text");
        this.animations = ToggleSetting.create(this, "Animations", true, "Use animations on text gui");
        this.watermark = ToggleSetting.create(this, "Watermark", false, "Renders the Umbra watermark");
        this.renderBackground = ToggleSetting.create(this, "Render background", true);
        this.hideModules = ToggleSetting.create(this, "Hide modules", false, "Allows you to blacklist certain modules from being shown");
        this.hiddenModules = (OptionalItemFilter)OptionalItemFilter.createWithDescription(this, "module-show-blacklist", "Hidden Modules", "Name of module to hide", OptionalItemFilter.BLOCK_LIST_COLOR, Arrays.asList("ESP", "NameTags", "StorageESP")).setSuggestionProvider(new ModuleNameSuggestionProvider());
        this.rescale = ToggleSetting.create(this, "Rescale", true, "Rescales text GUI");
        this.scale = SliderSetting.create((Object)this, "Scale", "#.#", "", 0.1, 1.0, 2.0, 0.1);
        this.addCustomText = ToggleSetting.create(this, "Add custom text", false);
        this.customText = StringValue.create(this, "Custom text", "");
        this.customTextColorEnabled = ToggleSetting.create(this, "Set custom text color", false);
        this.customTextColor = ColorPicker.create(this, "Color of custom text", new Color(206, 7, 7));
        this.smoothFont = ToggleSetting.create(this, "Smooth font", true);
        this.basicSuffixMode = new ModeOption("Basic");
        this.extendedSuffixMode = new ModeOption("Extended");
        this.noSuffix = new ModeOption("None");
        this.suffixMode = OptionSetting.create((Object)this, "Suffix mode", this.basicSuffixMode, this.basicSuffixMode, this.extendedSuffixMode, this.noSuffix);
        INSTANCE = this;
        this.addValue(this.sortMode, this.colorMode, this.textGuiColor, this.suffixMode, this.clickDisable, this.shadow, this.animations, this.watermark, this.renderBackground, this.hideModules, this.hiddenModules, this.rescale, this.scale, this.addCustomText, this.customText, this.customTextColorEnabled, this.customTextColor);
        this.U(this.smoothFont, new MinecraftVersionConstraint[0]);
        this.customText.setBase64Encoded(true);
        this.colorMode.addActiveMode(this.textGuiColor, this.customColor);
        this.hideModules.addDependentValues(this.hiddenModules);
        this.addCustomText.addDependentValues(this.customText);
        this.addCustomText.addDependentValues(this.customTextColorEnabled);
        this.customTextColorEnabled.addDependentValues(this.customTextColor);
    }

}

