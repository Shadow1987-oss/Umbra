package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MInventoryPlayer;
import gg.umbra.wrapper.Wrapper;

import java.util.AbstractList;
import java.util.ArrayList;

public class InventoryPlayer
extends Wrapper {
    public void g(int n) {
        InventoryPlayer.umbraInstance.getMappings().v.A(this.I, n);
    }

    public ItemStack A() {
        return new ItemStack(MInventoryPlayer.N(InventoryPlayer.umbraInstance.getMappings().v, this.I));
    }

    public Object[] i() {
        if (ForgeVersion.MC_1_21_6.d()) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
            for (Object e : InventoryListBridge.armor().getSlots()) {
                EquipmentSlotSet equipmentSlotSet = new EquipmentSlotSet(e);
                arrayList.add(this.c(equipmentSlotSet.d()));
            }
            return arrayList.toArray();
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            AbstractList abstractList = MInventoryPlayer.S(InventoryPlayer.umbraInstance.getMappings().v, this.I);
            return abstractList.toArray();
        }
        return MInventoryPlayer.i(InventoryPlayer.umbraInstance.getMappings().v, this.I);
    }

    public InventoryPlayer(Object object) {
        super(object);
    }

    public Object[] M() {
        if (ForgeVersion.MC_1_12_2.d()) {
            AbstractList abstractList = InventoryPlayer.umbraInstance.getMappings().v.T(this.I);
            return abstractList.toArray();
        }
        return InventoryPlayer.umbraInstance.getMappings().v.X(this.I);
    }

    public ItemStack c(int n) {
        return new ItemStack(InventoryPlayer.umbraInstance.getMappings().v.N(this.I, n));
    }

    public int v() {
        return InventoryPlayer.umbraInstance.getMappings().v.s(this.I);
    }
}
