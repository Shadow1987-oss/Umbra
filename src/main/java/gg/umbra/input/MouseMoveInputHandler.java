package gg.umbra.input;

import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.InputEventHandler;
import gg.umbra.input.Win32InputConstants;

public class MouseMoveInputHandler
implements InputEventHandler {
    @Override
    public boolean handle(long firstArgument, long packedCoordinates) {
        short mouseX = Win32InputConstants.extractLowWord(packedCoordinates);
        short mouseY = Win32InputConstants.extractHighWord(packedCoordinates);
        return InputEventDispatcher.getInstance().getMouseState().updateCursorPosition(mouseX, mouseY);
    }
}
