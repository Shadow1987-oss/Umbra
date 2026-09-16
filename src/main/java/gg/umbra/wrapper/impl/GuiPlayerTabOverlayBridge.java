package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MGuiPlayerTabOverlay;
import gg.umbra.wrapper.Wrapper;

import java.util.Comparator;

public class GuiPlayerTabOverlayBridge
extends Wrapper {
    public static Ordering O() {
        if (ForgeVersion.MC_1_20_6.d()) {
            throw new RuntimeException("Use getPlayerInfoMap_50() instead");
        }
        return new Ordering(MGuiPlayerTabOverlay.b(Wrapper.umbraInstance.getMappings().hP));
    }

    private static RuntimeException a(RuntimeException runtimeException) {
        return runtimeException;
    }

    public String Z(PlayerInfo playerInfo) {
        if (ForgeVersion.MC_1_20_6.d()) {
            ITextComponent iTextComponent = new ITextComponent(MGuiPlayerTabOverlay.d(GuiPlayerTabOverlayBridge.umbraInstance.getMappings().hP, this.I, playerInfo.getObject()));
            return iTextComponent.getFormattedText();
        }
        return MGuiPlayerTabOverlay.d(GuiPlayerTabOverlayBridge.umbraInstance.getMappings().hP, this.getObject(), playerInfo.getObject());
    }

    public GuiPlayerTabOverlayBridge(Object object) {
        super(object);
    }

    public ITextComponent U(PlayerInfo playerInfo, TextComponent textComponent) {
        if (ForgeVersion.MC_1_16_5.v()) {
            throw new UnsupportedOperationException("This method is only for versions below 1.16.5");
        }
        return new ITextComponent(MGuiPlayerTabOverlay.Z(Wrapper.umbraInstance.getMappings().hP, this.getObject(), playerInfo.getObject(), textComponent.getObject()));
    }

    public static Comparator T() {
        return (Comparator)MGuiPlayerTabOverlay.b(Wrapper.umbraInstance.getMappings().hP);
    }

    public TextComponentBaseBridge L(PlayerInfo playerInfo, TextComponentBaseBridge textComponentBaseBridge) {
        if (ForgeVersion.MC_1_20_6.v()) {
            throw new UnsupportedOperationException("This method is only for versions 1.16.5 and above");
        }
        return new TextComponentBaseBridge(MGuiPlayerTabOverlay.Z(Wrapper.umbraInstance.getMappings().hP, this.getObject(), playerInfo.getObject(), textComponentBaseBridge.getObject()));
    }
}
