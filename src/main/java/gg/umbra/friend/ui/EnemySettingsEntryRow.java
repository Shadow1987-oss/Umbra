/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.friend.ui;

import gg.umbra.friend.Enemy;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.SelectableTextRowComponent;

public class EnemySettingsEntryRow
extends SelectableTextRowComponent {
    private final Enemy enemy;

    @Override
    public boolean isSelected() {
        return !this.enemy.isExclusive();
    }

    public Enemy getEnemy() {
        return this.enemy;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        this.enemy.setExclusive(!this.enemy.isExclusive());
    }

    @Override
    public void H() {
        String text = this.enemy.getName();
        if (!this.enemy.getDisplayName().equals(this.enemy.getName()) && !this.isHovered()) {
            text = "*" + this.enemy.getAlias();
        }
        this.setText(text);
        super.H();
    }

    public EnemySettingsEntryRow(Enemy enemy) {
        super(EnemySettingsEntryRow.J.d, enemy.getName());
        this.enemy = enemy;
    }
}

