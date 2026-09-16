/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.friend.ui;

import gg.umbra.Umbra;
import gg.umbra.friend.Enemy;
import gg.umbra.friend.ui.EnemySettingsAddEnemyInputComponent;
import gg.umbra.friend.ui.EnemySettingsEntryRow;
import gg.umbra.friend.ui.EnemySettingsFrameToggleHeaderComponent;
import gg.umbra.friend.ui.EnemySettingsRemoveEntryClickHandler;
import gg.umbra.ui.click.component.ColorDividerComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import gg.umbra.ui.click.component.value.ColorPickerEditorComponent;
import gg.umbra.ui.click.frame.CollapsibleFrame;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.frame.FrameHeaderComponent;

public class EnemySettingsFrame
extends Frame
implements CollapsibleFrame {
    private BooleanToggleComponent spoofAliasToggle;
    private BooleanToggleComponent recolorVisualsToggle;
    private ColorPickerEditorComponent enemyColorEditor;
    private BooleanToggleComponent useAliasToggle;
    private ColorDividerComponent colorDivider;
    private BooleanToggleComponent useEnemiesToggle;
    private boolean expanded = true;

    static ColorPickerEditorComponent getEnemyColorEditor(EnemySettingsFrame frame) {
        return frame.enemyColorEditor;
    }

    @Override
    public void v() {
    }

    @Override
    public boolean q() {
        return this.expanded;
    }

    public EnemySettingsFrame() {
        this.colorDivider = new ColorDividerComponent(EnemySettingsFrame.J.l);
        this.setDisabledOverlayColor(EnemySettingsFrame.J.i);
        this.K(300.0);
        this.S(100.0);
        this.setVisible(false);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.Y(new EnemySettingsFrameToggleHeaderComponent(this, this, "newfriends", "Enemies"));
        this.enemyColorEditor = new ColorPickerEditorComponent(Umbra.INSTANCE.getEnemyManager().enemyColor);
        this.recolorVisualsToggle = new BooleanToggleComponent(Umbra.INSTANCE.getFriendManager().recolorVisuals);
        this.useEnemiesToggle = new BooleanToggleComponent(Umbra.INSTANCE.getEnemyManager().useEnemies);
        this.useAliasToggle = new BooleanToggleComponent(Umbra.INSTANCE.getFriendManager().useAlias);
        this.spoofAliasToggle = new BooleanToggleComponent(Umbra.INSTANCE.getFriendManager().spoofAlias);
        this.enemyColorEditor.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.recolorVisualsToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.useEnemiesToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.useAliasToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.spoofAliasToggle.setDisabledOverlayColor(EnemySettingsFrame.J.r);
        this.enemyColorEditor.setVisible(false);
        this.recolorVisualsToggle.setVisible(false);
        this.useEnemiesToggle.setVisible(false);
        this.useAliasToggle.setVisible(false);
        this.spoofAliasToggle.setVisible(false);
        this.colorDivider.setVisible(false);
    }

    public void refreshEntries() {
        this.removeMarkedChildren();
        this.addChildren(this.enemyColorEditor, this.recolorVisualsToggle, this.useEnemiesToggle, this.useAliasToggle, this.spoofAliasToggle, this.colorDivider);
        this.h(new EnemySettingsAddEnemyInputComponent("Username / Alias"), new Object[0]);
        for (Enemy enemy : Umbra.INSTANCE.getEnemyManager().getEnemies()) {
            this.h(new EnemySettingsEntryRow(enemy).setDeleteActionListener(new EnemySettingsRemoveEntryClickHandler(this, enemy)), new Object[0]);
        }
        this.l$src$V$1mibm4x();
    }

    static BooleanToggleComponent getUseEnemiesToggle(EnemySettingsFrame frame) {
        return frame.useEnemiesToggle;
    }

    @Override
    public void Y() {
    }

    @Override
    public void w() {
        this.expanded = !this.expanded;
        for (GuiComponent guiComponent : this.f()) {
            if (guiComponent instanceof FrameHeaderComponent) continue;
            guiComponent.setVisible(this.expanded);
        }
        this.l$src$V$1mibm4x();
    }

    static BooleanToggleComponent getRecolorVisualsToggle(EnemySettingsFrame frame) {
        return frame.recolorVisualsToggle;
    }

    static ColorDividerComponent getColorDivider(EnemySettingsFrame frame) {
        return frame.colorDivider;
    }

    static BooleanToggleComponent getSpoofAliasToggle(EnemySettingsFrame frame) {
        return frame.spoofAliasToggle;
    }

    static BooleanToggleComponent getUseAliasToggle(EnemySettingsFrame frame) {
        return frame.useAliasToggle;
    }

    @Override
    public String getName() {
        return "Enemies";
    }
}

