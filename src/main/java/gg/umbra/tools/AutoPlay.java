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
import gg.umbra.value.StringValue;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.Arrays;

public class AutoPlay
extends HackModule {
    private final StringValue mode;
    private final SliderSetting delay;
    private final OptionalItemFilter triggers;
    private final TimerUtil pendingTimer = new TimerUtil();
    private boolean pending = false;
    private long lastPlayedAt = 0L;

    public AutoPlay() {
        super("AutoPlay", 0, Category.UTILITY, "Queues the next game with /play as soon as one ends.");
        this.mode = StringValue.create(this, "Mode", "bedwars_eight_one");
        this.delay = SliderSetting.createWithDescription(this, "Delay", "#", "ms", 0.0, 700.0, 5000.0, "Delay before sending /play after the game ends.");
        this.triggers = OptionalItemFilter.createWithDescription(this, "autoplay-triggers", "Game End Triggers", "Phrases that detect the end of a game", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("you died", "you won", "1st killer", "1st place", "victory", "defeat", "a player has won the game", "the game has ended"));
        this.addValue(this.mode, this.delay, this.triggers);
    }

    @Override
    public String getId() {
        return "autoplay";
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

    private void onGameEndDetected(String message) {
        if (!this.isEnabled() || this.pending || message == null || message.isEmpty() || !this.isGameEndMessage(message)) {
            return;
        }
        if (System.currentTimeMillis() - this.lastPlayedAt < 60000L) {
            return;
        }
        this.pending = true;
        this.pendingTimer.reset();
    }

    @Listen
    public void onChat(EventChat event) {
        if (event.getMessage() != null) {
            this.onGameEndDetected(event.getMessage().getFormattedText());
        }
    }

    @Listen
    public void onChatMessageRender(EventChatMessageRender event) {
        this.onGameEndDetected(event.getContentComponent().getFormattedText());
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (!this.pending || !this.pendingTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        this.pending = false;
        this.lastPlayedAt = System.currentTimeMillis();
        Minecraft.thePlayer().sendChatMessage("/play " + (String) this.mode.getValue());
    }
}
