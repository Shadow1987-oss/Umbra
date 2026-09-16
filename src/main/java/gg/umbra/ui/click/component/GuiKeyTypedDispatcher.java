package gg.umbra.ui.click.component;

import gg.umbra.Umbra;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiKeyTypedListener;

public class GuiKeyTypedDispatcher {
    public static void dispatch(char character, int keyCode) {
        ClientSettings clientSettings = Umbra.INSTANCE.getHackManager().getMod(ClientSettings.class);
        if (ClientSettings.activeComponent != null) {
            for (GuiKeyTypedListener keyTypedListener : ClientSettings.activeComponent.getKeyTypedListeners()) {
                keyTypedListener.onKeyTyped(character, keyCode);
            }
        }
    }
}
