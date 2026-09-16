package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.Umbra;
import gg.umbra.config.Profile;
import gg.umbra.ui.click.frame.FrameNavigationButtonComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.unmap.ColorUtil;
import gg.umbra.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ProfilesFrameNavigationButtonComponent
extends FrameNavigationButtonComponent {

    public ProfilesFrameNavigationButtonComponent() {
        super("Profiles", null, ProfilesSettingsFrame.class);
    }

    @Override
    public void H() {
        double d;
        super.H();
        Profile profile = Umbra.INSTANCE.getProfilesManager().getActiveProfile();
        String string = profile.getName();
        int n = 10;
        if (string.length() > n) {
            string = string.substring(0, n) + "...";
        }
        SmoothFontRenderer smoothFontRenderer = this.getAlternateFontRenderer(0.8);
        double d10 = this.n() + this.L() / 2.0;
        double d11 = smoothFontRenderer.N(string);
        double d12 = smoothFontRenderer.d(string);
        double d13 = d12 + 6.0;
        double d14 = d11 + (double)9;
        double d15 = d = 23.0;
        double d16 = d15 + 4.0;
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + this.A() - d16 - d11, d10 - d13 / 2.0, d14, d13, ColorUtil.withAlpha(Color.WHITE, 7));
        smoothFontRenderer.d(string, this.G$src$D$1b2f02a() + this.A() - d15 - d11, d10 - d12 / 2.0 + 0.5, new Color(115, 113, 115));
    }
}

