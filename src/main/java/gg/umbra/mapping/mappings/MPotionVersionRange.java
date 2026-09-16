package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingMethod;
import java.util.Set;

public class MPotionVersionRange
extends Mapping {
    private MappingMethod entrySetMethod;
    private MappingField emptyField;

    private Object readEmpty() {
        return this.emptyField.getObject(null);
    }

    public MPotionVersionRange() {
        super(MappedClasses.VX);
        Class[] entrySetParameterTypes = new Class[]{};
        Class<Set> entrySetReturnType = Set.class;
        boolean entrySetPublic = true;
        String entrySetMethodName = "entrySet";
        MPotionVersionRange mapping = this;
        this.entrySetMethod = mapping.Y(entrySetMethodName, entrySetPublic, entrySetReturnType, entrySetParameterTypes);
        Class emptyFieldType = MappedClasses.VX;
        boolean emptyFieldPublic = true;
        String emptyFieldName = "EMPTY";
        MPotionVersionRange emptyMapping = this;
        this.emptyField = emptyMapping.registerStaticField(emptyFieldName, emptyFieldPublic, emptyFieldType);
    }

    public static Object getEntrySet(MPotionVersionRange mapping, Object enchantmentsHandle) {
        return mapping.readEntrySet(enchantmentsHandle);
    }

    private Object readEntrySet(Object enchantmentsHandle) {
        return this.entrySetMethod.invokeObject(enchantmentsHandle, new Object[0]);
    }

    public static Object getEmpty(MPotionVersionRange mapping) {
        return mapping.readEmpty();
    }
}

