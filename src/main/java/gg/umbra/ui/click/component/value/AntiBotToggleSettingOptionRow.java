package gg.umbra.ui.click.component.value;

import gg.umbra.hacks.exploits.antibot.AntiBotToggleSetting;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class AntiBotToggleSettingOptionRow
extends GuiComponent {
    private static final double SWATCH_BORDER_WIDTH = 1.0;
    private final AntiBotToggleSetting teamColorPicker;
    private final String label;
    private static final double SWATCH_WIDTH = 8.0;
    private static final double SWATCH_HEIGHT = 8.0;

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 16.0;
    }

    @Override
    public void F() {
    }


    public String getFormattedTeamColor() {
        Integer teamColor = (Integer)this.teamColorPicker.getValue();
        if (teamColor == null) {
            return "None";
        }
        return String.format("#%06X", teamColor);
    }

    public AntiBotToggleSettingOptionRow(AntiBotToggleSetting antiBotToggleSetting) {
        this(antiBotToggleSetting, "Team color");
    }

    @Override
    public double x() {
        return 110.0;
    }

    public AntiBotToggleSettingOptionRow(AntiBotToggleSetting antiBotToggleSetting, String label) {
        this.teamColorPicker = antiBotToggleSetting;
        this.label = label;
        this.bindValue(antiBotToggleSetting);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void H() {
        this.onDisable();
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        smoothFontRenderer.d(this.label, this.G$src$D$1b2f02a() + 5.0, this.n() + this.L() / 2.0 - smoothFontRenderer.d(this.label) / 2.0, AntiBotToggleSettingOptionRow.J.Z);
        Integer teamColor = (Integer)this.teamColorPicker.getValue();
        Color swatchColor = teamColor != null ? new Color(teamColor) : Color.GRAY;
        double swatchX = this.G$src$D$1b2f02a() + this.A() - 5.0 - SWATCH_WIDTH;
        double swatchY = this.n() + (this.L() - SWATCH_HEIGHT) / 2.0;
        GuiRenderPrimitives.B(swatchX, swatchY, SWATCH_WIDTH, SWATCH_HEIGHT, swatchColor, (float)SWATCH_BORDER_WIDTH);
    }

    public AntiBotToggleSetting getTeamColorPicker() {
        return this.teamColorPicker;
    }

    @Override
    public void u() {
        this.w("Current team color: " + this.getFormattedTeamColor());
    }
}

