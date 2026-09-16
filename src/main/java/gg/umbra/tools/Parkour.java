package gg.umbra.tools;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.visual.GhostCamera;
import gg.umbra.wrapper.impl.AxisAlignedBB;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.MovementInput;
import java.util.List;

public class Parkour
extends HackModule {
    private boolean jumpPending;
    private boolean jumpKeyWasPressed;

    public Parkour() {
        super("Parkour", 0, Category.WORLD, "Jumps for you at the edge of blocks.");
    }

    @Override
    public String getId() {
        return "parkour";
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (Umbra.INSTANCE.getHackManager().getState(GhostCamera.class)) {
            return;
        }
        KeyBinding keyBinding = Minecraft.gameSettings().O();
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (this.jumpPending) {
            if (!this.jumpKeyWasPressed) {
                KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
            }
            this.jumpPending = false;
            this.jumpKeyWasPressed = false;
            return;
        }
        if (keyBinding.isKeyDown()) {
            return;
        }
        MovementInput movementInput = localPlayer.movementInput();
        boolean movingForward = movementInput.D() > 0.0f
                || ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().Y());
        if (movingForward && localPlayer.b$src$Z$fqlxe4()) {
            AxisAlignedBB boundingBox;
            if (ForgeVersion.MC_1_8_9.d()) {
                boundingBox = localPlayer.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl();
            } else {
                AxisAlignedBB currentBox = localPlayer.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl();
                boundingBox = currentBox.copy();
            }
            double distance = 0.0;
            double yaw = localPlayer.J();
            double yawOffset = 90.0;
            double offsetX = Math.cos(Math.toRadians(yaw + yawOffset)) * distance;
            double offsetZ = Math.sin(Math.toRadians(yaw + yawOffset)) * distance;
            double offsetY = -0.1;
            AxisAlignedBB offsetBox = boundingBox.k(offsetX, offsetY, offsetZ);
            List<?> nearCollisions = Minecraft.theWorld().i(localPlayer, offsetBox);
            distance = 1.0;
            offsetX = Math.cos(Math.toRadians(yaw + yawOffset)) * distance;
            offsetZ = Math.sin(Math.toRadians(yaw + yawOffset)) * distance;
            offsetY = -0.1;
            offsetBox = boundingBox.k(offsetX, offsetY, offsetZ);
            List<?> farCollisions = Minecraft.theWorld().i(localPlayer, offsetBox);
            int nearCount = nearCollisions.size();
            int farCount = farCollisions.size();
            if (nearCount == 0 && farCount == 0) {
                this.jumpKeyWasPressed = keyBinding.u();
                KeyBindingHelper.setPressedAndTick(keyBinding, true);
                this.jumpPending = true;
            }
        }
    }
}
