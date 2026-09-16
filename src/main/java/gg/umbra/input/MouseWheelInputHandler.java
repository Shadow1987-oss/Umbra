package gg.umbra.input;

import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.InputEventHandler;
import gg.umbra.input.Win32InputConstants;

public class MouseWheelInputHandler
implements InputEventHandler {
    @Override
    public boolean handle(long packedWheelMetadata, long secondArgument) {
        short scrollDelta = Win32InputConstants.extractHighWord(packedWheelMetadata);
        return InputEventDispatcher.getInstance().getMouseState().setScrollDelta(scrollDelta);
    }
}
