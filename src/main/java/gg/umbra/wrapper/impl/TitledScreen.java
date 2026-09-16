package gg.umbra.wrapper.impl;

public class TitledScreen
extends Screen {
    public String getDisplayedTitle() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return "";
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            ITextComponent title = new ITextComponent(
                    TitledScreen.umbraInstance.getMappingsMapperCompat().titledScreen.getDisplayedTitle(this.I));
            return title.getFormattedText();
        }
        return (String)TitledScreen.umbraInstance.getMappingsMapperCompat().titledScreen.getDisplayedTitle(this.I);
    }

    public TitledScreen(Object titledScreenHandle) {
        super(titledScreenHandle);
    }

}

