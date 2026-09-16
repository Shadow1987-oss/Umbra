package gg.umbra.mapping;

import gg.umbra.mapping.InsertedCallbackMarker;
import gg.umbra.utils.render.RenderBatchManager;

public class RenderBatchFlushCallbackMarker
extends InsertedCallbackMarker {
    public static void call() {
        RenderBatchManager.getInstance().refreshTargetFramebuffer();
    }
}

