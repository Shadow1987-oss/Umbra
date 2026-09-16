package gg.umbra.mapping;

import gg.umbra.event.impl.EventPlayerUseItem;
import gg.umbra.mapping.InsertedEventCallback;
import gg.umbra.wrapper.impl.EntityPlayer;
import gg.umbra.wrapper.impl.EnumHand;
import gg.umbra.wrapper.impl.ItemStack;

public class PlayerUseItemCallback
implements InsertedEventCallback {
    private final EntityPlayer h;
    private final EnumHand r;

    @Override
    public boolean fire() {
        ItemStack itemStack = this.h.i(this.r);
        EventPlayerUseItem eventPlayerUseItem = new EventPlayerUseItem(itemStack.getObject());
        return eventPlayerUseItem.fire();
    }

    public PlayerUseItemCallback(Object object, Object object2) {
        this.h = new EntityPlayer(object);
        this.r = new EnumHand(object2);
    }
}

