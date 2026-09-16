package gg.umbra.ui.click.frame.impl;

import gg.umbra.Umbra;
import gg.umbra.manager.client.FriendManager;
import gg.umbra.ui.click.component.ColorDividerComponent;
import gg.umbra.ui.click.component.DropdownSelectComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.InsetFilledSpacerComponent;
import gg.umbra.ui.click.component.SpacerComponent;
import gg.umbra.ui.click.component.input.BindValueRowComponent;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import gg.umbra.ui.click.component.value.ColorPickerEditorComponent;
import gg.umbra.ui.click.frame.impl.ThemeComponentGroupKey;
import gg.umbra.ui.theme.ThemeColors;
import java.util.LinkedHashMap;

public final class ThemeComponentGroupFactory {
    public static GuiComponent[] k(ThemeColors themeColors) {
        return new GuiComponent[]{new ColorDividerComponent(themeColors.i)};
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public static GuiComponent[] E(ThemeColors themeColors) {
        return new GuiComponent[0];
    }

    public static LinkedHashMap<ThemeComponentGroupKey, GuiComponent[]> R(ThemeColors themeColors) {
        FriendManager friendManager = Umbra.INSTANCE.getFriendManager();
        LinkedHashMap<ThemeComponentGroupKey, GuiComponent[]> linkedHashMap = new LinkedHashMap<ThemeComponentGroupKey, GuiComponent[]>();
        linkedHashMap.put(new ThemeComponentGroupKey("Friend Settings", "newfriends"), new GuiComponent[]{new BooleanToggleComponent(friendManager.recolorVisuals), new ColorPickerEditorComponent(friendManager.friendColor), new BooleanToggleComponent(friendManager.useFriends), new BooleanToggleComponent(friendManager.useAlias), new BooleanToggleComponent(friendManager.spoofAlias), new BindValueRowComponent(Umbra.INSTANCE.getClientSettings().addFriendBind), new InsetFilledSpacerComponent(90.0, 2.0, 0.5, 4.0, themeColors.l), new SpacerComponent(1.0, 2.0)});
        return linkedHashMap;
    }

    private ThemeComponentGroupFactory() {
    }
}
