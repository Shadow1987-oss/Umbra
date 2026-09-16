package gg.umbra.input;

import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.InputEventHandler;
import gg.umbra.input.KeyboardCodeUtil;

public class ExtendedMouseButtonReleaseInputHandler
implements InputEventHandler {
    private static boolean legacyState;

    public static void setLegacyState(boolean state) {
        legacyState = state;
    }

    @Override
    public boolean handle(long buttonMetadata, long secondArgument) {
        return InputEventDispatcher.getInstance().getMouseState().setButtonState(KeyboardCodeUtil.decodeExtendedMouseButton(buttonMetadata), false);
    }


    public static boolean getLegacySentinelResult() {
        boolean state = ExtendedMouseButtonReleaseInputHandler.getLegacyState();
        return false;
    }

    public static boolean getLegacyState() {
        return legacyState;
    }

    static {
        if (!ExtendedMouseButtonReleaseInputHandler.getLegacyState()) {
            ExtendedMouseButtonReleaseInputHandler.setLegacyState(true);
        }
    }
}

