package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.Umbra;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GlyphIconComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.PanelComponent;
import gg.umbra.ui.click.component.SimpleTextLabelComponent;
import gg.umbra.ui.click.frame.FrameHeaderComponent;
import gg.umbra.ui.click.frame.impl.ClientSettingsComponentFactory;
import gg.umbra.ui.click.frame.impl.ClientSettingsFrameSectionLabelComponent;
import gg.umbra.ui.click.frame.impl.ThemeComponentGroupKey;
import gg.umbra.ui.click.frame.impl.main.ClickGuiLayer;
import gg.umbra.ui.click.frame.impl.main.ClickGuiMainFrame;
import gg.umbra.ui.click.frame.impl.main.ClickGuiMainFrameHeaderActionComponent;
import gg.umbra.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.umbra.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.umbra.ui.click.frame.impl.main.ClickGuiSectionTabComponent;
import gg.umbra.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.ImageRenderer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClickGuiMainFrameHeader
extends FrameHeaderComponent {
    private static final double HEADER_HEIGHT = 40.0;
    private static final double EDGE_PADDING = 6.0;
    private static final double LOGO_SIZE = 18.0;
    private static final double VERSION_BADGE_HEIGHT = 15.0;
    private static final double ACTION_ICON_SPACING = 15.0;
    private final GlyphIconComponent syncIcon;
    private final ClickGuiMainFrameHeaderActionComponent refreshAction = new ClickGuiMainFrameHeaderActionComponent();
    private final GlyphIconComponent settingsIcon;
    private final List<ClickGuiSectionTabComponent> sectionTabs;
    private final List<GlyphIconComponent> actionIcons;
    private final SimpleTextLabelComponent versionLabel;
    private final GlyphIconComponent refreshIcon;
    private final GlyphIconComponent overlaysIcon;


    private static void lambda$null$5(GuiComponent[] guiComponentArray, PanelComponent panelComponent) {
        if (guiComponentArray != null) {
            double d = Math.max(0.0, panelComponent.A());
            for (GuiComponent guiComponent : guiComponentArray) {
                guiComponent.o(d);
                guiComponent.setExplicitWidth(d);
                panelComponent.h(guiComponent, new Object[0]);
            }
        }
    }

    private void lambda$new$1() {
        this.syncIcon.setVisible(false);
    }

    private static void lambda$new$0() {
        ClientSettings.INSTANCE.switchFrameStack(ClientSettings.mainStack);
        Umbra.INSTANCE.getPublicProfileSettings().guiStyle.setValue(Umbra.INSTANCE.getPublicProfileSettings().framesGuiStyle);
        Umbra.INSTANCE.getPublicProfileSettings().guiStyle.setValue(Umbra.INSTANCE.getPublicProfileSettings().centralGuiStyle);
        Umbra.INSTANCE.getNotificationManager().showInfo("Refreshed", "Umbra GUI refreshed", 1000L);
    }

    public void addSectionTab(ClickGuiSectionTabComponent clickGuiSectionTabComponent) {
        this.sectionTabs.add(clickGuiSectionTabComponent);
        this.addChildren(clickGuiSectionTabComponent);
    }

    private static void lambda$null$6(ThemeComponentGroupKey themeComponentGroupKey, ClickGuiMainFrame clickGuiMainFrame, String string, String string2) {
        Map<ThemeComponentGroupKey, GuiComponent[]> map = ClientSettingsComponentFactory.d(J, Umbra.INSTANCE.getClientSettings(), ClientSettings.INSTANCE, true);
        GuiComponent[] guiComponentArray = map.get(themeComponentGroupKey);
        clickGuiMainFrame.showOverlay(ClickGuiOverlaySpec.builder().title(string).initializeSidecar(arg_0 -> ClickGuiMainFrameHeader.lambda$null$4(string2, arg_0)).initializeContent(arg_0 -> ClickGuiMainFrameHeader.lambda$null$5(guiComponentArray, arg_0)).transitionMode(ClickGuiOverlayTransitionMode.PUSH).build());
    }

    private static void lambda$new$8(ClickGuiMainFrame clickGuiMainFrame) {
        clickGuiMainFrame.showOverlay(ClickGuiOverlaySpec.builder().title("Settings").initializeSidecar(ClickGuiMainFrameHeader::lambda$null$3).initializeContent(arg_0 -> ClickGuiMainFrameHeader.lambda$null$7(clickGuiMainFrame, arg_0)).build());
    }

    @Override
    public void H() {
        double d;
        this.syncIcon.setVisible(false);
        double d2 = this.w$src$Lgg_umbra_ui_click_frame_Frame_$y4htd0() != null ? this.w$src$Lgg_umbra_ui_click_frame_Frame_$y4htd0().G$src$D$1b2f02a() : this.G$src$D$1b2f02a();
        double d3 = this.w$src$Lgg_umbra_ui_click_frame_Frame_$y4htd0() != null ? this.w$src$Lgg_umbra_ui_click_frame_Frame_$y4htd0().n() : this.n();
        double d4 = this.w$src$Lgg_umbra_ui_click_frame_Frame_$y4htd0() != null ? this.w$src$Lgg_umbra_ui_click_frame_Frame_$y4htd0().A() : this.A();
        this.K(d2);
        this.S(d3);
        this.o(d4);
        double d5 = this.G$src$D$1b2f02a();
        double d6 = this.n();
        double d7 = this.A();
        double d8 = this.L();
        GuiRenderPrimitives.p(d5, d6, d7, d8, ClickGuiMainFrameHeader.J.r, false, 2.0f, 1.0f, 0.0f, ClickGuiMainFrameHeader.J.B, 3);
        GuiRenderPrimitives.C(d5, d6 + d8 - 1.0, d7, 0.5, ClickGuiMainFrameHeader.J.s);
        float f = (float)ImageRenderer.getImageHeight("umbralogo");
        float fLogoScale = f / (float)LOGO_SIZE;
        this.refreshAction.y(fLogoScale);
        double d9 = d5 + EDGE_PADDING;
        double d10 = d6 + (d8 - LOGO_SIZE) / 2.0;
        this.refreshAction.K(d9);
        this.refreshAction.S(d10);
        double dLogoWidth = (double)ImageRenderer.getImageWidth("umbralogo") / (double)fLogoScale;
        double d11 = d9 + dLogoWidth + 8.0;
        double d12 = this.versionLabel.getTextWidth() + 12.0;
        double d13 = d11;
        double d14 = d6 + (d8 - VERSION_BADGE_HEIGHT) / 2.0;
        GuiRenderPrimitives.B(d13, d14, d12, VERSION_BADGE_HEIGHT, ClientSettings.INSTANCE.getAccentColor(), (float)(VERSION_BADGE_HEIGHT / 2.0));
        this.versionLabel.K(d13 + 6.0);
        this.versionLabel.S(d14);
        this.versionLabel.Y(VERSION_BADGE_HEIGHT);
        double d15 = d13 + d12 + 14.0;
        for (ClickGuiSectionTabComponent clickGuiSectionTabComponent : this.sectionTabs) {
            double d16 = clickGuiSectionTabComponent.getLabelHeight();
            clickGuiSectionTabComponent.o(d16);
            clickGuiSectionTabComponent.Y(20.0);
            clickGuiSectionTabComponent.K(d15);
            clickGuiSectionTabComponent.S(d6 + (d8 - 20.0) / 2.0);
            d15 += d16 + 14.0;
        }
        double d17 = d6 + 5.0;
        int n = 0;
        for (int i = this.actionIcons.size() - 1; i >= 0; --i) {
            GlyphIconComponent glyphIconComponent = this.actionIcons.get(i);
            if (!glyphIconComponent.V$src$Z$1xhop3l()) continue;
            d = d5 + d7 - 10.0 - 5.0 - (double)n * ACTION_ICON_SPACING;
            glyphIconComponent.K(d);
            glyphIconComponent.S(d17);
            ++n;
        }
    }

    private static void lambda$null$4(String string, ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        clickGuiSidecarPanelBase.setLeadingIconKey(string != null ? string : "newsettings");
    }

    private static void lambda$null$3(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
        clickGuiSidecarPanelBase.setLeadingIconKey("newsettings");
    }

    private static void lambda$null$7(ClickGuiMainFrame clickGuiMainFrame, PanelComponent panelComponent) {
        Object object;
        Map<ThemeComponentGroupKey, GuiComponent[]> map = ClientSettingsComponentFactory.d(J, Umbra.INSTANCE.getClientSettings(), ClientSettings.INSTANCE, true);
        for (Map.Entry<ThemeComponentGroupKey, GuiComponent[]> object22 : map.entrySet()) {
            object = object22.getKey();
            ThemeComponentGroupKey groupKey = (ThemeComponentGroupKey)object;
            String string = groupKey.h();
            String string2 = groupKey.u();
            ClientSettingsFrameSectionLabelComponent clientSettingsFrameSectionLabelComponent = new ClientSettingsFrameSectionLabelComponent(string);
            double d = Math.max(0.0, panelComponent.A());
            clientSettingsFrameSectionLabelComponent.o(d);
            clientSettingsFrameSectionLabelComponent.setExplicitWidth(d);
            clientSettingsFrameSectionLabelComponent.Y(18.0);
            clientSettingsFrameSectionLabelComponent.setDisabledOverlayColor(ClickGuiMainFrameHeader.J.m);
            clientSettingsFrameSectionLabelComponent.addClickListener(() -> ClickGuiMainFrameHeader.lambda$null$6(groupKey, clickGuiMainFrame, string, string2));
            panelComponent.h(clientSettingsFrameSectionLabelComponent, new Object[0]);
        }
        List<GuiComponent> list = ClientSettingsComponentFactory.M(J, Umbra.INSTANCE.getClientSettings(), ClientSettings.INSTANCE, true);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            object = (GuiComponent)iterator.next();
            ((GuiComponent)object).setExplicitWidth(panelComponent.A());
            ((GuiComponent)object).o(panelComponent.A());
            panelComponent.h((GuiComponent)object, new Object[0]);
        }
    }

    public ClickGuiMainFrameHeader(ClickGuiMainFrame clickGuiMainFrame) {
        super(clickGuiMainFrame);
        this.refreshIcon = new GlyphIconComponent("weapons", 6.0, 6.0, 10.0, 10.0, ClickGuiMainFrameHeader.J.V, ClickGuiMainFrameHeader.J.f, null);
        this.syncIcon = new GlyphIconComponent("newsync", 6.0, 6.0, 10.0, 10.0, ClickGuiMainFrameHeader.J.V, ClickGuiMainFrameHeader.J.f, null);
        this.overlaysIcon = new GlyphIconComponent("newoverlays_2x", 6.0, 6.0, 10.0, 10.0, ClickGuiMainFrameHeader.J.V, ClickGuiMainFrameHeader.J.f, null);
        this.settingsIcon = new GlyphIconComponent("newsettings", 6.0, 6.0, 10.0, 10.0, ClickGuiMainFrameHeader.J.V, ClickGuiMainFrameHeader.J.f, null);
        this.sectionTabs = new ArrayList<ClickGuiSectionTabComponent>();
        this.actionIcons = new ArrayList<GlyphIconComponent>();
        this.Y(HEADER_HEIGHT);
        this.setShowDisabledOverlay(false);
        this.refreshIcon.setCenterHorizontally(true);
        this.refreshIcon.setCenterVertically(true);
        this.refreshIcon.setClickListener(ClickGuiMainFrameHeader::lambda$new$0);
        this.syncIcon.setCenterHorizontally(true);
        this.syncIcon.setCenterVertically(true);
        this.syncIcon.w("Save your profiles to the cloud");
        this.syncIcon.setClickListener(this::lambda$new$1);
        this.overlaysIcon.setCenterHorizontally(true);
        this.overlaysIcon.setCenterVertically(true);
        this.overlaysIcon.setBackgroundAnimationColors(ClickGuiMainFrameHeader.J.t, ClickGuiMainFrameHeader.J.M);
        this.overlaysIcon.setCornerRadius(5.0f);
        this.overlaysIcon.setClickListener(ClickGuiMainFrameHeader::lambda$new$2);
        this.settingsIcon.setCenterHorizontally(true);
        this.settingsIcon.setCenterVertically(true);
        this.settingsIcon.setBackgroundAnimationColors(ClickGuiMainFrameHeader.J.t, ClickGuiMainFrameHeader.J.M);
        this.settingsIcon.setCornerRadius(5.0f);
        this.settingsIcon.setClickListener(() -> ClickGuiMainFrameHeader.lambda$new$8(clickGuiMainFrame));
        this.actionIcons.addAll(Arrays.asList(this.syncIcon, this.overlaysIcon, this.settingsIcon));
        this.versionLabel = new SimpleTextLabelComponent("v" + Umbra.VERSION, 0.6, ClickGuiMainFrameHeader.J.r);
        this.versionLabel.setBold(true);
        this.versionLabel.setOffsetX(0.0f);
        this.versionLabel.setOffsetY(0.0f);
        this.versionLabel.setExtraHeight(0);
        this.addChildren(this.refreshAction, this.syncIcon, this.overlaysIcon, this.settingsIcon, this.versionLabel);
    }

    private static void lambda$new$2() {
        ClientSettings.clickGuiFrameManager.showLayer(ClickGuiLayer.OVERLAYS);
    }
}
