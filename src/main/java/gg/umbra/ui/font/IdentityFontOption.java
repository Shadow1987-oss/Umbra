package gg.umbra.ui.font;

import gg.umbra.ui.font.BaseFontOption;

public class IdentityFontOption
extends BaseFontOption {
    @Override
    public String s(String string) {
        return string;
    }

    public IdentityFontOption(String string) {
        super(string);
    }
}

