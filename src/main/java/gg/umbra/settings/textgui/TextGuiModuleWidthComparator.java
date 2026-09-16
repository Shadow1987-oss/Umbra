package gg.umbra.settings.textgui;

import gg.umbra.Umbra;
import gg.umbra.module.HackModule;
import gg.umbra.settings.TextGuiSettings;
import gg.umbra.ui.font.SmoothFontRenderer;
import java.util.Comparator;

public class TextGuiModuleWidthComparator
implements Comparator<HackModule> {
    @Override
    public int compare(HackModule mod, HackModule mod2) {
        SmoothFontRenderer smoothFontRenderer = Umbra.INSTANCE.getFontManager().Y();
        return Double.compare(smoothFontRenderer.N(mod2.getName() + mod2.getSuffixForMode(TextGuiSettings.INSTANCE.getSuffixModeIndex())), smoothFontRenderer.N(mod.getName() + mod.getSuffixForMode(TextGuiSettings.INSTANCE.getSuffixModeIndex())));
    }

    public TextGuiModuleWidthComparator() {
    }
}
