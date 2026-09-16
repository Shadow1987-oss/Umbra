package gg.umbra.ui.click.frame.impl;

import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.config.PublicProfileSettings;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.ColorDividerComponent;
import gg.umbra.ui.click.component.DropdownSelectComponent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.input.BindValueRowComponent;
import gg.umbra.ui.click.component.module.ModuleComponent;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import gg.umbra.ui.click.component.value.ClientSettingsColorPickerEditorComponent;
import gg.umbra.ui.click.component.value.ClientSettingsEntityCheckDependentToggleComponent;
import gg.umbra.ui.click.component.value.ClientSettingsPrimaryBooleanToggle;
import gg.umbra.ui.click.component.value.ClientSettingsSecondaryBooleanToggle;
import gg.umbra.ui.click.component.value.ClientSettingsTertiaryBooleanToggle;
import gg.umbra.ui.click.component.value.ClientSettingsThemeBooleanToggle;
import gg.umbra.ui.click.component.value.NumberSliderComponent;
import gg.umbra.ui.click.component.value.PublicProfileModeDropdownComponent;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.text.SuffixTextTruncationIndexCache;
import gg.umbra.ui.click.text.TextTruncationIndexCache;
import gg.umbra.ui.font.FontManager;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.ui.theme.ThemeColors;
import gg.umbra.utils.render.ItemIconRenderer;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.I18n;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Potion;
import gg.umbra.wrapper.impl.StatusEffect;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ClientSettingsComponentFactory {
    private static void lambda$createSubmenuComponents$14() {
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().c(i);
            if (itemStack.isNull()) continue;
            Umbra.debugLog("Display Name: " + itemStack.x() + " ID: " + Item.f(itemStack.getItem()) + " Slot: " + i);
        }
    }

    private static void lambda$createMainChildren$9() {
        Umbra.debugLog("Saving settings...");
        Umbra.INSTANCE.saveAndStop();
    }

    private static GuiComponent[] W(ClientSettings clientSettings, boolean bl) {
        ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
        arrayList.add(new BooleanToggleComponent(clientSettings.blurBackground));
        arrayList.add(new BooleanToggleComponent(clientSettings.guiBindIndicator));
        arrayList.add(new BooleanToggleComponent(clientSettings.showTooltips));
        if (!bl) {
            arrayList.add(new BooleanToggleComponent(clientSettings.showLegitMode));
        }
        arrayList.add(new NumberSliderComponent(clientSettings.rainbowSpeed));
        arrayList.add(new DropdownSelectComponent(Umbra.INSTANCE.getPublicProfileSettings().guiStyle));
        if (bl) {
            arrayList.add(new BooleanToggleComponent(clientSettings.showEnabledCount));
        }
        arrayList.add(new DropdownSelectComponent(Umbra.INSTANCE.getClientSettings().guiScale));
        if (!bl) {
            arrayList.add(new DropdownSelectComponent(ClientSettings.INSTANCE.searchBarStyle));
        }
        return arrayList.toArray(new GuiComponent[0]);
    }

    private static Void lambda$null$8(Throwable throwable) {
        Umbra.logThrowable(throwable);
        return null;
    }

    public static List<GuiComponent> M(ThemeColors themeColors, gg.umbra.config.ClientSettings clientSettings, ClientSettings clientSettings2, boolean bl) {
        ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
        ClientSettingsColorPickerEditorComponent clientSettingsColorPickerEditorComponent = new ClientSettingsColorPickerEditorComponent(clientSettings.guiColor);
        arrayList.add(clientSettingsColorPickerEditorComponent);
        arrayList.add(new BindValueRowComponent("Rebind GUI", clientSettings2.getBind()).w("Change the bind of the GUI"));
        arrayList.add(new ColorDividerComponent(themeColors.i));
        return arrayList;
    }

    private static void lambda$createSubmenuComponents$13() {
        Umbra.INSTANCE.getProfilesManager().resetAllSettings();
    }

    private static void lambda$createSubmenuComponents$16() {
        int n = 0;
        Umbra.debugLog("Potion Data Dump Version: " + ForgeVersion.c());
        Umbra.debugLog("__________________________________________________________");
        while (true) {
            Wrapper wrapper;
            if (ForgeVersion.MC_1_16_5.d()) {
                wrapper = StatusEffect.E(n);
                if (wrapper.isNull()) {
                    if (n != 0) break;
                    ++n;
                    continue;
                }
                Umbra.debugLog(n + " -> Display Name: " + ((StatusEffect)wrapper).d());
            } else {
                wrapper = Potion.getPotionById(n);
                if (wrapper.isNull()) {
                    if (n != 0) break;
                    ++n;
                    continue;
                }
                Umbra.debugLog(n + " -> Translation Key: " + ((Potion)wrapper).y$src$Ljava_lang_String_$yl6pfj() + " Display Name: " + I18n.format(((Potion)wrapper).y$src$Ljava_lang_String_$yl6pfj(), new Object[0]));
            }
            ++n;
        }
        Umbra.debugLog("__________________________________________________________");
    }

    private static void lambda$createSubmenuComponents$17() {
        Umbra.INSTANCE.getFontManager().U();
    }

    private static void lambda$createMainChildren$1() {
        try {
            ItemIconRenderer.clear();
        }
        catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        Umbra.INSTANCE.getNotificationManager().showInfo("Cleared", "Cleared ItemStack Cache", 3000L);
    }

    private static void lambda$createMainChildren$12() {
        Umbra.INSTANCE.getNotificationManager().showInfo("Umbra", "Online features are disabled in this build.", 3000L);
    }

    private static void lambda$createMainChildren$6() {
        Umbra.debugLog("Cloud sync is disabled.");
    }

    private static void lambda$createMainChildren$3() {
        Umbra.debugLog("Reloading settings...");
        Umbra.INSTANCE.getProfilesManager().resetAllSettings();
    }

    private static void lambda$createMainChildren$2() {
        ItemMappingEntry itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(Minecraft.thePlayer().B$src$Lgg_umbra_wrapper_impl_ItemStack_$impdvt());
        Umbra.INSTANCE.getNotificationManager().showInfo("Universal Item", "You're holding: " + (itemMappingEntry != null ? itemMappingEntry.getResourceKey() : null), 3000L);
    }

    private static void lambda$createSubmenuComponents$15() {
        for (Frame frame : ClientSettings.getAllFrames()) {
            if (!(frame instanceof ModuleCategoryFrame)) continue;
            ModuleCategoryFrame moduleCategoryFrame = (ModuleCategoryFrame)frame;
            moduleCategoryFrame.setVisible(true);
            for (GuiComponent guiComponent : moduleCategoryFrame.f()) {
                if (!(guiComponent instanceof ModuleComponent)) continue;
                ModuleComponent moduleComponent = (ModuleComponent)guiComponent;
                moduleComponent.expandValueComponents();
            }
        }
        ClientSettings.clearPositionedFrames();
    }

    private ClientSettingsComponentFactory() {
    }

    public static Map<ThemeComponentGroupKey, GuiComponent[]> d(ThemeColors themeColors, gg.umbra.config.ClientSettings clientSettings, ClientSettings clientSettings2, boolean bl) {
        PublicProfileSettings publicProfileSettings = Umbra.INSTANCE.getPublicProfileSettings();
        LinkedHashMap<ThemeComponentGroupKey, GuiComponent[]> linkedHashMap = new LinkedHashMap<ThemeComponentGroupKey, GuiComponent[]>();
        linkedHashMap.put(new ThemeComponentGroupKey("General", null), new GuiComponent[]{new BooleanToggleComponent(clientSettings2.multiKeybinding), new BooleanToggleComponent(publicProfileSettings.autoSave), new PublicProfileModeDropdownComponent(publicProfileSettings.language), new ClientSettingsActionButtonRowComponent("Reset current profile", ClientSettingsComponentFactory::lambda$createSubmenuComponents$13).w("This will set your current profile to the default settings of Umbra")});
        linkedHashMap.put(new ThemeComponentGroupKey("Modules", null), new GuiComponent[]{new ColorDividerComponent(themeColors.l), new BooleanToggleComponent(clientSettings.lobbyCheck), new BooleanToggleComponent(clientSettings.sanityCheck), new BooleanToggleComponent(clientSettings.showNbtTags), new BooleanToggleComponent(clientSettings.healthPrediction), new ClientSettingsEntityCheckDependentToggleComponent(clientSettings.estimateFoodHealing, clientSettings), new ClientSettingsThemeBooleanToggle(clientSettings.estimateFallDamage, clientSettings)});
        linkedHashMap.put(new ThemeComponentGroupKey("Silent Aim", null), new GuiComponent[]{new DropdownSelectComponent(clientSettings.movementCorrection), new BooleanToggleComponent(clientSettings.thirdPersonAimView), new BooleanToggleComponent(clientSettings.aimIndicator), new BooleanToggleComponent(clientSettings.useReach), new BooleanToggleComponent(clientSettings.useHitboxes)});
        linkedHashMap.put(new ThemeComponentGroupKey("GUI", "newgui"), ClientSettingsComponentFactory.W(clientSettings2, bl));
        linkedHashMap.put(new ThemeComponentGroupKey("Sound", null), new GuiComponent[]{new NumberSliderComponent(publicProfileSettings.volume), new BooleanToggleComponent(publicProfileSettings.muted)});
        linkedHashMap.put(new ThemeComponentGroupKey("Notifications", null), new GuiComponent[]{new BooleanToggleComponent(publicProfileSettings.notifications), new ClientSettingsPrimaryBooleanToggle(publicProfileSettings.toggleAlerts, publicProfileSettings), new ClientSettingsTertiaryBooleanToggle(publicProfileSettings.profileSwitchNotifications, publicProfileSettings), new ClientSettingsSecondaryBooleanToggle(publicProfileSettings.friendNotifications, publicProfileSettings)});
        return linkedHashMap;
    }

    private static void lambda$createMainChildren$0() {
        Umbra.INSTANCE.getNotificationManager().showInfo("Test header", "test body", 500000L);
    }

    private static void x() {
        Umbra.debugLog("=== Cache Sizes ===");
        Umbra.debugLog("CutoffLabelManager: " + SuffixTextTruncationIndexCache.INSTANCE.getCacheSize());
        Umbra.debugLog("SmoothCutoffLabelManager: " + TextTruncationIndexCache.INSTANCE.getCacheSize());
        Umbra.debugLog("--- FontRenderers ---");
        FontManager fontManager = Umbra.INSTANCE.getFontManager();
        Map<String, Map<Integer, SmoothFontRenderer>> map = fontManager.n();
        for (Map.Entry<String, Map<Integer, SmoothFontRenderer>> entry : map.entrySet()) {
            String string = entry.getKey();
            Map<Integer, SmoothFontRenderer> map2 = entry.getValue();
            for (Map.Entry<Integer, SmoothFontRenderer> entry2 : map2.entrySet()) {
                int n = entry2.getKey();
                SmoothFontRenderer smoothFontRenderer = entry2.getValue();
                Umbra.debugLog(string + "[" + n + "] cachedWidths=" + smoothFontRenderer.b() + " cachedStripped=" + smoothFontRenderer.R());
            }
        }
        Umbra.debugLog("===================");
    }
}
