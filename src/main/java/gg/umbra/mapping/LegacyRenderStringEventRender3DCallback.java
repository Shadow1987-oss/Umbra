package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.EventRender3DCallback;
import gg.umbra.mapping.InsertedCallbackMarker;
import gg.umbra.render.OffscreenRenderContext;
import gg.umbra.wrapper.impl.Minecraft;

public class LegacyRenderStringEventRender3DCallback
extends InsertedCallbackMarker {
    private static final String b = "hand";

    private static Exception a(Exception exception) {
        return exception;
    }

    public static void call(String string) {
        if (string.equals(b) && !OffscreenRenderContext.isRenderingOffscreen()) {
            float f = Minecraft.getTimer().renderPartialTicks();
            EventRender3DCallback eventRender3DCallback = new EventRender3DCallback(f);
            try {
                eventRender3DCallback.fire();
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        }
    }
}
