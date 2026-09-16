package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseButton;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.frame.impl.profile.ProfileListEntryComponent;
import java.awt.Point;

class ProfileListEntryMouseForwardingListener
implements GuiMouseListener {
    private final ProfileListEntryComponent entry;

    @Override
    public void g(Point point, MouseClickButton button) {
        MouseButton action = button == MouseClickButton.LEFT_CLICK
            ? MouseButton.LEFT_CLICK
            : button == MouseClickButton.RIGHT_CLICK
                ? MouseButton.RIGHT_CLICK
                : button == MouseClickButton.MIDDLE_CLICK ? MouseButton.MIDDLE_CLICK : MouseButton.UNKNOWN;
        this.entry.g(new GuiMouseEvent(point.x, point.y, action));
    }


    ProfileListEntryMouseForwardingListener(ProfileListEntryComponent entry) {
        this.entry = entry;
    }
}

