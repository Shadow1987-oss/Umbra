/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.friend;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventChat;
import gg.umbra.event.impl.EventNameFormat;
import gg.umbra.event.impl.EventPlayerTabOverlayDisplayNameLegacy;
import gg.umbra.friend.FriendAliasDisplayNameListener;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.worldmods.cheststeal.ChestStealInventoryState;
import gg.umbra.wrapper.impl.ITextComponent;
import gg.umbra.wrapper.impl.TextComponentString;
import java.util.HashSet;
import java.util.Set;

public final class FriendAliasEventListener
extends FriendAliasDisplayNameListener {
    private void replaceAliasesInComponent(ITextComponent component) {
        Set<TextComponentString> textComponents = this.collectTextComponents(component, new HashSet<TextComponentString>());
        for (TextComponentString textComponent : textComponents) {
            String text = textComponent.getText();
            String replacedText = this.getReplacedDisplayName(text, this.getTargetedFriends());
            if (replacedText == null || text.equalsIgnoreCase(replacedText)) continue;
            textComponent.setText(replacedText);
        }
    }

    @Listen
    public void onNameFormat(EventNameFormat event) {
        if (!(this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends())) {
            return;
        }
        ITextComponent displayName = event.getDisplayName();
        if (displayName.isNull() || !displayName.isInstance(MappedClasses.z9)) {
            return;
        }
        TextComponentString rootText = new TextComponentString(displayName.getObject());
        this.replaceAliasesInComponent(rootText);
        for (ITextComponent sibling : rootText.G()) {
            if (sibling.isNull() || !sibling.isInstance(MappedClasses.z9)) continue;
            TextComponentString siblingText = new TextComponentString(sibling.getObject());
            this.replaceAliasesInComponent(siblingText);
        }
    }

    @Listen
    public void onChat(EventChat event) {
        if (!this.isAliasSpoofEnabled() || !this.hasFriends()) {
            return;
        }
        this.replaceAliasesInComponent(event.getMessage());
    }

    @Listen
    public void onLegacyTabDisplayName(EventPlayerTabOverlayDisplayNameLegacy event) {
        if (!(this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends())) {
            return;
        }
        String displayName = event.getDisplayName();
        String replacedName = this.getReplacedDisplayName(displayName, this.getTargetedFriends());
        if (replacedName != null && !displayName.equalsIgnoreCase(replacedName)) {
            event.setDisplayName(replacedName);
        }
    }

    private Set<TextComponentString> collectTextComponents(ITextComponent component, Set<TextComponentString> result) {
        if (component.isInstance(MappedClasses.ux)) {
            ChestStealInventoryState translationComponent = new ChestStealInventoryState(component.getObject());
            for (Object siblingHandle : translationComponent.getSiblings()) {
                ITextComponent sibling = new ITextComponent(siblingHandle);
                if (sibling.isNull() || !sibling.isInstance(MappedClasses.Yr)) continue;
                this.collectTextComponents(sibling, result);
            }
        }
        for (ITextComponent sibling : component.G()) {
            if (sibling.isNull() || !sibling.isInstance(MappedClasses.z9)) continue;
            TextComponentString textComponent = new TextComponentString(sibling.getObject());
            this.collectTextComponents(textComponent, result);
        }
        if (component.isInstance(MappedClasses.z9)) {
            result.add(new TextComponentString(component.getObject()));
        }
        return result;
    }
}

