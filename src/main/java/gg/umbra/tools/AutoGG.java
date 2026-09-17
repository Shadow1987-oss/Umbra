package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventChat;
import gg.umbra.event.impl.EventChatMessageRender;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.Arrays;

public class AutoGG
extends HackModule {
    private final SliderSetting delay;
    private final OptionalItemFilter triggers;
    private final TimerUtil pendingTimer = new TimerUtil();
    private boolean pending = false;
    private long lastSentAt = 0L;

    public AutoGG() {
        super("AutoGG", -371460927, Category.UTILITY, "Says gg for you when the game ends.");
        this.delay = SliderSetting.createWithDescription(this, "Delay", "#", "ms", 0.0, 700.0, 5000.0, "Delay before sending gg after the game ends.");
        this.triggers = OptionalItemFilter.createWithDescription(this, "autogg-triggers", "Game End Triggers", "Phrases that detect the end of a game", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("you died", "you won", "1st killer", "1st place", "victory", "defeat", "a player has won the game", "the game has ended"));
        this.addValue(this.delay, this.triggers);
    }

    @Override
    public String getId() {
        return "autogg";
    }

    private boolean isGameEndMessage(String message) {
        String lower = message.toLowerCase();
        for (String trigger : this.triggers.getEnabledValues()) {
            if (lower.contains(trigger.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Listen
    public void onChat(EventChat event) {
        if (!this.isEnabled() || this.pending) {
            return;
        }
        String message = event.getMessage().getFormattedText();
        if (message == null || message.isEmpty() || !this.isGameEndMessage(message)) {
            return;
        }
        if (System.currentTimeMillis() - this.lastSentAt < 60000L) {
            return;
        }
        this.pending = true;
        this.pendingTimer.reset();
    }

    @Listen
    public void onChatMessageRender(EventChatMessageRender event) {
        if (!this.isEnabled() || this.pending) {
            return;
        }
        String message = event.getContentComponent().getFormattedText();
        if (message == null || message.isEmpty() || !this.isGameEndMessage(message)) {
            return;
        }
        if (System.currentTimeMillis() - this.lastSentAt < 60000L) {
            return;
        }
        this.pending = true;
        this.pendingTimer.reset();
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (!this.pending || !this.pendingTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        this.pending = false;
        this.lastSentAt = System.currentTimeMillis();
        Minecraft.thePlayer().sendChatMessage("gg");
    }
}
