package gg.umbra.tools;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import io.netty.channel.Channel;

public class AutoLog
extends HackModule {
    private final SliderSetting health = SliderSetting.create(this, "Health", "#.#", "", 1.0, 6.0, 20.0, 0.5, "Disconnect when your health drops below this (in hearts)");
    private final ToggleSetting respectTotem = ToggleSetting.create(this, "Respect totem", true, "Do not disconnect while a totem of undying is equipped in your offhand");
    private boolean disconnected;

    public AutoLog() {
        super("AutoLog", 0, Category.UTILITY, "Logs out before your health runs out.");
        this.addValue(this.health, this.respectTotem);
    }

    @Override
    public String getId() {
        return "autolog";
    }

    private boolean hasTotem(EntityPlayerSP player) {
        ItemStack itemStack = player.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(45).getStack();
        if (itemStack == null || itemStack.isNull()) {
            return false;
        }
        ItemMappingEntry itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack);
        return itemMappingEntry != null && itemMappingEntry.getResourceKey().toLowerCase().contains("totem_of_undying");
    }

    private void disconnect() {
        try {
            Object channelHandle = Minecraft.x$src$Lgg_umbra_wrapper_impl_NetworkManager_$1sglv7v().getChannel().getObject();
            if (channelHandle instanceof Channel) {
                ((Channel) channelHandle).close();
            }
        } catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (this.disconnected) {
            return;
        }
        EntityPlayerSP player = event.getThePlayer();
        if (player == null || player.isNull()) {
            return;
        }
        if (player.M$src$Z$ff28xj()) {
            return;
        }
        if ((double) player.w$src$F$15l9epb() > (Double) this.health.getValue()) {
            return;
        }
        if (this.respectTotem.getEffectiveValue() && this.hasTotem(player)) {
            return;
        }
        this.disconnected = true;
        this.disconnect();
        this.toggle();
    }

    @Override
    public void onEnable() {
        this.disconnected = false;
    }
}
