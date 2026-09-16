package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;
import java.util.function.Consumer;

public class LevelEntityGetter
extends Wrapper {
    public LevelEntityGetter(Object wrappedObject) {
        super(wrappedObject);
    }

    public void forEachEntityInBounds(AxisAlignedBB boundingBox, Consumer consumer) {
        LevelEntityGetter.umbraInstance.getMappingsMapperCompat().levelEntityGetter.forEachEntityInBounds(this.I, boundingBox.getObject(), consumer);
    }
}
