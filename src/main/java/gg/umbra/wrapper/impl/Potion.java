package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MPotion;
import gg.umbra.wrapper.Wrapper;

import java.util.ArrayList;

public class Potion
extends Wrapper {
    public Potion(Object object) {
        super(object);
    }

    public boolean n() {
        if (ForgeVersion.MC_1_16_5.d()) {
            for (Object e : Potion.umbraInstance.getMappings().qU.W(this.I)) {
                PotionEffect potionEffect = new PotionEffect(e);
                if (potionEffect.i().p()) continue;
                return true;
            }
            return false;
        }
        return Potion.umbraInstance.getMappings().qU.E(this.I);
    }

    public int getId() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return -1;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return MPotion.R(Potion.umbraInstance.getMappings().qU, this.getObject());
        }
        return MPotion.g(Potion.umbraInstance.getMappings().qU, this.I);
    }

    public boolean isBadEffect() {
        return MPotion.u(Potion.umbraInstance.getMappings().qU, this.I);
    }

    public static Potion[] getPotionTypes() {
        if (ForgeVersion.MC_1_12_2.d()) {
            Iterable iterable = (Iterable)MPotion.x(Potion.umbraInstance.getMappings().qU);
            ArrayList<Potion> arrayList = new ArrayList<Potion>();
            arrayList.add(null);
            for (Object t : iterable) {
                Potion potion = new Potion(t);
                arrayList.add(potion);
            }
            return arrayList.toArray(new Potion[arrayList.size()]);
        }
        Object[] objectArray = MPotion.i(Potion.umbraInstance.getMappings().qU);
        Potion[] potionArray = new Potion[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            potionArray[i] = new Potion(objectArray[i]);
        }
        return potionArray;
    }


    public int y() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return -1;
        }
        return MPotion.H(Potion.umbraInstance.getMappings().qU, this.I);
    }

    public String y$src$Ljava_lang_String_$yl6pfj() {
        return MPotion.q(Potion.umbraInstance.getMappings().qU, this.I);
    }

    public static Potion getPotionById(int n) {
        if (ForgeVersion.MC_1_12_2.d()) {
            return new Potion(MPotion.Q(Potion.umbraInstance.getMappings().qU, n));
        }
        return Potion.getPotionTypes()[n];
    }
}

