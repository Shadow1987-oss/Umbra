package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import java.util.ArrayList;
import java.util.List;

public class ProfilesSettingsFrameState {
    public static List<GuiComponent> F(boolean bl) {
        ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
        arrayList.add(new BooleanToggleComponent(Umbra.INSTANCE.getPublicProfileSettings().autoLoadModuleStates));
        if (!bl) {
            arrayList.add(new BooleanToggleComponent(Umbra.INSTANCE.getPublicProfileSettings().framePositionsPerProfile));
        }
        return arrayList;
    }

}

