package gg.umbra.utils.render;

import gg.umbra.utils.render.BufferedOpenGlBackend;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.LegacyOpenGlBackend;
import gg.umbra.utils.render.OpenGlBackend;

public class OpenGlBackendHolder {
    public static OpenGlBackend backend = GuiRenderPrimitives.d() ? new BufferedOpenGlBackend() : new LegacyOpenGlBackend();

}

