package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.ColorDividerComponent;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSectionFrameCloseSearchClickHandler;
import gg.umbra.ui.click.frame.impl.ClientSettingsSectionFrameCloseSettingsClickHandler;
import gg.umbra.ui.click.frame.impl.SettingsFrameHeaderComponent;

public class ClientSettingsSectionFrame
extends Frame {
    private String mR = "Settings";

    public ClientSettingsSectionFrame() {
        this.setDisabledOverlayColor(ClientSettingsSectionFrame.J.i);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        this.Y(clientSettingsSearchFrame.L());
        this.o(clientSettingsSearchFrame.A());
        this.s$src$V$yca8r0();
        this.addChildren(new ColorDividerComponent(ClientSettingsSectionFrame.J.l));
        this.setVisible(false);
        this.L(false, false);
    }


    public ClientSettingsSectionFrame z(String string) {
        this.mR = string;
        return this;
    }

    @Override
    public void v() {
    }

    @Override
    public void Y() {
        this.Y(238.0);
    }

    @Override
    public String getName() {
        return this.mR;
    }

    public void s$src$V$yca8r0() {
        if (this.j$src$Lgg_umbra_ui_click_frame_FrameHeaderComponent_$175vsfc() != null) {
            this.removeChild(this.j$src$Lgg_umbra_ui_click_frame_FrameHeaderComponent_$175vsfc());
        }
        SettingsFrameHeaderComponent settingsFrameHeaderComponent = new SettingsFrameHeaderComponent(this, this.mR);
        settingsFrameHeaderComponent.n(new ClientSettingsSectionFrameCloseSettingsClickHandler(this));
        settingsFrameHeaderComponent.f(new ClientSettingsSectionFrameCloseSearchClickHandler(this));
        this.Y(settingsFrameHeaderComponent);
    }
}

