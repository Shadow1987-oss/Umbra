/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.friend.ui;

import gg.umbra.Umbra;
import gg.umbra.friend.Enemy;
import gg.umbra.friend.ui.EnemySettingsFrame;
import gg.umbra.ui.click.component.GuiClickListener;

class EnemySettingsRemoveEntryClickHandler
implements GuiClickListener {
    final Enemy enemy;
    final EnemySettingsFrame frame;

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.saveAndStop();
        Umbra.INSTANCE.getEnemyManager().removeEnemy(this.enemy);
        this.frame.refreshEntries();
    }

    EnemySettingsRemoveEntryClickHandler(EnemySettingsFrame enemySettingsFrame, Enemy enemy) {
        this.frame = enemySettingsFrame;
        this.enemy = enemy;
    }
}

