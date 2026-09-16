package gg.umbra.ui.click.animation;

import gg.umbra.Umbra;
import gg.umbra.ui.click.animation.ColorAnimation;
import java.awt.Color;

public class ThemeColorAnimation
extends ColorAnimation {
    public ThemeColorAnimation(double d, Color color) {
        super(d, color, Umbra.INSTANCE.getClientSettings().guiColor.getMutableColor());
    }

    @Override
    public Color getInterpolatedColor() {
        super.setEndColor(Umbra.INSTANCE.getClientSettings().guiColor.getMutableColor());
        return super.getInterpolatedColor();
    }
}
