package gg.umbra.macros;

import gg.umbra.macros.CommandMacro;
import gg.umbra.macros.MacroAction;
import gg.umbra.wrapper.impl.Minecraft;

class CommandMacroAction
implements MacroAction {
    private final CommandMacro macro;

    CommandMacroAction(CommandMacro commandMacro) {
        this.macro = commandMacro;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void tick() {
        Minecraft.a_xH_J().sendChatMessage(this.macro.getName());
    }
}

