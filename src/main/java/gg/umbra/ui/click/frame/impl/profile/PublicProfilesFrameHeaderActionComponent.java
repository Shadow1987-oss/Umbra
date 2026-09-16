/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gg.umbra.settings.ClientSettings
 *  gg.umbra.ui.click.component.GuiClickListener
 *  gg.umbra.ui.click.component.GuiComponent
 *  gg.umbra.ui.click.component.SquareIconButtonComponent
 *  gg.umbra.ui.click.frame.Frame
 *  gg.umbra.ui.click.frame.FrameHeaderComponent
 *  gg.umbra.ui.font.SmoothFontRenderer
 *  gg.umbra.utils.render.ImageRenderer
 *  org.jetbrains.annotations.Nullable
 */
package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.SquareIconButtonComponent;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.frame.FrameHeaderComponent;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class PublicProfilesFrameHeaderActionComponent
extends FrameHeaderComponent {
    @Nullable
    private String iconResource;
    private static final String CLOSE_ICON_RESOURCE = "newclose";
    @Nullable
    private GuiClickListener clickListener;
    private float iconScale;
    private String title;
    private SquareIconButtonComponent closeButton = new SquareIconButtonComponent("newclose", 1.5);

    public PublicProfilesFrameHeaderActionComponent(Frame frame, String string, String string2) {
        this(frame, string, string2, 1.0);
    }

    public SquareIconButtonComponent O$src$Lgg_umbra_ui_click_component_SquareIconButtonComp$z3cp96() {
        return this.closeButton;
    }

    public PublicProfilesFrameHeaderActionComponent Q(@Nullable GuiClickListener guiClickListener) {
        this.clickListener = guiClickListener;
        return this;
    }

    public String K$src$Ljava_lang_String_$bvh3j6() {
        return this.title;
    }

    public void j(String string) {
        this.title = string;
    }

    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = PublicProfilesFrameHeaderActionComponent.J.A;
        double d = smoothFontRenderer.d(this.title);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        if (this.iconResource != null) {
            double d3 = this.n() + this.L() / 2.0 - (double)(8.0f * this.iconScale / 2.0f);
            smoothFontRenderer.d(this.title, this.G$src$D$1b2f02a() + 10.0 + 8.0, d2, color);
            ImageRenderer.drawImage((Color)color, (float)((float)this.G$src$D$1b2f02a() + 5.0f), (float)((float)d3), (String)this.iconResource, (float)(8.0f * this.iconScale), (float)(8.0f * this.iconScale), (boolean)false);
        } else {
            smoothFontRenderer.d(this.title, this.G$src$D$1b2f02a() + 5.0, d2, color);
        }
        this.closeButton.K(this.G$src$D$1b2f02a() + this.A() - 7.5 - 8.0);
        this.closeButton.S(this.n());
        this.closeButton.Y(this.L());
    }

    public PublicProfilesFrameHeaderActionComponent(Frame frame, @Nullable String string, String string2, double d) {
        super(frame);
        this.iconScale = (float)d;
        this.iconResource = string;
        this.title = string2;
        this.closeButton.addClickListener(() -> {
            if (this.clickListener != null) {
                this.clickListener.onPrimaryClick();
            }
            ClientSettings.setFrameVisibility(frame.getClass(), (boolean)false);
        });
        this.addChildren(new GuiComponent[]{this.closeButton});
    }
}
