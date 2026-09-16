package gg.umbra.macros;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventKeyPress;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Macro;
import gg.umbra.macros.MacroAction;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.List;

public class MacroEventListener
implements EventListener {
    private MacroAction activeAction;

    private boolean startMacro(Macro macro) {
        MacroAction nextAction = macro.createAction();
        if (nextAction == null) {
            return false;
        }
        if (this.activeAction != null) {
            this.activeAction.cancel();
            nextAction.inheritState(this.activeAction);
        }
        this.activeAction = nextAction;
        return true;
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (this.activeAction == null) {
            return;
        }
        this.activeAction.tick();
        if (this.activeAction.isFinished()) {
            this.activeAction = null;
        }
    }


    @Listen
    public void onMouseButton(EventMouseButton event) {
        if (event.getButtonState()) {
            int binding = -100 + event.getButton();
            List<Macro> macros = Umbra.INSTANCE.getMacrosManager().getMacros(binding);
            for (Macro macro : macros) {
                if (macro.activateIfMatched(binding) && this.startMacro(macro)) {
                    break;
                }
            }
        }
    }

    @Listen
    public void onKeyPress(EventKeyPress event) {
        if (event.isDown()) {
            return;
        }
        if (event.getThePlayer().isNull()) {
            return;
        }
        if (Minecraft.a_pt_1_w().isInstance(MappedClasses.qo)) {
            return;
        }
        int binding = event.getKey();
        List<Macro> macros = Umbra.INSTANCE.getMacrosManager().getMacros(binding);
        for (Macro macro : macros) {
            if (macro.activateIfMatched(binding) && this.startMacro(macro)) {
                break;
            }
        }
    }
}

