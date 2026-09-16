package gg.umbra.module;

import gg.umbra.module.Category;
import gg.umbra.module.HackModule;

public class UtilityHack
extends HackModule {
    public UtilityHack(String name) {
        super(name, 0, 0, Category.UTILITY, "");
    }

    public UtilityHack(String name, String description) {
        super(name, 0, 0, Category.UTILITY, description);
    }

    @Override
    public boolean isRequiresBind() {
        return true;
    }

    public UtilityHack(String name, Category category, String description) {
        super(name, 0, 0, category, description);
    }
}

