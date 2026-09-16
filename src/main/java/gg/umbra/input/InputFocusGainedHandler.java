package gg.umbra.input;

import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.InputEventHandler;

public class InputFocusGainedHandler
implements InputEventHandler {
    @Override
    public boolean handle(long windowHandle, long focusState) {
        InputEventDispatcher.getInstance().getFocusState().markFocused();
        return false;
    }
}
