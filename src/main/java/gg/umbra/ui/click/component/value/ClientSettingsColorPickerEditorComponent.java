package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.IconButtonComponent;
import gg.umbra.ui.click.component.value.ColorChannelSliderComponent;
import gg.umbra.ui.click.component.value.ColorChannelType;
import gg.umbra.ui.click.component.value.ColorPaletteSliderComponent;
import gg.umbra.ui.click.component.value.ColorPreviewSwatchComponent;
import gg.umbra.ui.click.component.value.ColorPickerEditorExpandToggleClickHandler;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.ImageRenderer;
import gg.umbra.value.ColorPicker;
import java.awt.Color;

public class ClientSettingsColorPickerEditorComponent
extends GuiComponent {
    private ColorPaletteSliderComponent paletteSlider;
    private ColorChannelSliderComponent saturationSlider;
    private static String[] legacyState;
    private IconButtonComponent collapseButton = new IconButtonComponent("upcollapse", 0.3);
    private boolean collapsed = true;
    private ColorChannelSliderComponent vibranceSlider;
    private final ColorPreviewSwatchComponent previewSwatch;
    private ColorPicker colorValue;
    private ColorChannelSliderComponent customColorSlider;

    @Override
    public void F() {
    }

    static {
        ClientSettingsColorPickerEditorComponent.setLegacyState(null);
    }

    static boolean setCollapsedCompat(ClientSettingsColorPickerEditorComponent editor, boolean collapsed) {
        editor.collapsed = collapsed;
        return editor.collapsed;
    }

    @Override
    public void H() {
        this.onDisable();
        this.paletteSlider.o(this.A());
        this.customColorSlider.o(this.A());
        this.saturationSlider.o(this.A());
        this.vibranceSlider.o(this.A());
        this.paletteSlider.K(this.G$src$D$1b2f02a());
        this.paletteSlider.S(this.n());
        this.collapseButton.K(this.G$src$D$1b2f02a() + this.paletteSlider.getLabelWidth() + 5.0);
        this.collapseButton.S(this.n() + 2.5);
        this.collapseButton.Y(this.paletteSlider.L() / 2.0);
        this.collapseButton.setIconResource(this.collapsed ? "downexpand" : "upcollapse");
        ImageRenderer.drawImage(this.colorValue.getMutableColor(), (float)(this.G$src$D$1b2f02a() + this.A() - 5.0 - 6.0), (float)this.n() + 5.0f, "colorpreview", 6.0f, 6.0f, false);
        this.previewSwatch.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - 5.0 - 6.0);
        this.previewSwatch.S(this.n() + 5.0);
        if (this.collapsed) {
            this.customColorSlider.setVisible(false);
            this.saturationSlider.setVisible(false);
            this.vibranceSlider.setVisible(false);
        } else {
            float arrowSize = 7.0f;
            double arrowLeftX = this.paletteSlider.getHandleBounds().o() - (double)(arrowSize / 2.0f);
            double arrowBaseY = this.n() + this.paletteSlider.L() + 2.5 + 3.0;
            double arrowCenterX = arrowLeftX + (double)arrowSize;
            double arrowTipY = arrowBaseY - 3.0;
            double arrowRightX = arrowLeftX + (double)(arrowSize * 2.0f);
            GuiRenderPrimitives.U(arrowLeftX, arrowBaseY, arrowCenterX, arrowTipY, arrowRightX, arrowBaseY, ClientSettingsColorPickerEditorComponent.J.r);
            this.customColorSlider.setVisible(true);
            this.customColorSlider.K(this.G$src$D$1b2f02a());
            this.customColorSlider.S(this.n() + 20.0 + 5.0);
            this.saturationSlider.setVisible(true);
            this.saturationSlider.K(this.G$src$D$1b2f02a());
            this.saturationSlider.S(this.n() + 40.0 + 10.0);
            this.vibranceSlider.setVisible(true);
            this.vibranceSlider.K(this.G$src$D$1b2f02a());
            this.vibranceSlider.S(this.n() + 60.0 + 15.0);
        }
    }


    @Override
    public void I() {
    }

    public void resetPaletteSlider() {
        this.paletteSlider.resetToMiddleColor();
    }

    public ClientSettingsColorPickerEditorComponent(ColorPicker colorValue) {
        this.colorValue = colorValue;
        this.bindValue(colorValue);
        this.paletteSlider = new ColorPaletteSliderComponent("GUI Theme", colorValue, new Color[]{new Color(250, 50, 56), new Color(242, 99, 33), new Color(252, 179, 22), ClientSettingsColorPickerEditorComponent.J.B, new Color(47, 122, 229), new Color(126, 84, 217), new Color(232, 96, 152)});
        this.customColorSlider = new ColorChannelSliderComponent(this.paletteSlider);
        this.saturationSlider = new ColorChannelSliderComponent(ColorChannelType.SATURATION, colorValue);
        this.vibranceSlider = new ColorChannelSliderComponent(ColorChannelType.VIBRANCE, colorValue);
        this.previewSwatch = new ColorPreviewSwatchComponent(colorValue);
        this.paletteSlider.setDisabledOverlayColor(this.getDisabledOverlayColor());
        this.customColorSlider.setDisabledOverlayColor(ClientSettingsColorPickerEditorComponent.J.r);
        this.saturationSlider.setDisabledOverlayColor(ClientSettingsColorPickerEditorComponent.J.r);
        this.vibranceSlider.setDisabledOverlayColor(ClientSettingsColorPickerEditorComponent.J.r);
        this.paletteSlider.setToolTips(null);
        this.saturationSlider.setToolTips(null);
        this.vibranceSlider.setToolTips(null);
        this.collapseButton.addClickListener(new ColorPickerEditorExpandToggleClickHandler(this));
        this.addChildren(this.paletteSlider, this.collapseButton, this.previewSwatch, this.customColorSlider, this.saturationSlider, this.vibranceSlider);
    }

    public static void setLegacyState(String[] state) {
        legacyState = state;
    }

    public static String[] getLegacyState() {
        return legacyState;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return this.collapsed ? 25.0 : 100.0;
    }

    @Override
    public void u() {
    }

    static boolean isCollapsedCompat(ClientSettingsColorPickerEditorComponent editor) {
        return editor.collapsed;
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
        this.paletteSlider.setForceCustomColor(false);
        this.paletteSlider.synchronizeSelectionFromValue();
    }

    @Override
    public double x() {
        return 110.0;
    }
}

