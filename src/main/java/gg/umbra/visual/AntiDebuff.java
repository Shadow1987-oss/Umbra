package gg.umbra.visual;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPostRenderTick;
import gg.umbra.event.impl.EventPotionEffectCheck;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.value.ToggleSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Potion;
import gg.umbra.wrapper.impl.PotionEffect;
import gg.umbra.wrapper.impl.PotionEntry;
import gg.umbra.wrapper.impl.PotionRegistry;

public class AntiDebuff
extends HackModule {
    private final ToggleSetting removeEffects;
    private boolean duringRenderTick;
    private final ToggleSetting removeNausea = ToggleSetting.create(this, "Remove Nausea", true);
    private final ToggleSetting removeBlindness = ToggleSetting.create(this, "Remove Blindness", true);
    private final ToggleSetting removeSlowness = ToggleSetting.create(this, "Remove Slowness", true);

    @Listen
    public void onPostRenderTick(EventPostRenderTick event) {
        this.duringRenderTick = false;
    }

    @Listen
    public void onPreRenderTick(EventPreRenderTick event) {
        this.duringRenderTick = true;
    }

    @Override
    public String getId() {
        return "antidebuff";
    }

    public AntiDebuff() {
        super("AntiDebuff", -256, Category.RENDER, "Removes negative visual potion effects");
        this.removeEffects = ToggleSetting.create(this, "Remove Effects", false, "Removes non-visual effects\nCan be detected by anti-cheat");
        this.setDefaultVisibility(false);
        this.addValue(this.removeNausea, this.removeBlindness, this.removeSlowness, this.removeEffects);
    }


    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (this.removeNausea.getEffectiveValue().booleanValue()) {
            player.q(PotionRegistry.X.getResolvedId());
        }
        if (this.removeBlindness.getEffectiveValue().booleanValue() && this.removeEffects.getEffectiveValue().booleanValue()) {
            player.q(PotionRegistry.K.getResolvedId());
        }
        if (this.removeSlowness.getEffectiveValue().booleanValue() && this.removeEffects.getEffectiveValue().booleanValue()) {
            PotionEntry slowness = PotionRegistry.o;
            if (ForgeVersion.MC_1_16_5.v()) {
                PotionEffect slownessEffect = player.b(slowness);
                if (slownessEffect.isNotNull()) {
                    slowness.t(player, player.z$src$Ljava_lang_Object_$1k68ls2(), slownessEffect.L());
                }
            } else {
                slowness.t(player, player.z$src$Ljava_lang_Object_$1k68ls2(), 0);
            }
            player.q(slowness.getResolvedId());
        }
    }

    @Listen
    public void onPotionEffectCheck(EventPotionEffectCheck event) {
        if (!this.duringRenderTick) {
            return;
        }
        if (!event.getEntity().equals(event.getThePlayer())) {
            return;
        }
        Potion potion = event.getPotion();
        boolean suppressBlindness = this.removeBlindness.getEffectiveValue().booleanValue() && PotionRegistry.K.matchesLegacyPotion(potion);
        if (suppressBlindness) {
            event.setActive(false);
            event.setCancelled(true);
        }
    }
}

