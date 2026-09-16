package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import java.util.List;

public class MClientChunkProvider
extends Mapping {
    private static final String CHUNK_LISTING_FIELD_NAME = "chunkListing";
    private final MappingField chunkListingField;

    public MClientChunkProvider() {
        super(MappedClasses.le);
        this.chunkListingField = this.J(CHUNK_LISTING_FIELD_NAME, true, List.class);
    }

    public List getChunkListing(Object clientChunkProvider) {
        return (List)this.chunkListingField.getObject(clientChunkProvider);
    }
}

