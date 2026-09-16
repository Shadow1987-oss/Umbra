package gg.umbra.config;

import gg.umbra.Umbra;
import gg.umbra.config.ModuleProfileMetadataCodec;
import gg.umbra.config.Profile;
import gg.umbra.manager.HackManager;
import gg.umbra.module.HackModule;
import gg.umbra.ui.click.component.GuiComponent;
import java.util.ArrayList;

public abstract class BuiltinProfile
extends Profile {
    private static final String CLIENT_VERSION;
    private static GuiComponent[] sharedGuiComponents;

    private static void clearSelectedModules() {
        ModuleProfileMetadataCodec metadataCodec = Umbra.INSTANCE.getModuleProfileMetadataCodec();
        for (HackModule module : new ArrayList<HackModule>(metadataCodec.getSelectedModules())) {
            metadataCodec.removeModule(module);
        }
    }

    public static GuiComponent[] getSharedGuiComponents() {
        return sharedGuiComponents;
    }

    public final BuiltinProfile applyPreset() {
        BuiltinProfile.clearSelectedModules();
        this.configureModules();
        this.captureCurrentState();
        return this;
    }

    protected final void selectModule(Class<? extends HackModule> moduleClass) {
        HackManager modManager = Umbra.INSTANCE.getHackManager();
        HackModule module = modManager.getMod(moduleClass);
        if (module == null) {
            return;
        }
        Umbra.INSTANCE.getModuleProfileMetadataCodec().addModule(module);
    }

    protected abstract void configureModules();

    protected BuiltinProfile(String name) {
        super(name, CLIENT_VERSION);
    }

    public abstract boolean isApplicable();


    static {
        BuiltinProfile.setSharedGuiComponents(null);
        CLIENT_VERSION = "1.0.0";
    }

    public static void setSharedGuiComponents(GuiComponent[] components) {
        sharedGuiComponents = components;
    }
}

