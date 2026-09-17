package gg.umbra.hacks.pvp;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.value.ItemFilterList;
import gg.umbra.wrapper.impl.CPacketPlayerDigging;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;

public class BlockHit
extends HackModule {
    public final ItemFilterList allowedItems = ItemFilterList.create(this, "noitemrelease-alloweditems", "Allowed items", ItemFilterList.ALLOW_LIST_COLOR, new ItemLimitData("swords"), new ItemLimitData("food"), new ItemLimitData("potions"));

    public BlockHit() {
        super("NoItemRelease", -2420426, Category.OTHER);
        this.addValue(this.allowedItems);
    }

    @Override
    public String getId() {
        return "noitemrelease";
    }

    @Listen
    public void onPacketSend(EventPacketSend event) {
        Packet packet = event.getPacket();
        boolean inGui = Minecraft.l$src$Z$b9uwii();
        if (inGui) {
            if (packet.isInstance(MappedClasses.DN)
                    && new CPacketPlayerDigging(packet).isReleaseUseItem()) {
                event.setCancelled(true);
            }
            return;
        }
        if (packet.isInstance(MappedClasses.DN)
                && new CPacketPlayerDigging(packet).isReleaseUseItem()
                && this.allowedItems.matches(Minecraft.thePlayer().B$src$Lgg_umbra_wrapper_impl_ItemStack_$impdvt())) {
            event.setCancelled(true);
        }
    }
}
