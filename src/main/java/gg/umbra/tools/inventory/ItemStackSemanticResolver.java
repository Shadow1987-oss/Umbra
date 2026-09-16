package gg.umbra.tools.inventory;

import gg.umbra.Umbra;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.tools.armorswitch.ArmorMaterialType;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.List;
import gg.umbra.tools.inventory.ArmorItemMappingEntry;
import org.jetbrains.annotations.Nullable;

public class ItemStackSemanticResolver {
    private final List<ItemMappingEntry> mappings = new ArrayList<ItemMappingEntry>();
    private final List<ItemMappingEntry> legacyMappings = new ArrayList<ItemMappingEntry>();
    public static boolean LOG_MISSING_MAPPINGS = true;
    private boolean reloadScheduled;

    public void loadMappings() {
        String resourceName = "universal_items.csv";
        byte[] resourceBytes = Umbra.readResource(resourceName);
        ArrayList<ItemMappingEntry> newMappings = new ArrayList<ItemMappingEntry>();
        ArrayList<ItemMappingEntry> newLegacyMappings = new ArrayList<ItemMappingEntry>();
        boolean registryUnavailable = false;
        for (String line : new String(resourceBytes).split("\n")) {
            String mappingLine = line.trim();
            ItemMappingEntry itemMappingEntry = ItemMappingEntry.parse(mappingLine);
            try {
                Item resolvedItem = itemMappingEntry.resolveItem();
                if (resolvedItem != null && ItemStackScoreUtil.R(resolvedItem)) {
                    for (ArmorMaterialType armorMaterialType : ArmorMaterialType.values()) {
                        if (!armorMaterialType.G(itemMappingEntry.getResourceKey())) continue;
                        itemMappingEntry = new ArmorItemMappingEntry(itemMappingEntry, armorMaterialType);
                    }
                }
            }
            catch (Throwable throwable) {
                // Item registries are not bound yet (e.g. injected mid world-load):
                // keep the base mapping and schedule a reload once the world is ready.
                registryUnavailable = true;
            }
            newMappings.add(itemMappingEntry);
            if (itemMappingEntry.getLegacyIdString() == null) continue;
            newLegacyMappings.add(itemMappingEntry);
        }
        this.mappings.clear();
        this.legacyMappings.clear();
        this.mappings.addAll(newMappings);
        this.legacyMappings.addAll(newLegacyMappings);
        this.reloadScheduled = registryUnavailable;
    }

    public void ensureLoaded() {
        if (!this.reloadScheduled || Minecraft.theWorld().isNull()) {
            return;
        }
        this.reloadScheduled = false;
        this.loadMappings();
    }

    @Nullable
    public ItemMappingEntry findByName(String name) {
        for (ItemMappingEntry itemMappingEntry : this.mappings) {
            if (!itemMappingEntry.getResourceKey().equals(name)) continue;
            return itemMappingEntry;
        }
        return null;
    }


    public void reportMissingMappings() {
        ArrayList<ItemStack> missingItems = new ArrayList<ItemStack>();
        for (ItemStack itemStack : ItemStackScoreUtil.S()) {
            ItemMappingEntry itemMappingEntry = this.resolve(itemStack);
            if (itemMappingEntry != null) continue;
            missingItems.add(itemStack);
        }
        if (!LOG_MISSING_MAPPINGS) {
            return;
        }
        if (missingItems.isEmpty()) {
            return;
        }
        Umbra.debugLog("Failed to find " + missingItems.size() + " item(s):");
    }

    @Nullable
    public ItemMappingEntry findLegacyMapping(int itemId, int metadata) {
        for (ItemMappingEntry itemMappingEntry : this.legacyMappings) {
        assert (itemMappingEntry.getLegacyId() != null);
        if (itemMappingEntry.getLegacyId() != itemId || itemMappingEntry.getMetadata() == null || itemMappingEntry.getMetadata() != metadata) continue;
            return itemMappingEntry;
        }
        return null;
    }

    @Nullable
    public ItemMappingEntry findLegacyMapping(int itemId) {
        for (ItemMappingEntry itemMappingEntry : this.legacyMappings) {
        assert (itemMappingEntry.getLegacyId() != null);
        if (itemMappingEntry.getLegacyId() != itemId || itemMappingEntry.getMetadata() != null && itemMappingEntry.getMetadata() != 0) continue;
            return itemMappingEntry;
        }
        return null;
    }

    @Nullable
    public ItemMappingEntry resolve(ItemStack itemStack) {
        this.ensureLoaded();
        ItemMappingEntry itemMappingEntry;
        if (itemStack.isNull()) {
            return null;
        }
        Item item = itemStack.getItem();
        if (item.isNull()) {
            return null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            String itemName = item.getObject().toString();
            String normalizedName = ForgeVersion.MC_1_21_0.d() ? itemName : "minecraft:" + itemName;
            return this.findByName(normalizedName);
        }
        int itemId = item.P();
        int metadata = itemStack.L();
        if (!item.p()) {
            metadata = 0;
        }
        if ((itemMappingEntry = this.findLegacyMapping(itemId, metadata)) != null) {
            return itemMappingEntry;
        }
        if (itemStack.L() != 0) {
            return null;
        }
        return this.findLegacyMapping(itemId);
    }
}

