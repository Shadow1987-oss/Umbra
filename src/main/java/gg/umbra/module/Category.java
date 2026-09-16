package gg.umbra.module;

import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.unmap.INamed;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Category
implements INamed {
    public static Category COMBAT;
    public static Category RENDER;
    public static Category NONE;
    private final String name;
    public static Category WORLD;
    private static List<Category> values;
    private final int color;
    private final String description;
    public static Category INVENTORY;
    public static Category HIDDEN;
    private static GuiComponent[] components;
    public static Category UTILITY;
    private final String iconKey;
    public static Category OTHER;
    public static Category FAVORITES;
    public static Category NETWORK;

    public static GuiComponent[] getLegacyComponents() {
        return components;
    }

    public int getColor() {
        return this.color;
    }

    private Category(String name, String iconKey, String description, int color) {
        this.name = name;
        this.iconKey = iconKey;
        this.description = description;
        this.color = color;
    }

    static {
        if (Category.getLegacyComponents() == null) {
            Category.setLegacyComponents(new GuiComponent[4]);
        }
        NONE = new Category("None", "newfavorites", new Color(100, 116, 139).getRGB());
        COMBAT = new Category("Combat", "combat", "Combat advantage modules", new Color(244, 63, 94).getRGB());
        UTILITY = new Category("Utility", "utility", new Color(251, 191, 36).getRGB());
        RENDER = new Category("Render", "render", "All kinds of visual goodies", new Color(139, 92, 246).getRGB());
        WORLD = new Category("World", "world", new Color(16, 185, 129).getRGB());
        INVENTORY = new Category("Inventory", "inventory", new Color(56, 189, 248).getRGB());
        NETWORK = new Category("Network", "network", new Color(129, 140, 248).getRGB());
        OTHER = new Category("Other", "other", new Color(148, 163, 184).getRGB());
        FAVORITES = new Category("Favorites", "newfavorites", "", 0);
        HIDDEN = new Category("Hidden", "favorites", new Color(71, 85, 105).getRGB());
        values = new ArrayList<Category>();
        values.add(FAVORITES);
        values.add(COMBAT);
        values.add(RENDER);
        values.add(UTILITY);
        values.add(WORLD);
        values.add(INVENTORY);
        values.add(OTHER);
        values.add(NONE);
    }

    private Category(String name, String iconKey, int color) {
        this(name, iconKey, "", color);
    }

    public String toString() {
        return this.name;
    }

    public static void setLegacyComponents(GuiComponent[] legacyComponents) {
        components = legacyComponents;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static List<Category> values() {
        return values;
    }

    public static void destruct() {
        values.clear();
        values = null;
        NONE = null;
        COMBAT = null;
        UTILITY = null;
        RENDER = null;
        OTHER = null;
        WORLD = null;
        FAVORITES = null;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIconKey() {
        return this.iconKey;
    }
}

