package gg.umbra.worldmods.cheststeal;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MTextComponentTranslation;
import gg.umbra.unmap.TextComponentBase;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.TextComponent;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ChestStealInventoryState
extends TextComponentBase {
    private static final String UNSUPPORTED_MESSAGE = "This method is available on 1.20.6 and later.";

    public static ChestStealInventoryState createTranslation(String translationKey, Object ... formatArguments) {
        return new ChestStealInventoryState(MTextComponentTranslation.B(ChestStealInventoryState.umbraInstance.getMappings().D_, translationKey, formatArguments));
    }

    public String getTranslationKey() {
        if (ForgeVersion.MC_1_16_5.v()) {
            Umbra.notifyNativeStackTrace();
        }
        return MTextComponentTranslation.C(ChestStealInventoryState.umbraInstance.getMappings().D_, this.getObject());
    }

    public String getFallback() {
        if (ForgeVersion.MC_1_20_6.v()) {
            Umbra.notifyNativeStackTrace();
        }
        return MTextComponentTranslation.a(ChestStealInventoryState.umbraInstance.getMappings().D_, this.getObject());
    }

    public static ChestStealInventoryState createTranslationWithFallback(String translationKey, @Nullable String fallback, Object[] formatArguments) {
        if (ForgeVersion.MC_1_20_6.v()) {
            Umbra.notifyNativeStackTrace();
            throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
        }
        return new ChestStealInventoryState(MTextComponentTranslation.k(ChestStealInventoryState.umbraInstance.getMappings().D_, translationKey, fallback, formatArguments));
    }

    @Override
    public String getFormattedText() {
        if (ForgeVersion.MC_1_20_6.d()) {
            TextComponent textComponent = TextComponent.p(this.I);
            return textComponent.getFormattedText();
        }
        return super.getFormattedText();
    }

    public Object[] getFormatArguments() {
        if (ForgeVersion.MC_1_16_5.v()) {
            Umbra.notifyNativeStackTrace();
        }
        return MTextComponentTranslation.x(ChestStealInventoryState.umbraInstance.getMappings().D_, this.getObject());
    }

    public ChestStealInventoryState(Object component) {
        super(component);
    }

    public List<?> getSiblings() {
        return ChestStealInventoryState.umbraInstance.getMappings().D_.k(this.getObject());
    }
}

