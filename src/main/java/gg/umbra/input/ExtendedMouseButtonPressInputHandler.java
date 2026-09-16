package gg.umbra.input;

import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.InputEventHandler;
import gg.umbra.input.KeyboardCodeUtil;

public class ExtendedMouseButtonPressInputHandler
implements InputEventHandler {
    @Override
    public boolean handle(long buttonMetadata, long secondArgument) {
        return InputEventDispatcher.getInstance().getMouseState().setButtonState(KeyboardCodeUtil.decodeExtendedMouseButton(buttonMetadata), true);
    }
}
