package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MPacketIdFactory;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MCPacketAnimation
extends Mapping {
    private MappingMethod noArgConstructor;
    private MappingMethod handConstructor;

    public Object createAnimationPacket() {
        return this.noArgConstructor.newInstance(new Object[0]);
    }

    public MCPacketAnimation() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketAnimation(GuiComponent[] controlFlowState) {
        super(MappedClasses.VF);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_12_2.d()) {
                this.handConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{MappedClasses.Yf});
            } else {
                this.noArgConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{});
            }
            return;
        }
        this.noArgConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{});
    }

    public Object createAnimationPacket(Object hand) {
        return this.handConstructor.newInstance(hand);
    }

}
