package gg.umbra.macros;

import gg.umbra.module.Macro;
import gg.umbra.macros.CommandMacroAction;
import gg.umbra.macros.MacroAction;

public class CommandMacro
extends Macro {
    @Override
    public MacroAction createAction() {
        return new CommandMacroAction(this);
    }

    public CommandMacro(String name) {
        super(name);
    }
}

