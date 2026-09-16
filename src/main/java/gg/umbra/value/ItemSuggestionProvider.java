package gg.umbra.value;

import gg.umbra.wrapper.impl.BuiltInRegistries;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Registry;
import gg.umbra.wrapper.impl.ResourceLocation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemSuggestionProvider
extends AbstractListValueSuggestionProvider {
    private static final Object CACHE_LOCK = new Object();
    private static List<String> cachedNames = null;
    private static Map<String, ItemSuggestionData> cachedData = null;

    public ItemSuggestionProvider() {
        this.setIncludeAllWhenEmpty(true);
    }

    @Override
    public List<String> getValues() {
        ItemSuggestionProvider.ensureBuilt();
        return cachedNames;
    }

    public static ItemStack getItemStack(String displayName) {
        ItemSuggestionData data = ItemSuggestionProvider.getData(displayName);
        if (data == null || data.item == null) {
            return null;
        }
        try {
            return data.item.b();
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static String getRegistryName(String displayName) {
        ItemSuggestionData data = ItemSuggestionProvider.getData(displayName);
        return data == null ? null : data.registryPath;
    }

    private static ItemSuggestionData getData(String displayName) {
        ItemSuggestionProvider.ensureBuilt();
        return displayName == null ? null : cachedData.get(displayName);
    }

    private static void ensureBuilt() {
        synchronized (CACHE_LOCK) {
            if (cachedNames != null) {
                return;
            }
            ArrayList<String> names = new ArrayList<String>();
            LinkedHashMap<String, ItemSuggestionData> data = new LinkedHashMap<String, ItemSuggestionData>();
            try {
                Registry registry = BuiltInRegistries.Y();
                if (registry != null && registry.getObject() != null) {
                    registry.o().forEach(entry -> {
                        try {
                            Item item = new Item(entry);
                            if (item.isNull()) {
                                return;
                            }
                            String registryPath = null;
                            try {
                                ResourceLocation resourceLocation = registry.W(entry);
                                if (resourceLocation != null) {
                                    registryPath = resourceLocation.getResourcePath();
                                }
                            }
                            catch (Throwable throwable) {
                                // ignore missing resource location
                            }
                            String displayName = ItemSuggestionProvider.displayNameOf(item);
                            if (displayName == null || displayName.isEmpty()) {
                                return;
                            }
                            displayName = ItemSuggestionProvider.stripColorCodes(displayName);
                            if (data.containsKey(displayName)) {
                                return;
                            }
                            names.add(displayName);
                            data.put(displayName, new ItemSuggestionData(item, registryPath));
                        }
                        catch (Throwable throwable) {
                            // skip items that fail to resolve
                        }
                    });
                }
            }
            catch (Throwable throwable) {
                // registry unavailable on this version -> empty suggestions
            }
            cachedNames = names;
            cachedData = data;
        }
    }

    private static String displayNameOf(Item item) {
        try {
            if (ForgeVersion.MC_1_21_0.d()) {
                return item.Y$src$Ljava_lang_String_$uel3xb();
            }
            ItemStack stack = ItemStack.S(item);
            if (ForgeVersion.MC_1_16_5.d()) {
                return item.h(stack);
            }
            return item.getItemStackDisplayName(stack);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static String stripColorCodes(String text) {
        return text.replaceAll("\u00a7[0-9a-fk-or]", "");
    }

    private static final class ItemSuggestionData {
        final Item item;
        final String registryPath;

        ItemSuggestionData(Item item, String registryPath) {
            this.item = item;
            this.registryPath = registryPath;
        }
    }
}
