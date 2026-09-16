package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventKeyPress;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.SyntheticAttackRequestEvent;
import gg.umbra.settings.ClientSettings;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.KeystrokesHudFrame;
import gg.umbra.unmap.ModeOption;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.OptionSetting;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;

public class KeystrokesHudModule
extends HudModule {
    public final ModeOption keyboardKeyStyle = new ModeOption("Keyboard");
    public final ModeOption mouseIconStyle;
    public final OptionSetting mouseStyle;
    public final OptionSetting keyStyle;
    public final ToggleSetting showCpsOnly;
    public final ModeOption mouseButtonStyle;
    public final ToggleSetting showSpacebar;
    public final ModeOption arrowKeyStyle = new ModeOption("Arrow");

    @Listen
    public void onKeyPress(EventKeyPress eventKeyPress) {
        KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
        if (keystrokesHudFrame == null) {
            return;
        }
        keystrokesHudFrame.handleKeyEvent(eventKeyPress);
    }

    public KeystrokesHudModule() {
        super("Keystrokes", HudModuleGroup.HUD, "keystrokes", KeystrokesHudFrame.class);
        this.keyStyle = OptionSetting.create((Object)this, "Key Style", this.keyboardKeyStyle, this.keyboardKeyStyle, this.arrowKeyStyle);
        this.mouseButtonStyle = new ModeOption("Button");
        this.mouseIconStyle = new ModeOption("Icon");
        this.mouseStyle = OptionSetting.create((Object)this, "Mouse Style", this.mouseButtonStyle, this.mouseButtonStyle, this.mouseIconStyle);
        this.showSpacebar = ToggleSetting.create(this, "Show Spacebar", true);
        this.showCpsOnly = ToggleSetting.create(this, "Show CPS Only", false);
        this.setSuffix("Shows when your movement keys or mouse buttons are pressed\nAs well as mouse clicks per second");
        this.addValue(this.keyStyle, this.mouseStyle, this.showSpacebar, this.showCpsOnly);
    }

    public void updateKeyState(KeyBinding keyBinding, boolean pressed) {
        KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
        if (keystrokesHudFrame == null) {
            return;
        }
        keystrokesHudFrame.updateKeyState(keyBinding, pressed);
    }


    @Listen(priority=EventPriority.HIGHEST)
    public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
        if (event.isCanceled() || Minecraft.currentScreen().isNotNull()) {
            return;
        }
        KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
        if (keystrokesHudFrame == null) {
            return;
        }
        keystrokesHudFrame.registerSyntheticAttack();
    }

    @Listen
    public void onMouseButton(EventMouseButton eventMouseButton) {
        KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
        if (keystrokesHudFrame == null) {
            return;
        }
        keystrokesHudFrame.handleMouseEvent(eventMouseButton);
        if (!eventMouseButton.getButtonState()) {
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        keystrokesHudFrame.getCpsCounter().recordClick(eventMouseButton.getButton());
    }
}

