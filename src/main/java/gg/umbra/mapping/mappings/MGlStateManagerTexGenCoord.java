package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MGlStateManagerTexGenCoord
extends Mapping {
    private final MappingField clientField;
    private final MappingField noneField;
    private final MappingField serverField;

    public Object getClient() {
        return this.clientField.getObject(null);
    }

    public MGlStateManagerTexGenCoord() {
        super(MappedClasses.Zi);
        this.noneField = this.registerStaticField("NONE", true, MappedClasses.Zi);
        this.clientField = this.registerStaticField("CLIENT", true, MappedClasses.Zi);
        this.serverField = this.registerStaticField("SERVER", true, MappedClasses.Zi);
    }

    public Object getServer() {
        return this.serverField.getObject(null);
    }

    public Object getNone() {
        return this.noneField.getObject(null);
    }
}

