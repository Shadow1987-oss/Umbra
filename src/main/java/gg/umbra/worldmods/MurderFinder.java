package gg.umbra.worldmods;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventRender2D;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.notification.NotificationType;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.EntityItem;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.FontRenderer;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.ScaledResolution;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MurderFinder
extends HackModule {
    private final OptionalItemFilter messages;
    private final TimerUtil calloutTimer;
    private Object trackedWorld;
    private final Queue<String> pendingMessages = new ConcurrentLinkedQueue<>();
    private final ToggleSetting callout;
    private final SliderSetting delay;
    private final ItemFilterList murdererItems;
    private final List<Integer> murdererIds = new ArrayList<>();
    private final ToggleSetting knifeOnly;
    private final OptionalItemFilter knifeNames;
    private final ToggleSetting esp;
    private final ColorPicker espColor;
    private final ToggleSetting knifeFinder;
    private final ColorPicker knifeColor;
    private final ToggleSetting proximityAlert;
    private final SliderSetting alertRange;
    private final TimerUtil alertTimer;
    private EntityItem nearestKnife;
    private double nearestKnifeDistance = Double.MAX_VALUE;

    public boolean isMurderer(EntityLivingBase entity) {
        if (!this.isEnabled()) {
            return false;
        }
        if (!entity.isInstance(MappedClasses.Yl)) {
            return false;
        }
        return this.murdererIds.contains(entity.S());
    }

    private boolean isKnife(ItemStack itemStack) {
        if (itemStack == null || itemStack.isNull()) {
            return false;
        }
        String displayName = itemStack.x();
        if (displayName == null || displayName.isEmpty()) {
            return false;
        }
        String lower = displayName.toLowerCase();
        for (String name : this.knifeNames.getEnabledValues()) {
            if (lower.contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private double getDistanceSq(EntityLivingBase entity) {
        return Minecraft.thePlayer().i(entity.c(), entity.A(), entity.Z());
    }

    @Listen
    public void onRender3D(EventRender3D event) {
        if (!this.esp.getEffectiveValue().booleanValue() && !this.knifeFinder.getEffectiveValue().booleanValue()) {
            return;
        }
        for (Object e : Minecraft.theWorld().z()) {
            if (MappedClasses.zW.isAssignableFrom(e.getClass())) {
                if (this.knifeFinder.getEffectiveValue().booleanValue()) {
                    EntityItem entityItem = new EntityItem(e);
                    if (this.isKnife(entityItem.getItemStack())) {
                        RenderUtil.k(entityItem, 1.0, null, this.knifeColor.getMutableColor(), event.getTicks());
                    }
                }
                continue;
            }
            if (!this.esp.getEffectiveValue().booleanValue() || !MappedClasses.Yl.isAssignableFrom(e.getClass()) || MappedClasses.z5.isAssignableFrom(e.getClass())) continue;
            EntityPlayer entityPlayer = new EntityPlayer(e);
            if (this.murdererIds.contains(entityPlayer.S())) {
                RenderUtil.k(entityPlayer, 1.0, null, this.espColor.getMutableColor(), event.getTicks());
            }
        }
    }

    @Listen
    public void onRender2D(EventRender2D event) {
        ScaledResolution scaledResolution = new ScaledResolution();
        int textY = 25;
        FontRenderer fontRenderer = event.getFontRenderer();
        if (!this.murdererIds.isEmpty()) {
            fontRenderer.drawStringWithShadow("\u00a7nMurderer List", (double)(scaledResolution.getScaledWidth() / 2 - 20), 15.0, -1);
            for (Object e : Minecraft.theWorld().z()) {
                EntityPlayer entityPlayer;
                if (!MappedClasses.Yl.isAssignableFrom(e.getClass()) || MappedClasses.z5.isAssignableFrom(e.getClass()) || !this.murdererIds.contains((entityPlayer = new EntityPlayer(e)).S())) continue;
                fontRenderer.drawStringWithShadow(entityPlayer.getName(), (double)(scaledResolution.getScaledWidth() / 2 - 20), (double)textY, -1);
                textY += 10;
            }
        }
        if (this.knifeFinder.getEffectiveValue().booleanValue() && this.nearestKnife != null && this.nearestKnifeDistance <= ((Number)this.alertRange.getValue()).doubleValue()) {
            fontRenderer.drawStringWithShadow("\u00a76Knife: " + (int)Math.ceil(this.nearestKnifeDistance) + "m", (double)(scaledResolution.getScaledWidth() / 2 - 20), (double)textY, -13684944);
        }
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (this.trackedWorld == null || !Minecraft.theWorld().getObject().equals(this.trackedWorld)) {
            this.murdererIds.clear();
            this.pendingMessages.clear();
            this.nearestKnife = null;
            this.nearestKnifeDistance = Double.MAX_VALUE;
            this.trackedWorld = Minecraft.theWorld().getObject();
        }
        if (this.pendingMessages.size() > 0 && this.calloutTimer.hasTimeElapsed(((Double)this.delay.getValue()).longValue())) {
            Minecraft.thePlayer().sendChatMessage(this.pendingMessages.poll());
            this.calloutTimer.reset();
        }
        this.nearestKnife = null;
        this.nearestKnifeDistance = Double.MAX_VALUE;
        for (Object e : Minecraft.theWorld().z()) {
            if (MappedClasses.zW.isAssignableFrom(e.getClass())) {
                if (this.knifeFinder.getEffectiveValue().booleanValue()) {
                    EntityItem entityItem = new EntityItem(e);
                    if (this.isKnife(entityItem.getItemStack())) {
                        double distanceSq = Minecraft.thePlayer().i(entityItem.c(), entityItem.A(), entityItem.Z());
                        if (distanceSq < this.nearestKnifeDistance) {
                            this.nearestKnifeDistance = distanceSq;
                            this.nearestKnife = entityItem;
                        }
                    }
                }
                continue;
            }
            EntityPlayer entityPlayer;
            if (!MappedClasses.Yl.isAssignableFrom(e.getClass()) || MappedClasses.z5.isAssignableFrom(e.getClass())) continue;
            entityPlayer = new EntityPlayer(e);
            if (this.murdererIds.contains(entityPlayer.S())) {
                this.checkProximity(entityPlayer);
                continue;
            }
            if (!entityPlayer.getHeldItemHand().isNotNull()) continue;
            boolean knifeMatch = this.isKnife(entityPlayer.getHeldItemHand());
            boolean itemMatch = this.murdererItems.matches(entityPlayer.getHeldItemHand());
            boolean matches;
            if (this.knifeOnly.getEffectiveValue().booleanValue()) {
                matches = knifeMatch;
            } else {
                matches = knifeMatch || itemMatch;
            }
            if (!matches) continue;
            this.murdererIds.add(entityPlayer.S());
            this.checkProximity(entityPlayer);
            if (!this.callout.getEffectiveValue().booleanValue()) continue;
            List<String> messageTemplates = this.messages.getEnabledValues();
            int messageIndex = (int)Math.round((double)messageTemplates.size() * Math.random());
            if (messageIndex >= messageTemplates.size()) {
                messageIndex = messageTemplates.size() - 1;
            }
            String message = messageTemplates.get(messageIndex).replace("%s", entityPlayer.getName());
            this.pendingMessages.add(message);
        }
    }

    private void checkProximity(EntityPlayer entityPlayer) {
        if (!this.proximityAlert.getEffectiveValue().booleanValue() || !this.alertTimer.hasTimeElapsed(4000L)) {
            return;
        }
        double distance = this.getDistanceSq(entityPlayer);
        if (distance <= ((Number)this.alertRange.getValue()).doubleValue()) {
            Umbra.INSTANCE.getNotificationManager().show("Murderer Nearby", entityPlayer.getName() + " is " + (int)distance + "m away!", NotificationType.ALERT, 3000L);
            this.alertTimer.reset();
        }
    }

    @Override
    public String getId() {
        return "murdererfinder";
    }

    public MurderFinder() {
        super("MurdererFinder", -11859, Category.WORLD, "Shows a list of suspected Murderers.");
        this.callout = ToggleSetting.create(this, "Callout", false, "Calls out who the suspected murderer is in chat.");
        this.messages = OptionalItemFilter.createWithDescription(this, "murder-messages", "Messages", "Use %s to use the murderer's name", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("%s is the murderer!", "i saw that %s!"));
        this.delay = SliderSetting.createWithDescription(this, "Delay", "#", "ms", 0.0, 3100.0, 5000.0, "Delay between murderer callouts.");
        this.murdererItems = ItemFilterList.create(this, "murderer-items", "Murderer Items", ItemFilterList.BLOCK_LIST_COLOR, Arrays.asList(new ItemLimitData("swords"), new ItemLimitData("shovels"), new ItemLimitData("axes"), new ItemLimitData("pickaxes"), new ItemLimitData(288), new ItemLimitData(396), new ItemLimitData(421), new ItemLimitData(398), new ItemLimitData(369), new ItemLimitData(75), new ItemLimitData(50), new ItemLimitData(352)));
        this.knifeOnly = ToggleSetting.create(this, "Knife only", true, "Only detect suspects holding an item whose display name matches the knife names below.\nFixes the outdated numeric item IDs not matching on modern Minecraft.");
        this.knifeNames = OptionalItemFilter.createWithDescription(this, "murder-knife-names", "Knife Names", "Item display names that identify a murderer's knife", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("Knife", "Dagger"));
        this.esp = ToggleSetting.create(this, "ESP", true, "Draws a box around suspected murderers.");
        this.espColor = ColorPicker.create(this, "ESP color", new Color(255, 0, 0, 90));
        this.knifeFinder = ToggleSetting.create(this, "Knife finder", true, "Shows dropped knives on the ground and their distance.");
        this.knifeColor = ColorPicker.create(this, "Knife color", new Color(255, 200, 60, 120));
        this.proximityAlert = ToggleSetting.create(this, "Proximity alert", true, "Notifies when a suspected murderer gets too close.");
        this.alertRange = SliderSetting.createWithDescription(this, "Alert range", "#.#", "m", 0.0, 6.0, 32.0, "Distance at which the proximity alert triggers.");
        this.calloutTimer = new TimerUtil();
        this.alertTimer = new TimerUtil();
        this.callout.addDependentValues(this.delay, this.messages);
        this.knifeOnly.addDependentValues(this.knifeNames);
        this.esp.addDependentValues(this.espColor);
        this.knifeFinder.addDependentValues(this.knifeColor);
        this.proximityAlert.addDependentValues(this.alertRange);
        this.addValue(this.callout, this.delay, this.messages, this.murdererItems, this.knifeOnly, this.knifeNames, this.esp, this.espColor, this.knifeFinder, this.knifeColor, this.proximityAlert, this.alertRange);
    }
}
