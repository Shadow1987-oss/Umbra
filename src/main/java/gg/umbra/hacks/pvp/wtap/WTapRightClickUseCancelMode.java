package gg.umbra.hacks.pvp.wtap;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventClickMouse;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.hacks.pvp.TargetSelect;
import gg.umbra.rotation.RotationManager;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.GuiScreen;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;

public class WTapRightClickUseCancelMode
extends SubHack<TargetSelect> {
    private int cooldownTicks = -1;
    private boolean cancelActive = false;
    private static final long COOLDOWN_TICKS;

    static {
        COOLDOWN_TICKS = -5882419382201614328L;
    }

    public WTapRightClickUseCancelMode(HackModule mod, String string) {
        super(mod, string);
    }

    @Listen
    public void onClickMouse(EventClickMouse eventClickMouse) {
        if (this.isCancelActive()) {
            eventClickMouse.setCancelled(true);
        }
    }


    @Listen
    public void onTick(EventPreTick eventPreTick) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            this.cancelActive = false;
            return;
        }
        if (this.cooldownTicks > 0) {
            --this.cooldownTicks;
            this.cancelActive = false;
            return;
        }
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        EntityLivingBase target = rayTrace.isNotNull() && rayTrace.getEntity().isInstance(MappedClasses.zm)
                ? new EntityLivingBase(rayTrace.getEntity()) : new EntityLivingBase(null);
        GuiScreen screen = Minecraft.currentScreen();
        if (target.isNull() && screen.isNull()) {
            this.cancelActive = false;
            return;
        }
        if (target.isNotNull() && player.b$src$Z$fqlxe4() && target.V$src$I$fk0dv5() > 12) {
            if (!((TargetSelect)this.getParent()).shouldTrigger()) {
                this.cooldownTicks = (int)COOLDOWN_TICKS;
            }
            this.cancelActive = true;
            return;
        }
        this.cancelActive = false;
    }

    public boolean isCancelActive() {
        return this.cancelActive;
    }
}
