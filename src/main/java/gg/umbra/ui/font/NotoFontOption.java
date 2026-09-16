package gg.umbra.ui.font;

import gg.umbra.ui.font.FontFamily;
import gg.umbra.ui.font.FontOption;

public class NotoFontOption
extends FontOption {
    public NotoFontOption(String string) {
        super(string);
    }

    @Override
    public FontFamily b() {
        return FontFamily.NOTO;
    }
}

