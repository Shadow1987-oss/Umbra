package gg.umbra.wrapper.impl;

import com.google.common.collect.ImmutableMap;
import gg.umbra.mapping.mappings.MIBlockState;
import gg.umbra.wrapper.Wrapper;

public class BlockState
extends Wrapper {
    public boolean u() {
        return BlockState.umbraInstance.getMappings().DE.d(this.I);
    }

    public boolean Y() {
        return BlockState.umbraInstance.getMappings().DE.W(this.I);
    }

    public Object I(BlockProperty blockProperty) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return BlockState.umbraInstance.getMappings().DE.o(this.I, blockProperty.getObject());
        }
        ImmutableMap immutableMap = BlockState.umbraInstance.getMappings().DE.w(this.I);
        for (Object e : immutableMap.keySet()) {
            if (!e.getClass().equals(blockProperty.getObject().getClass())) continue;
            Object object = immutableMap.get(e);
            return object;
        }
        if (immutableMap.containsKey(blockProperty.getObject())) {
            Object object = immutableMap.get(blockProperty.getObject());
            return object;
        }
        return null;
    }

    public boolean x() {
        return BlockState.umbraInstance.getMappings().DE.e(this.I);
    }

    public Block getBlock() {
        return new Block(BlockState.umbraInstance.getMappings().DE.v(this.I));
    }

    public BlockState(Object object) {
        super(object);
    }

    public boolean g() {
        return BlockState.umbraInstance.getMappings().DE.I(this.I);
    }

    public BlockStateWorldBridge j() {
        return new BlockStateWorldBridge(MIBlockState.j(BlockState.umbraInstance.getMappings().DE, this.I));
    }

}

