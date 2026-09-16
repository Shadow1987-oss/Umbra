package gg.umbra.mapping;

import gg.umbra.event.impl.EventNameFormat;
import gg.umbra.mapping.InsertedEventCallback;
import gg.umbra.visual.entity.RenderEntityContextCache;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.ITextComponent;

public class PlayerNameFormatCallback
implements InsertedEventCallback {
    public static boolean s = false;
    private final Object U;
    private ITextComponent I;

    private ITextComponent grabDisplayName(EntityPlayer entityPlayer) {
        s = true;
        ITextComponent iTextComponent = RenderEntityContextCache.getDisplayName(entityPlayer);
        s = false;
        return iTextComponent;
    }

    @Override
    public boolean fire() {
        if (s) {
            s = false;
            return false;
        }
        EntityPlayer entityPlayer = new EntityPlayer(this.U);
        ITextComponent iTextComponent = RenderEntityContextCache.getCustomName(entityPlayer);
        if (iTextComponent != null) {
            this.I = iTextComponent;
        } else {
            iTextComponent = this.grabDisplayName(entityPlayer);
            this.I = new ITextComponent(iTextComponent);
            EventNameFormat eventNameFormat = new EventNameFormat(entityPlayer, this.I);
            eventNameFormat.fire();
            this.I = eventNameFormat.getDisplayName();
            RenderEntityContextCache.setCustomName(entityPlayer, this.I);
        }
        return true;
    }

    public PlayerNameFormatCallback(Object object) {
        this.U = object;
    }

    public ITextComponent getDisplayName() {
        return this.I;
    }


    public Object getRawDisplayName() {
        return this.I.getObject();
    }
}

