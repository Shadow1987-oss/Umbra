package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.Umbra;
import gg.umbra.friend.Friend;
import gg.umbra.friend.FriendEntry;
import gg.umbra.ui.click.component.FlowLayoutComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.PanelComponent;
import gg.umbra.ui.click.component.SimpleTextLabelComponent;
import gg.umbra.ui.click.component.SpacerComponent;
import gg.umbra.ui.click.component.gui.TextButton;
import gg.umbra.ui.click.component.input.SmallTextInputComponent;
import gg.umbra.ui.click.component.layout.PaddedComponent;
import gg.umbra.ui.click.frame.FrameScrollbarPlacement;
import java.awt.Color;

public class ClickGuiFriendsPage
extends ClickGuiPageBase {
    private final ClickGuiMainFrame mainFrame;
    private FlowLayoutComponent friendsList;
    private PanelComponent mainPanel;
    private boolean addingFriend;
    private FriendEntry selectedFriend;
    private SmallTextInputComponent nameInput;
    private SmallTextInputComponent aliasInput;
    private TextButton addButton;
    private TextButton cancelButton;
    private TextButton removeButton;
    private TextButton saveAliasButton;

    public ClickGuiFriendsPage(ClickGuiMainFrame clickGuiMainFrame, double d, double d2, double d3) {
        super(d, d2, d3, 2.0, "Friends");
        this.mainFrame = clickGuiMainFrame;
        this.addingFriend = false;
        this.renderSidebar();
        this.renderMainContent();
    }

    private void renderSidebar() {
        GuiComponent guiComponent = this.getSidebarHeader().f().get(0);
        this.getSidebarHeader().removeMarkedChildren();
        this.getSidebarHeader().h(guiComponent, "widthwrap");
        TextButton textButton = new TextButton("ADD FRIEND", 0.625, J.z(), J.z().brighter(), null, 2.0f, 1.0f, 53.0, 16.0);
        textButton.setIconResource("newadd");
        textButton.setIconSize(6.0f);
        textButton.setUseAlternateFont(true);
        textButton.setUppercase(true);
        textButton.setDeriveTextColorFromBackground(true);
        textButton.setUseThemeBackground(true);
        textButton.addClickListener(this::beginAddFriend);
        this.getSidebarHeader().h(new SpacerComponent(this.getSidebarHeader().A() - this.getSidebarHeader().l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().C() - textButton.A() - 1.0, 0.0), new Object[0]);
        this.getSidebarHeader().h(new PaddedComponent(0.0, 0.0, 0.0, 8.0, textButton), new Object[0]);
        this.getSidebarContent().removeMarkedChildren();
        this.getSidebarContent().h(new SpacerComponent(0.0, 3.0), new Object[0]);
        this.friendsList = this.createFlowLayout(this.getSidebarContent().A(), this.getSidebarContent().L() - this.getSidebarContent().l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().y() - 21.0);
        this.friendsList.F(FrameScrollbarPlacement.OUTSIDE);
        this.friendsList.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.friendsList.t(this.friendsList.L());
        this.friendsList.setShowDisabledOverlay(false);
        this.friendsList.E(true);
        this.friendsList.h(new SpacerComponent(0.0, 1.0), new Object[0]);
        this.getSidebarContent().h(this.friendsList, new Object[0]);
        this.populateFriendList();
    }

    private void populateFriendList() {
        if (this.friendsList == null) {
            return;
        }
        this.friendsList.removeMarkedChildren();
        this.friendsList.h(new SpacerComponent(0.0, 1.0), new Object[0]);
        for (FriendEntry friendEntry : Umbra.INSTANCE.getFriendManager().getFriends()) {
            ClickGuiFriendCardComponent clickGuiFriendCardComponent = new ClickGuiFriendCardComponent(friendEntry);
            clickGuiFriendCardComponent.o(this.friendsList.A());
            clickGuiFriendCardComponent.Y(20.0);
            clickGuiFriendCardComponent.setSelected(friendEntry == this.selectedFriend);
            clickGuiFriendCardComponent.setSelectAction(() -> this.selectFriend(friendEntry));
            clickGuiFriendCardComponent.setRemoveAction(() -> this.removeFriend(friendEntry));
            this.friendsList.h(new PaddedComponent(0.0, 3.0, 0.0, 0.0, clickGuiFriendCardComponent), new Object[0]);
        }
        this.friendsList.l$src$V$1mibm4x();
    }

    private void selectFriend(FriendEntry friendEntry) {
        this.addingFriend = false;
        this.selectedFriend = friendEntry;
        this.renderMainContent();
        this.populateFriendList();
    }

    private void removeFriend(FriendEntry friendEntry) {
        Umbra.INSTANCE.getFriendManager().removeFriend(friendEntry);
        if (this.selectedFriend == friendEntry) {
            this.selectedFriend = null;
        }
        Umbra.INSTANCE.getNotificationManager().showInfo("\u00a7cRemoved\u00a7r " + friendEntry.getName() + " from friends", "", 2000L);
        this.renderMainContent();
        this.populateFriendList();
    }

    private void beginAddFriend() {
        if (this.addingFriend) {
            return;
        }
        this.addingFriend = true;
        this.selectedFriend = null;
        this.nameInput = null;
        this.aliasInput = null;
        this.addButton = null;
        this.cancelButton = null;
        this.renderMainContent();
        this.populateFriendList();
    }

    private void cancelAddFriend() {
        this.addingFriend = false;
        this.nameInput = null;
        this.aliasInput = null;
        this.addButton = null;
        this.cancelButton = null;
        this.renderMainContent();
        this.populateFriendList();
    }

    private void confirmAddFriend() {
        if (this.nameInput == null || !this.nameInput.hasNonBlankText()) {
            if (this.nameInput != null) {
                this.nameInput.setText("");
            }
            return;
        }
        String string = this.nameInput.getText().trim();
        String string2 = this.aliasInput != null && this.aliasInput.hasNonBlankText() ? this.aliasInput.getText().trim() : string;
        Umbra.INSTANCE.getFriendManager().addFriend(new Friend(string, string2));
        Umbra.INSTANCE.getNotificationManager().showInfo("\u00a7aAdded\u00a7r " + string + " to friends", "", 2000L);
        this.addingFriend = false;
        this.selectedFriend = null;
        this.nameInput = null;
        this.aliasInput = null;
        this.addButton = null;
        this.cancelButton = null;
        this.renderMainContent();
        this.populateFriendList();
    }

    private void saveAlias() {
        if (this.selectedFriend instanceof Friend && this.aliasInput != null) {
            String string = this.aliasInput.getText().trim();
            ((Friend)this.selectedFriend).setAlias(string.isEmpty() ? this.selectedFriend.getName() : string);
            Umbra.INSTANCE.getFriendManager().refreshPlayerNames();
        }
        this.renderMainContent();
        this.populateFriendList();
    }

    private void updateAddButtonState() {
        if (this.addButton == null || this.nameInput == null) {
            return;
        }
        this.addButton.setInteractionDisabled(!this.nameInput.hasNonBlankText());
    }

    private SmallTextInputComponent createInput(String string) {
        SmallTextInputComponent smallTextInputComponent = new SmallTextInputComponent(string);
        smallTextInputComponent.setRightInset(0.0f);
        smallTextInputComponent.setHorizontalInset(0.0);
        smallTextInputComponent.setLeftInset(0.0f);
        smallTextInputComponent.setVerticalInset(0.0f);
        smallTextInputComponent.setUseExplicitHeight(true);
        smallTextInputComponent.Y(18.0);
        smallTextInputComponent.setMaxLength(32);
        return smallTextInputComponent;
    }

    private void renderMainContent() {
        this.getMainContent().removeMarkedChildren();
        this.mainPanel = new PanelComponent(this.getMainContainer().A(), this.getMainContent().L());
        this.mainPanel.setDisabledOverlayColor(J.m);
        this.mainPanel.setCornerRadius(3.0f);
        this.mainPanel.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        double d = 8.0;
        if (this.addingFriend) {
            this.renderAddFriend(d);
        } else if (this.selectedFriend != null) {
            this.renderFriendDetails(d);
        } else {
            this.renderEmptyState(d);
        }
        this.getMainContent().h(this.mainPanel, new Object[0]);
    }

    private void renderAddFriend(double d) {
        double d2 = this.mainPanel.A() - d * 2.0;
        this.nameInput = this.createInput("Player name");
        this.nameInput.o(d2);
        this.aliasInput = this.createInput("Alias (optional)");
        this.aliasInput.o(d2);
        this.mainPanel.h(new PaddedComponent(d, d, d, d, this.nameInput), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 4.0), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, d, d, d, this.aliasInput), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 4.0), new Object[0]);
        PanelComponent panelComponent = new PanelComponent(d2, 14.0);
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.cancelButton = new TextButton("CANCEL", 0.625, J.i, J.i.brighter(), null, 2.0f, 1.0f, 35.5, 14.0);
        this.cancelButton.setUppercase(true);
        this.cancelButton.setUseAlternateFont(true);
        this.cancelButton.setDeriveTextColorFromBackground(false);
        this.cancelButton.setNormalTextColor(J.A);
        this.cancelButton.addClickListener(this::cancelAddFriend);
        this.addButton = new TextButton("ADD", 0.625, J.B, J.B.brighter(), null, 2.0f, 1.0f, 27.5, 14.0);
        this.addButton.setUppercase(true);
        this.addButton.setUseAlternateFont(true);
        this.addButton.setDeriveTextColorFromBackground(false);
        this.addButton.setNormalTextColor(J.A);
        this.addButton.setInteractionDisabled(true);
        this.addButton.addClickListener(this::confirmAddFriend);
        double d3 = this.cancelButton.A() + 4.0 + this.addButton.A();
        panelComponent.h(new SpacerComponent(panelComponent.A() - d3, 0.0), new Object[0]);
        panelComponent.h(new PaddedComponent(0.0, 0.0, 0.0, 4.0, this.cancelButton), new Object[0]);
        panelComponent.h(this.addButton, new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, d, d, 4.0, panelComponent), new Object[0]);
        this.nameInput.addKeyTypedListener(this::handleNameInputChanged);
        this.updateAddButtonState();
        this.nameInput.requestFocus();
    }

    private void renderFriendDetails(double d) {
        double d2 = this.mainPanel.A() - d * 2.0;
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent(this.selectedFriend.getName(), 1.0, J.A, true);
        this.mainPanel.h(new PaddedComponent(d, d, d, d, simpleTextLabelComponent), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 6.0), new Object[0]);
        String string = this.selectedFriend.getAlias();
        if (string == null || string.trim().isEmpty()) {
            string = this.selectedFriend.getName();
        }
        this.aliasInput = this.createInput("Alias");
        this.aliasInput.setText(string);
        this.aliasInput.o(d2);
        this.mainPanel.h(new PaddedComponent(d, d, d, d, this.aliasInput), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 4.0), new Object[0]);
        PanelComponent panelComponent = new PanelComponent(d2, 14.0);
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("widthwrap");
        this.saveAliasButton = new TextButton("SAVE ALIAS", 0.625, J.B, J.B.brighter(), null, 2.0f, 1.0f, 52.0, 14.0);
        this.saveAliasButton.setUppercase(true);
        this.saveAliasButton.setUseAlternateFont(true);
        this.saveAliasButton.setDeriveTextColorFromBackground(false);
        this.saveAliasButton.setNormalTextColor(J.A);
        this.saveAliasButton.addClickListener(this::saveAlias);
        panelComponent.h(new SpacerComponent(panelComponent.A() - this.saveAliasButton.A(), 0.0), new Object[0]);
        panelComponent.h(this.saveAliasButton, new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, d, d, 4.0, panelComponent), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 6.0), new Object[0]);
        this.removeButton = new TextButton("REMOVE FRIEND", 0.625, J.d, J.d.brighter(), null, 2.0f, 1.0f, 72.0, 14.0);
        this.removeButton.setIconResource("newtrash");
        this.removeButton.setIconSize(6.0f);
        this.removeButton.setUppercase(true);
        this.removeButton.setUseAlternateFont(true);
        this.removeButton.setDeriveTextColorFromBackground(false);
        this.removeButton.setNormalTextColor(J.A);
        this.removeButton.addClickListener(() -> this.removeFriend(this.selectedFriend));
        this.mainPanel.h(new PaddedComponent(d, d, d, d, this.removeButton), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 10.0), new Object[0]);
        SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent("This player will not be targeted by", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("combat modules: AimAssist, KillAura,", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent4 = new SimpleTextLabelComponent("SilentCombat, ProjectileAim and AntiBot.", 0.625, J.h, false);
        this.mainPanel.h(new PaddedComponent(d, d, d, 0.0, simpleTextLabelComponent2), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, 0.0, d, 0.0, simpleTextLabelComponent3), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, 0.0, d, 0.0, simpleTextLabelComponent4), new Object[0]);
    }

    private void renderEmptyState(double d) {
        double d2 = this.mainPanel.A() - d * 2.0;
        SimpleTextLabelComponent simpleTextLabelComponent = new SimpleTextLabelComponent("No friend selected", 1.0, J.A, true);
        this.mainPanel.h(new PaddedComponent(d, d, d, d, simpleTextLabelComponent), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 8.0), new Object[0]);
        SimpleTextLabelComponent simpleTextLabelComponent2 = new SimpleTextLabelComponent("Add a friend with the \"ADD FRIEND\" button", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent3 = new SimpleTextLabelComponent("on the left, or use the \"Add friend bind\"", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent4 = new SimpleTextLabelComponent("from Settings while looking at a player.", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent5 = new SimpleTextLabelComponent("Friends are excluded from combat modules", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent6 = new SimpleTextLabelComponent("(AimAssist, KillAura, SilentCombat, ProjectileAim)", 0.625, J.h, false);
        SimpleTextLabelComponent simpleTextLabelComponent7 = new SimpleTextLabelComponent("and get recolored in render modules.", 0.625, J.h, false);
        this.mainPanel.h(new PaddedComponent(d, d, d, 0.0, simpleTextLabelComponent2), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, 0.0, d, 0.0, simpleTextLabelComponent3), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, 0.0, d, 0.0, simpleTextLabelComponent4), new Object[0]);
        this.mainPanel.h(new SpacerComponent(0.0, 8.0), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, d, d, 0.0, simpleTextLabelComponent5), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, 0.0, d, 0.0, simpleTextLabelComponent6), new Object[0]);
        this.mainPanel.h(new PaddedComponent(d, 0.0, d, 0.0, simpleTextLabelComponent7), new Object[0]);
    }

    private void handleNameInputChanged(char c, int n) {
        this.updateAddButtonState();
    }

    @Override
    public void Z$src$V$15w0jcm() {
        super.Z$src$V$15w0jcm();
        this.populateFriendList();
        this.renderMainContent();
    }
}
