package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class ClientChunkProvider
extends Wrapper {
    public ClientChunkProvider(Object object) {
        super(object);
    }

    public List<Chunk> L() {
        List list = ClientChunkProvider.umbraInstance.getMappingsMapperCompat().Dr.getChunkListing(this.I);
        ArrayList<Chunk> arrayList = new ArrayList<Chunk>();
        for (Object e : list) {
            arrayList.add(new Chunk(e));
        }
        return arrayList;
    }
}
