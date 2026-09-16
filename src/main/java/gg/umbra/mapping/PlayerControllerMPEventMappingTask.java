package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBedBreakerUpdate;
import gg.umbra.event.impl.EventPlayerUseItem;
import gg.umbra.event.impl.EventPostAttack;
import gg.umbra.event.impl.EventPreAttack;
import gg.umbra.event.impl.EventWindowClick;
import gg.umbra.wrapper.impl.ForgeVersion;

public class PlayerControllerMPEventMappingTask
extends JavassistMappingTask {
    public static final boolean U = ForgeVersion.MC_1_16_5.d() && (Umbra.INSTANCE.isForgeAbsent() || Umbra.INSTANCE.isVanillaMinecraftPresent());

    public PlayerControllerMPEventMappingTask() {
        super(MappedClasses.ld);
    }

    @Override
    public void transform() {
        Object object;
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().hj.c;
        if (ForgeVersion.MC_1_12_2.v()) {
            this.O(mappingMethod, EventPlayerUseItem.class, "$3", "false");
        } else if (ForgeVersion.MC_1_12_2.d()) {
            object = ForgeVersion.MC_1_20_6.d() ? "$1, $2" : "$1, $3";
            this.O(mappingMethod, PlayerUseItemCallback.class, (String)object, MappedClasses.zr.getName() + "." + Umbra.INSTANCE.getMappings().enumActionResult.passField.getResolvedName());
        }
        this.c(Umbra.INSTANCE.getMappings().hj.L, EventBedBreakerUpdate.class, "");
        if (!U) {
            object = Umbra.INSTANCE.getMappings().hj.i;
            this.c((MappingMethod)object, EventPreAttack.class, "$2");
            this.k((MappingMethod)object, EventPostAttack.class, "$2");
        }
        this.O(Umbra.INSTANCE.getMappings().hj.I, EventWindowClick.class, "$0", "null");
    }

}

