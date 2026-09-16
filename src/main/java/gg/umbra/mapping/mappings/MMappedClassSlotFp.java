package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.ui.click.component.GuiComponent;

public class MMappedClassSlotFp
extends Mapping {
    private static GuiComponent[] g;

    public MMappedClassSlotFp() {
        super(MappedClasses.Fp);
    }

    public static void k(GuiComponent[] guiComponentArray) {
        g = guiComponentArray;
    }

    public static GuiComponent[] l() {
        return g;
    }

    static {
        if (MMappedClassSlotFp.l() == null) {
            MMappedClassSlotFp.k(new GuiComponent[1]);
        }
    }
}

