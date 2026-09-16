package gg.umbra.ui.click.frame.impl.target;

import gg.umbra.value.ToggleSetting;

public class TargetInfoSettings {
    public final ToggleSetting potsUsedComparator;
    public final ToggleSetting damageComparator;
    public final ToggleSetting showHovered = ToggleSetting.create(this, "Show Hovered", true, "Show information on a hovered entity if not attacking.");
    public final ToggleSetting hitsComparator;
    public final ToggleSetting comboCounter;

    public TargetInfoSettings() {
        this.damageComparator = ToggleSetting.create(this, "Damage Comparator", true, "Measures strength of target compared to yourself\nConsiders armor and weapon damage");
        this.comboCounter = ToggleSetting.create(this, "Combo Counter", true, "Shows how many hits in a direct row you've landed, or taken, from target");
        this.hitsComparator = ToggleSetting.create(this, "Hits Comparator", false, "Measures how many hits you've landed compared to target");
        this.potsUsedComparator = ToggleSetting.create(this, "Pots Used Comparator", false, "Measures how many heal pots you've used compared to target");
    }
}
