package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MWorldRenderer;
import gg.umbra.wrapper.Wrapper;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class WorldRenderer
extends Wrapper {
    public WorldRenderer(Object object) {
        super(object);
    }

    public void Q(boolean bl) {
        MWorldRenderer.X(WorldRenderer.umbraInstance.getMappings().qZ, this.I, bl);
    }


    public IntBuffer O() {
        if (ForgeVersion.MC_1_21_0.d()) {
            Umbra.notifyNativeStackTrace();
            return null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            ByteBuffer byteBuffer = (ByteBuffer)MWorldRenderer.t(WorldRenderer.umbraInstance.getMappings().qZ, this.I);
            return byteBuffer.asIntBuffer();
        }
        return (IntBuffer)MWorldRenderer.v(WorldRenderer.umbraInstance.getMappings().qZ, this.I);
    }

    public int o(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Umbra.notifyNativeStackTrace();
            return -1;
        }
        return MWorldRenderer.G(WorldRenderer.umbraInstance.getMappings().qZ, this.I, n);
    }
}

