package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventChat;
import gg.umbra.event.impl.EventChatMessageRender;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.worldmods.cheststeal.ChestStealInventoryState;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.StringValue;
import gg.umbra.wrapper.impl.ITextComponent;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.TextComponentString;
import java.util.HashSet;
import java.util.Set;

public class NameProtect
extends HackModule {
    private final StringValue alias;
    private final ToggleSetting protectOwnName;

    public NameProtect() {
        super("NameProtect", 0, Category.UTILITY, "Shows an alias instead of your username in chat.");
        this.alias = StringValue.create(this, "Alias", "Player");
        this.protectOwnName = ToggleSetting.create(this, "Protect name", true, "Replace your username in chat messages.");
        this.addValue(this.alias, this.protectOwnName);
    }

    @Override
    public String getId() {
        return "nameprotect";
    }

    private void replaceInComponent(ITextComponent component) {
        String ownName = Minecraft.getSessionUsername();
        if (ownName == null || ownName.isEmpty()) {
            return;
        }
        String replacement = (String) this.alias.getValue();
        Set<TextComponentString> textComponents = this.collectTextComponents(component, new HashSet<TextComponentString>());
        for (TextComponentString textComponent : textComponents) {
            String text = textComponent.getText();
            if (text == null || text.isEmpty()) {
                continue;
            }
            String replaced = text.replace(ownName, replacement);
            if (replaced.equals(text)) {
                continue;
            }
            textComponent.setText(replaced);
        }
    }

    private Set<TextComponentString> collectTextComponents(ITextComponent component, Set<TextComponentString> result) {
        if (component.isInstance(MappedClasses.ux)) {
            ChestStealInventoryState translationComponent = new ChestStealInventoryState(component.getObject());
            for (Object siblingHandle : translationComponent.getSiblings()) {
                ITextComponent sibling = new ITextComponent(siblingHandle);
                if (sibling.isNull() || !sibling.isInstance(MappedClasses.Yr)) {
                    continue;
                }
                this.collectTextComponents(sibling, result);
            }
        }
        for (ITextComponent sibling : component.G()) {
            if (sibling.isNull() || !sibling.isInstance(MappedClasses.z9)) {
                continue;
            }
            TextComponentString textComponent = new TextComponentString(sibling.getObject());
            this.collectTextComponents(textComponent, result);
        }
        if (component.isInstance(MappedClasses.z9)) {
            result.add(new TextComponentString(component.getObject()));
        }
        return result;
    }

    @Listen
    public void onChat(EventChat event) {
        if (!this.protectOwnName.getEffectiveValue().booleanValue() || event.getMessage() == null) {
            return;
        }
        this.replaceInComponent(event.getMessage());
    }

    @Listen
    public void onChatMessageRender(EventChatMessageRender event) {
        if (!this.protectOwnName.getEffectiveValue().booleanValue()) {
            return;
        }
        this.replaceInComponent(event.getContentComponent());
        event.setOutputContentComponent(event.getContentComponent());
    }
}
