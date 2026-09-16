package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MChatMessageRenderTarget
extends Mapping {
    private static boolean chatMessageControlFlowState;
    public MappingMethod addMessageMethod;
    private static final String ADD_MESSAGE_METHOD_NAME;

    public static boolean getChatMessageControlFlowState() {
        return chatMessageControlFlowState;
    }

    public static boolean getDisabledControlFlowState() {
        boolean bl = MChatMessageRenderTarget.getChatMessageControlFlowState();
        return false;
    }

    public static void setChatMessageControlFlowState(boolean state) {
        chatMessageControlFlowState = state;
    }


    public MChatMessageRenderTarget() {
        this(MChatMessageRenderTarget.getDisabledControlFlowState());
    }

    private MChatMessageRenderTarget(boolean bl) {
        super(MappedClasses.d);
        if (bl) {
            if (ForgeVersion.MC_26_1.v()) {
                Class[] classArray = new Class[]{MappedClasses.Yr, MappedClasses.uh, MappedClasses.zF};
                Class<Void> clazz = Void.TYPE;
                boolean bl2 = true;
                String string = ADD_MESSAGE_METHOD_NAME;
                MChatMessageRenderTarget mChatMessageRenderTarget = this;
                this.addMessageMethod = mChatMessageRenderTarget.Y(string, bl2, clazz, classArray);
            }
            GuiComponent.setLegacyComponentState(new GuiComponent[2]);
            return;
        }
        if (ForgeVersion.MC_26_1.v()) {
            Class[] classArray = new Class[]{MappedClasses.Yr, MappedClasses.uh, MappedClasses.zF};
            Class<Void> clazz = Void.TYPE;
            boolean bl3 = true;
            String string = ADD_MESSAGE_METHOD_NAME;
            MChatMessageRenderTarget mChatMessageRenderTarget = this;
            this.addMessageMethod = mChatMessageRenderTarget.Y(string, bl3, clazz, classArray);
        }
    }

    static {
        MChatMessageRenderTarget.setChatMessageControlFlowState(true);
        ADD_MESSAGE_METHOD_NAME = "addMessage";
    }
}
