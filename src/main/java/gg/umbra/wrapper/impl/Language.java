package gg.umbra.wrapper.impl;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.Wrapper;

public class Language
extends Wrapper {
    public Language(Object languageHandle) {
        super(languageHandle);
    }


    public boolean isUnicode() {
        if (ForgeVersion.MC_1_16_5.d() && !MappedClasses.Vi.isInstance(this.getObject())) {
            return false;
        }
        return Language.umbraInstance.getMappingsMapperCompat().language.isUnicode(this.I);
    }
}

