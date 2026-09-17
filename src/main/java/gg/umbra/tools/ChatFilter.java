package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventChat;
import gg.umbra.event.impl.EventChatMessageRender;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.ITextComponent;
import java.util.Arrays;

public class ChatFilter
extends HackModule {
    private final OptionalItemFilter filters;

    public ChatFilter() {
        super("ChatFilter", 2117988976, Category.UTILITY, "Hides chat lines that contain your blocked words.");
        this.filters = OptionalItemFilter.createWithDescription(this, "chatfilter-keywords", "Blocked Words", "Messages containing any of these are hidden", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("discord.gg", "advertise", "www."));
        this.addValue(this.filters);
    }

    @Override
    public String getId() {
        return "chatfilter";
    }

    private boolean matches(String message) {
        String lower = message.toLowerCase();
        for (String filter : this.filters.getEnabledValues()) {
            if (lower.contains(filter.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Listen
    public void onChat(EventChat event) {
        if (event.getMessage() == null) {
            return;
        }
        String message = event.getMessage().getFormattedText();
        if (message != null && this.matches(message)) {
            event.setCancelled(true);
        }
    }

    @Listen
    public void onChatMessageRender(EventChatMessageRender event) {
        String message = event.getContentComponent().getFormattedText();
        if (message != null && this.matches(message)) {
            event.setOutputContentComponent(ITextComponent.P(""));
        }
    }
}
