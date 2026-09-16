package gg.umbra.tutorial.selector;

import gg.umbra.tutorial.TutorialTargetSelector;
import gg.umbra.tutorial.page.ModulesTutorialPage;
import gg.umbra.ui.click.frame.ModuleCategoryNavigationButtonComponent;

public class ModulesTutorialCategoryButtonSelector
extends TutorialTargetSelector<ModuleCategoryNavigationButtonComponent> {
    private static final String targetCategoryName = "Combat";
    private final ModulesTutorialPage tutorialPage;

    private boolean matchesCategory(ModuleCategoryNavigationButtonComponent moduleCategoryNavigationButtonComponent) {
        return moduleCategoryNavigationButtonComponent.N$src$Ljava_lang_String_$wy122q().equals(targetCategoryName);
    }

    @Override
    public boolean matches(ModuleCategoryNavigationButtonComponent moduleCategoryNavigationButtonComponent) {
        return this.matchesCategory(moduleCategoryNavigationButtonComponent);
    }

    public ModulesTutorialCategoryButtonSelector(ModulesTutorialPage modulesTutorialPage, Class clazz) {
        super(clazz);
        this.tutorialPage = modulesTutorialPage;
    }
}
