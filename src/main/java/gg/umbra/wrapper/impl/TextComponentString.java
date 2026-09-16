package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTextComponentString;

public class TextComponentString
extends ITextComponent {
    public TextComponentString(Object handle) {
        super(handle);
    }

    public String getText() {
        return MTextComponentString.getText(TextComponentString.umbraInstance.getMappingsMapperCompat().textComponentString, this.getObject());
    }

    public static TextComponentString create(String text) {
        return new TextComponentString(MTextComponentString.create(TextComponentString.umbraInstance.getMappingsMapperCompat().textComponentString, text));
    }

    public void setText(String text) {
        MTextComponentString.setText(TextComponentString.umbraInstance.getMappingsMapperCompat().textComponentString, this.getObject(), text);
    }
}
