package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import java.util.stream.Stream;

public class MResourceKeyRegistryLookupStreamBridge
extends Mapping {
    private static final String LIST_ELEMENTS_METHOD_NAME = "listElements";
    private final MappingMethod listElementsMethod;

    public MResourceKeyRegistryLookupStreamBridge() {
        super(MappedClasses.Da);
        this.listElementsMethod = this.Y(LIST_ELEMENTS_METHOD_NAME, true, Stream.class, new Class[]{});
    }

    public Stream<Object> listElements(Object registryLookup) {
        return (Stream)this.listElementsMethod.invokeObject(registryLookup, new Object[0]);
    }
}

