package gg.umbra.value;

import gg.umbra.Umbra;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.value.AbstractListValueSuggestionProvider;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class ModuleNameSuggestionProvider
extends AbstractListValueSuggestionProvider {
    private List<String> cachedModuleNames;
    private boolean excludeOtherCategory;
    private static GuiComponent[] legacyComponents;

    public ModuleNameSuggestionProvider(boolean excludeOtherCategory) {
        this.excludeOtherCategory = excludeOtherCategory;
    }

    public static GuiComponent[] getLegacyComponents() {
        return legacyComponents;
    }

    public static void setLegacyComponents(GuiComponent[] components) {
        legacyComponents = components;
    }

    public ModuleNameSuggestionProvider() {
        this(false);
    }

    @Override
    public @UnmodifiableView List<String> getValues() {
        if (this.cachedModuleNames == null) {
            this.cachedModuleNames = new ArrayList<String>();
            for (HackModule module : Umbra.INSTANCE.getHackManager().collectMods()) {
                if (module.getCategory() == Category.NONE || this.excludeOtherCategory && module.getCategory() == Category.OTHER) continue;
                this.cachedModuleNames.add(module.getName());
            }
        }
        return this.cachedModuleNames;
    }


    static {
        if (ModuleNameSuggestionProvider.getLegacyComponents() != null) {
            ModuleNameSuggestionProvider.setLegacyComponents(new GuiComponent[3]);
        }
    }
}

