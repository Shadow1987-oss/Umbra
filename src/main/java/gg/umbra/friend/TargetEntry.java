/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.friend;

import gg.umbra.friend.TargetType;

public class TargetEntry {
    private static int[] obfuscationState;
    private final TargetType type;

    public TargetType getType() {
        return this.type;
    }

    public static void setTargetObfuscationState(int[] state) {
        obfuscationState = state;
    }

    public static int[] getTargetObfuscationState() {
        return obfuscationState;
    }

    public TargetEntry(TargetType targetType) {
        this.type = targetType;
    }

    static {
        if (TargetEntry.getTargetObfuscationState() == null) {
            TargetEntry.setTargetObfuscationState(new int[4]);
        }
    }
}

