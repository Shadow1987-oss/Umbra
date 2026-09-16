package gg.umbra.unmap;

import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventKeyPress;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.unmap.Bendable;
import java.util.ArrayList;

public class BendableInputDispatcher
implements EventListener {
    private static final ArrayList<Bendable> registeredBindings = new ArrayList();
    private static GuiComponent[] legacyComponents;

    @Listen
    public void onKeyPress(EventKeyPress event) {
        if (!event.isDown()) {
            return;
        }
        for (Bendable binding : registeredBindings) {
            binding.activateIfMatched(event.getKey());
        }
    }

    public static void register(Bendable binding) {
        registeredBindings.add(binding);
    }

    @Listen
    public void onMouseButton(EventMouseButton event) {
        if (!event.getButtonState()) {
            return;
        }
        for (Bendable binding : registeredBindings) {
            binding.activateIfMatched(-100 + event.getButton());
        }
    }

    public static GuiComponent[] getLegacyComponents() {
        return legacyComponents;
    }

    public static void setLegacyComponents(GuiComponent[] components) {
        legacyComponents = components;
    }


    static {
        BendableInputDispatcher.setLegacyComponents(new GuiComponent[1]);
    }
}

