package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class Foods
extends Wrapper {
    public Foods(Object handle) {
        super(handle);
    }

    public static FoodProperties apple() {
        return wrap(Foods.umbraInstance.getMappings().foods.apple());
    }

    public static FoodProperties bakedPotato() {
        return wrap(Foods.umbraInstance.getMappings().foods.bakedPotato());
    }

    public static FoodProperties beef() {
        return wrap(Foods.umbraInstance.getMappings().foods.beef());
    }

    public static FoodProperties beetroot() {
        return wrap(Foods.umbraInstance.getMappings().foods.beetroot());
    }

    public static FoodProperties beetrootSoup() {
        return wrap(Foods.umbraInstance.getMappings().foods.beetrootSoup());
    }

    public static FoodProperties bread() {
        return wrap(Foods.umbraInstance.getMappings().foods.bread());
    }

    public static FoodProperties carrot() {
        return wrap(Foods.umbraInstance.getMappings().foods.carrot());
    }

    public static FoodProperties chicken() {
        return wrap(Foods.umbraInstance.getMappings().foods.chicken());
    }

    public static FoodProperties chorusFruit() {
        return wrap(Foods.umbraInstance.getMappings().foods.chorusFruit());
    }

    public static FoodProperties cod() {
        return wrap(Foods.umbraInstance.getMappings().foods.cod());
    }

    public static FoodProperties cookedBeef() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedBeef());
    }

    public static FoodProperties cookedChicken() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedChicken());
    }

    public static FoodProperties cookedCod() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedCod());
    }

    public static FoodProperties cookedMutton() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedMutton());
    }

    public static FoodProperties cookedPorkchop() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedPorkchop());
    }

    public static FoodProperties cookedRabbit() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedRabbit());
    }

    public static FoodProperties cookedSalmon() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookedSalmon());
    }

    public static FoodProperties cookie() {
        return wrap(Foods.umbraInstance.getMappings().foods.cookie());
    }

    public static FoodProperties driedKelp() {
        return wrap(Foods.umbraInstance.getMappings().foods.driedKelp());
    }

    public static FoodProperties enchantedGoldenApple() {
        return wrap(Foods.umbraInstance.getMappings().foods.enchantedGoldenApple());
    }

    public static FoodProperties goldenApple() {
        return wrap(Foods.umbraInstance.getMappings().foods.goldenApple());
    }

    public static FoodProperties goldenCarrot() {
        return wrap(Foods.umbraInstance.getMappings().foods.goldenCarrot());
    }

    public static FoodProperties honeyBottle() {
        return wrap(Foods.umbraInstance.getMappings().foods.honeyBottle());
    }

    public static FoodProperties melonSlice() {
        return wrap(Foods.umbraInstance.getMappings().foods.melonSlice());
    }

    public static FoodProperties mushroomStew() {
        return wrap(Foods.umbraInstance.getMappings().foods.mushroomStew());
    }

    public static FoodProperties mutton() {
        return wrap(Foods.umbraInstance.getMappings().foods.mutton());
    }

    public static FoodProperties poisonousPotato() {
        return wrap(Foods.umbraInstance.getMappings().foods.poisonousPotato());
    }

    public static FoodProperties porkchop() {
        return wrap(Foods.umbraInstance.getMappings().foods.porkchop());
    }

    public static FoodProperties potato() {
        return wrap(Foods.umbraInstance.getMappings().foods.potato());
    }

    public static FoodProperties pufferfish() {
        return wrap(Foods.umbraInstance.getMappings().foods.pufferfish());
    }

    public static FoodProperties pumpkinPie() {
        return wrap(Foods.umbraInstance.getMappings().foods.pumpkinPie());
    }

    public static FoodProperties rabbit() {
        return wrap(Foods.umbraInstance.getMappings().foods.rabbit());
    }

    public static FoodProperties rabbitStew() {
        return wrap(Foods.umbraInstance.getMappings().foods.rabbitStew());
    }

    public static FoodProperties rottenFlesh() {
        return wrap(Foods.umbraInstance.getMappings().foods.rottenFlesh());
    }

    public static FoodProperties salmon() {
        return wrap(Foods.umbraInstance.getMappings().foods.salmon());
    }

    public static FoodProperties spiderEye() {
        return wrap(Foods.umbraInstance.getMappings().foods.spiderEye());
    }

    public static FoodProperties suspiciousStew() {
        return wrap(Foods.umbraInstance.getMappings().foods.suspiciousStew());
    }

    public static FoodProperties sweetBerries() {
        return wrap(Foods.umbraInstance.getMappings().foods.sweetBerries());
    }

    public static FoodProperties glowBerries() {
        return wrap(Foods.umbraInstance.getMappings().foods.glowBerries());
    }

    public static FoodProperties tropicalFish() {
        return wrap(Foods.umbraInstance.getMappings().foods.tropicalFish());
    }

    private static FoodProperties wrap(Object handle) {
        return new FoodProperties(handle);
    }
}
