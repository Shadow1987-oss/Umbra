package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;
import java.util.Collection;

public class ResourceManager
extends Wrapper {
    public Collection<String> getSelectedIds() {
        return (Collection)ResourceManager.umbraInstance.getMappingsMapperCompat().packRepository.getSelectedIds(this.I);
    }

    public ResourceManager(Object handle) {
        super(handle);
    }
}
