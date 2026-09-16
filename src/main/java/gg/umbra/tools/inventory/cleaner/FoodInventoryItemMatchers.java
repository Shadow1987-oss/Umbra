package gg.umbra.tools.inventory.cleaner;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.tools.inventory.cleaner.CompositeInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatchContext;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherBuilderFoundation;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherGroup;
import gg.umbra.tools.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.umbra.tools.inventory.cleaner.StringInventoryItemMatcher;
import gg.umbra.tools.inventory.cleaner.StringInventoryItemMatcherBuilder;
import gg.umbra.tools.inventory.cleaner.StringMatchOperator;
import gg.umbra.wrapper.impl.DataComponentMap;
import gg.umbra.wrapper.impl.DataComponents;
import gg.umbra.wrapper.impl.FoodProperties;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemFood;
import gg.umbra.wrapper.impl.ItemStack;

public class FoodInventoryItemMatchers {
    public static final CompositeInventoryItemMatcher ANY_FOOD;
    public static final StringInventoryItemMatcher COOKED_FOOD;

    private static boolean isFoodItem(ItemStack itemStack, Item item) {
        if (ForgeVersion.MC_1_20_6.d()) {
            DataComponentMap dataComponentMap = item.g();
            return dataComponentMap.V(DataComponents.d());
        }
        return item.isInstance(MappedClasses.ITEM_FOOD);
    }

    static {
        String[] labels = new String[]{"baked_", "cooked_", "cooked-food-hover@2x", "Cooked food", "Any type of cooked food", "food-hover@2x", "Any food", "Any type of food", "cooked-food", "any-food"};
        ANY_FOOD = ((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)((InventoryItemMatcherBuilderFoundation)InventoryItemMatcher.builder().composite().withId(labels[9])).withName(labels[6])).withDescription(labels[7])).withIconName(labels[5])).withGroup(InventoryItemMatcherGroup.FOOD)).withPredicate(FoodInventoryItemMatchers::isFoodItem).withComparator(FoodInventoryItemMatchers::compareByNutrition)).build();
        COOKED_FOOD = ((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)((StringInventoryItemMatcherBuilder)InventoryItemMatcher.builder().stringMatcher().withId(labels[8])).withName(labels[3])).withDescription(labels[4])).withIconName(labels[2])).withGroup(InventoryItemMatcherGroup.FOOD)).addPattern(labels[1], StringMatchOperator.STARTS).addPattern(labels[0], StringMatchOperator.STARTS).withComparator(ANY_FOOD.getComparator())).build();
    }

    public static void initialize() {
        InventoryItemMatcherRegistry.register(ANY_FOOD);
        InventoryItemMatcherRegistry.register(COOKED_FOOD);
    }

    private static int compareByNutrition(InventoryItemMatchContext firstContext, InventoryItemMatchContext secondContext) {
        if (ForgeVersion.MC_1_20_6.d()) {
            DataComponentMap firstComponents = firstContext.getItem().g();
            DataComponentMap secondComponents = secondContext.getItem().g();
            Object firstFoodComponent = firstComponents.E(DataComponents.d());
            Object secondFoodComponent = secondComponents.E(DataComponents.d());
            if (firstFoodComponent == null || secondFoodComponent == null) {
                return 0;
            }
            FoodProperties firstFood = new FoodProperties(firstFoodComponent);
            FoodProperties secondFood = new FoodProperties(secondFoodComponent);
            float firstNutritionScore = (float)firstFood.getNutrition() * firstFood.getSaturation();
            float secondNutritionScore = (float)secondFood.getNutrition() * secondFood.getSaturation();
            return Float.compare(firstNutritionScore, secondNutritionScore);
        }
        ItemFood firstFood = new ItemFood(firstContext.getItem());
        ItemFood secondFood = new ItemFood(secondContext.getItem());
        float firstNutritionScore = (float)firstFood.getNutrition() * firstFood.getSaturation();
        float secondNutritionScore = (float)secondFood.getNutrition() * secondFood.getSaturation();
        return Float.compare(firstNutritionScore, secondNutritionScore);
    }

}

