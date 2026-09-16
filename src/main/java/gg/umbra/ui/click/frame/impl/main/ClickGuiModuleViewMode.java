package gg.umbra.ui.click.frame.impl.main;

public enum ClickGuiModuleViewMode {
    MODULE_CATEGORY,
    MACROS,
    LEGIT,
    DASHBOARD;

    private static final ClickGuiModuleViewMode[] b;

    static {
        String[] stringArray = new String[]{"MODULE_CATEGORY", "MACROS", "LEGIT", "DASHBOARD"};



        b = new ClickGuiModuleViewMode[]{MODULE_CATEGORY, MACROS, LEGIT, DASHBOARD};
    }

}
