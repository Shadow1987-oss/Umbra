package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MArmorMaterial
extends Mapping {
    private final MappingField leatherField;
    private final MappingField goldField;
    private final MappingField chainField;
    private final MappingField ironField;
    private final MappingField diamondField;

    public static Object getIron(MArmorMaterial mapping) {
        return mapping.readIron();
    }


    private Object readIron() {
        return this.ironField.getObject(null);
    }

    public static Object getChain(MArmorMaterial mapping) {
        return mapping.readChain();
    }

    public static Object getDiamond(MArmorMaterial mapping) {
        return mapping.readDiamond();
    }

    private Object readLeather() {
        return this.leatherField.getObject(null);
    }

    private Object readChain() {
        return this.chainField.getObject(null);
    }

    public static Object getGold(MArmorMaterial mapping) {
        return mapping.readGold();
    }

    private Object readDiamond() {
        return this.diamondField.getObject(null);
    }

    public MArmorMaterial() {
        this(MItemStack.M());
    }

    private MArmorMaterial(int mappingState) {
        super(MappedClasses.ZM);
        String leatherFieldName = mappingState == 0 && ForgeVersion.MC_1_7_10.L() ? "CLOTH" : "LEATHER";
        this.leatherField = this.registerStaticField(leatherFieldName, Wrapper.isNativeAvailable, MappedClasses.ZM);
        this.chainField = this.registerStaticField("CHAIN", Wrapper.isNativeAvailable, MappedClasses.ZM);
        this.ironField = this.registerStaticField("IRON", Wrapper.isNativeAvailable, MappedClasses.ZM);
        this.goldField = this.registerStaticField("GOLD", Wrapper.isNativeAvailable, MappedClasses.ZM);
        this.diamondField = this.registerStaticField("DIAMOND", Wrapper.isNativeAvailable, MappedClasses.ZM);
    }

    public static Object getLeather(MArmorMaterial mapping) {
        return mapping.readLeather();
    }

    private Object readGold() {
        return this.goldField.getObject(null);
    }
}

