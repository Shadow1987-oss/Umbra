package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPostRenderTick;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;

public class NoHurtCameraHudModule
extends HudModule {
    private int savedHurtTime;


    @Listen
    public void onPreRenderTick(EventPreRenderTick event) {
        if (event.getThePlayer().isNull()) {
            return;
        }
        if (event.getThePlayer().c$src$I$15a9iwo() > 0) {
            this.savedHurtTime = event.getThePlayer().c$src$I$15a9iwo();
            event.getThePlayer().I(0);
        }
    }

    public NoHurtCameraHudModule() {
        super("NoHurtCam", HudModuleGroup.GAME, "legitmodeicon");
        this.setSuffix("Disables the hurt camera shaking effect");
    }

    @Listen
    public void onPostRenderTick(EventPostRenderTick event) {
        if (event.getThePlayer().isNull()) {
            return;
        }
        if (this.savedHurtTime > 0) {
            event.getThePlayer().I(this.savedHurtTime);
            this.savedHurtTime = 0;
        }
    }
}

