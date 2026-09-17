package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventChat;
import gg.umbra.event.impl.EventChatMessageRender;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.Arrays;
import java.util.List;

public class AutoReply
extends HackModule {
    private final OptionalItemFilter triggers;
    private final OptionalItemFilter replies;
    private final SliderSetting delay;
    private final SliderSetting cooldown;
    private final ToggleSetting direct;
    private final TimerUtil pendingTimer = new TimerUtil();
    private final TimerUtil cooldownTimer = new TimerUtil();
    private boolean pending = false;
    private String pendingSender = null;

    public AutoReply() {
        super("AutoReply", -1175968219, Category.UTILITY, "Answers whispers for you so you never look AFK.");
        this.triggers = OptionalItemFilter.createWithDescription(this, "autoreply-triggers", "Triggers", "Phrases that mark a message as a private message", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("whispers", "whispered", "From ", "says:"));
        this.replies = OptionalItemFilter.createWithDescription(this, "autoreply-replies", "Replies", "Random reply to send", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("afk brb", "one sec", "im here", "busy rn"));
        this.delay = SliderSetting.createWithDescription(this, "Reply delay", "#", "ms", 100.0, 800.0, 5000.0, "Delay before sending the reply.");
        this.cooldown = SliderSetting.createWithDescription(this, "Cooldown", "#", "s", 5.0, 30.0, 300.0, "Seconds to wait between replies.");
        this.direct = ToggleSetting.create(this, "Direct reply", false, "Reply with /msg <sender> instead of a plain chat message.");
        this.addValue(this.triggers, this.replies, this.delay, this.cooldown, this.direct);
    }

    @Override
    public String getId() {
        return "autoreply";
    }

    private String extractSender(String text) {
        if (text.startsWith("From ")) {
            int colon = text.indexOf(58);
            if (colon > 5) {
                return text.substring(5, colon).trim();
            }
        }
        int whispers = text.indexOf(" whispers");
        if (whispers > 0) {
            return text.substring(0, whispers).trim();
        }
        int arrow = text.indexOf(" -> you:");
        if (arrow > 0) {
            return text.substring(0, arrow).trim();
        }
        return null;
    }

    private void onMessage(String text) {
        if (!this.isEnabled() || this.pending || !this.cooldownTimer.hasTimeElapsed((long) (((Double) this.cooldown.getValue()).doubleValue() * 1000.0)) || text == null || text.isEmpty()) {
            return;
        }
        String lower = text.toLowerCase();
        boolean matched = false;
        for (String trigger : this.triggers.getEnabledValues()) {
            if (!lower.contains(trigger.toLowerCase())) {
                continue;
            }
            matched = true;
            break;
        }
        if (!matched) {
            return;
        }
        this.pendingSender = this.extractSender(text);
        this.pending = true;
        this.pendingTimer.reset();
    }

    @Listen
    public void onChat(EventChat event) {
        if (event.getMessage() != null) {
            this.onMessage(event.getMessage().getFormattedText());
        }
    }

    @Listen
    public void onChatMessageRender(EventChatMessageRender event) {
        this.onMessage(event.getContentComponent().getFormattedText());
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (!this.pending || !this.pendingTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        this.pending = false;
        this.cooldownTimer.reset();
        List<String> replyList = this.replies.getEnabledValues();
        if (replyList.isEmpty()) {
            return;
        }
        String reply = replyList.get((int) (Math.random() * (double) replyList.size()));
        String sender = this.pendingSender;
        if (this.direct.getEffectiveValue().booleanValue() && sender != null && !sender.isEmpty()) {
            Minecraft.thePlayer().sendChatMessage("/msg " + sender + " " + reply);
        } else {
            Minecraft.thePlayer().sendChatMessage(reply);
        }
    }
}
