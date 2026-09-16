package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MNBTTagList;
import gg.umbra.wrapper.Wrapper;

public class TagList
extends Wrapper {
    public TagCompound getCompoundTagAt(int n) {
        return new TagCompound(MNBTTagList.w(TagList.umbraInstance.getMappingsMapperCompat().I, this.I, n));
    }

    public String a(int n) {
        return MNBTTagList.s(TagList.umbraInstance.getMappingsMapperCompat().I, this.I, n);
    }

    public int tagCount() {
        return MNBTTagList.R(TagList.umbraInstance.getMappingsMapperCompat().I, this.I);
    }

    public TagList(Object object) {
        super(object);
    }
}

