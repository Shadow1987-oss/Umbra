package gg.umbra.settings;

import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.settings.ClientSettings;
import gg.umbra.settings.TextGuiSettings;
import gg.umbra.ui.click.component.DropdownSelectComponent;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import gg.umbra.ui.click.component.value.ColorPickerEditorComponent;
import gg.umbra.ui.click.component.value.ListValueComponent;
import gg.umbra.ui.click.component.value.NumberSliderComponent;
import gg.umbra.ui.click.component.value.SliderComponentBase;
import gg.umbra.ui.click.component.value.StringValueTextInputComponent;
import gg.umbra.ui.click.frame.impl.TextGuiOverlayComponent;
import gg.umbra.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.umbra.ui.click.frame.impl.quickactions.QuickActionsFrame;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.wrapper.impl.Minecraft;

public class TextGuiSettingsFrame
extends HudSettingsFrameBase {
    private boolean wasPinned;
    private final TextGuiOverlayComponent overlay;
    private final StringValueTextInputComponent customTextInput;
    private final BooleanToggleComponent backgroundToggle;
    private final SliderComponentBase scaleSlider;
    private final BooleanToggleComponent watermarkToggle;
    private final BooleanToggleComponent smoothFontToggle;
    private final DropdownSelectComponent<ModeSelection> sortModeDropdown;
    private final DropdownSelectComponent<ModeSelection> colorModeDropdown;
    private final TextGuiSettings settings = Umbra.INSTANCE.getHackManager().getMod(TextGuiSettings.class);
    private final BooleanToggleComponent shadowToggle;
    private double targetX;
    private double savedHeight;
    private final BooleanToggleComponent animationsToggle;
    private final BooleanToggleComponent customTextColorToggle;
    private final DropdownSelectComponent<ModeSelection> suffixModeDropdown;
    private final ListValueComponent hiddenModulesList;
    private final BooleanToggleComponent hideModulesToggle;
    private final ColorPickerEditorComponent textGuiColorEditor;
    private final ColorPickerEditorComponent customTextColorEditor;
    private boolean rightAnchored;
    private boolean expanded;
    private final BooleanToggleComponent addCustomTextToggle;
    private final BooleanToggleComponent clickDisableToggle;
    private double savedWidth;

    @Override
    public double x() {
        if (this.isPublicProfilePreview()) {
            return this.expanded ? this.savedWidth : this.overlay.x();
        }
        return super.x();
    }


    @Override
    public void H() {
        this.updateAnchor();
        super.H();
    }

    @Override
    public void Y() {
        this.updateAnchor();
        if (this.scaleSlider.isDragging()) {
            if (!this.expanded) {
                this.expanded = true;
                this.savedWidth = this.overlay.x();
                this.savedHeight = Math.max(26.0, this.overlay.C());
            }
        } else if (this.expanded) {
            this.expanded = false;
            this.recomputeAnchor();
            this.H(true);
        }
    }

    private void updateAnchor() {
        if (!this.isPublicProfilePreview()) {
            this.wasPinned = this.IU;
            this.rightAnchored = false;
            this.targetX = Double.NaN;
            return;
        }
        if (this.IU) {
            this.wasPinned = true;
            return;
        }
        if (this.wasPinned) {
            this.wasPinned = false;
            this.recomputeAnchor();
        }
        if (Double.isNaN(this.targetX) && this.isPastMidpoint()) {
            this.recomputeAnchor();
        }
        if (this.rightAnchored) {
            double anchoredX;
            double deltaX;
            if (Double.isNaN(this.targetX)) {
                this.targetX = this.G$src$D$1b2f02a() + this.A();
            }
            if ((deltaX = (anchoredX = Math.floor(this.targetX - this.A())) - this.G$src$D$1b2f02a()) != 0.0) {
                this.T(deltaX, 0.0);
            }
        }
    }

    public TextGuiSettingsFrame() {
        super("newtextgui", "Text GUI");
        this.sortModeDropdown = new DropdownSelectComponent(this.settings.sortMode);
        this.colorModeDropdown = new DropdownSelectComponent(this.settings.colorMode);
        this.textGuiColorEditor = new ColorPickerEditorComponent(this.settings.textGuiColor);
        this.suffixModeDropdown = new DropdownSelectComponent(this.settings.suffixMode);
        this.clickDisableToggle = new BooleanToggleComponent(this.settings.clickDisable);
        this.shadowToggle = new BooleanToggleComponent(this.settings.shadow);
        this.animationsToggle = new BooleanToggleComponent(this.settings.animations);
        this.watermarkToggle = new BooleanToggleComponent(this.settings.watermark);
        this.backgroundToggle = new BooleanToggleComponent(this.settings.renderBackground);
        this.hideModulesToggle = new BooleanToggleComponent(this.settings.hideModules);
        this.hiddenModulesList = new ListValueComponent(this.settings.hiddenModules);
        this.scaleSlider = new NumberSliderComponent(this.settings.scale);
        this.addCustomTextToggle = new BooleanToggleComponent(this.settings.addCustomText);
        this.customTextInput = new StringValueTextInputComponent(this.settings.customText);
        this.customTextColorToggle = new BooleanToggleComponent(this.settings.customTextColorEnabled);
        this.customTextColorEditor = new ColorPickerEditorComponent(this.settings.customTextColor);
        this.smoothFontToggle = new BooleanToggleComponent(this.settings.smoothFont);
        this.targetX = Double.NaN;
        this.addSettings(this.sortModeDropdown, this.suffixModeDropdown,
                this.colorModeDropdown, this.textGuiColorEditor, this.scaleSlider,
                this.clickDisableToggle, this.shadowToggle, this.animationsToggle,
                this.smoothFontToggle, this.watermarkToggle, this.backgroundToggle,
                this.hideModulesToggle, this.hiddenModulesList, this.addCustomTextToggle,
                this.customTextInput, this.customTextColorToggle, this.customTextColorEditor);
        this.overlay = new TextGuiOverlayComponent(this);
        this.h(this.overlay, new Object[0]);
    }

    @Override
    public void t(JsonObject jsonObject) {
        super.t(jsonObject);
        this.recomputeAnchor();
        ClientSettings.getFrame(QuickActionsFrame.class).m$src$Lgg_umbra_ui_click_frame_impl_quickactions_QuickA$1kmfigl().setValue(this.V$src$Z$1xhop3l());
    }

    @Override
    protected void renderHudModeBorder() {
    }

    @Override
    public void v() {
    }

    public TextGuiOverlayComponent K$src$Lgg_umbra_ui_click_frame_impl_TextGuiOverlayCompo$1shgn4i() {
        return this.overlay;
    }

    private void recomputeAnchor() {
        double threshold;
        if (!this.isPublicProfilePreview()) {
            this.rightAnchored = false;
            this.targetX = Double.NaN;
            return;
        }
        double centerX = this.G$src$D$1b2f02a() + this.A() / 2.0;
        this.rightAnchored = centerX >= (threshold = (double)Minecraft.J() / 4.0 / Umbra.INSTANCE.getClientSettings().getGuiScaleFactor());
        this.targetX = this.rightAnchored ? this.G$src$D$1b2f02a() + this.A() : Double.NaN;
    }

    private boolean isPastMidpoint() {
        double threshold;
        double centerX = this.G$src$D$1b2f02a() + this.A() / 2.0;
        return centerX >= (threshold = (double)Minecraft.J() / 4.0 / Umbra.INSTANCE.getClientSettings().getGuiScaleFactor());
    }

    @Override
    public String getName() {
        return "Text GUI";
    }

    @Override
    public double L() {
        if (this.isPublicProfilePreview()) {
            return this.expanded ? this.savedHeight : Math.max(26.0, this.overlay.C());
        }
        return super.L();
    }

    @Override
    public double A() {
        if (this.isPublicProfilePreview()) {
            return this.expanded ? this.savedWidth : this.overlay.x();
        }
        return this.x();
    }
}

