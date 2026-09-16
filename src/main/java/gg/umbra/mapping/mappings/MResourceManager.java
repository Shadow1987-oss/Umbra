package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import java.util.Collection;

public class MResourceManager
extends Mapping {
    private static final String GET_SELECTED_IDS_METHOD_NAME;
    private final MappingMethod getSelectedIdsMethod;
    private static int packRepositoryControlFlowState;

    public MResourceManager() {
        this(MResourceManager.getPackRepositoryControlFlowState());
    }

    private MResourceManager(int controlFlowState) {
        super(MappedClasses.FF);
        int currentControlFlowState = controlFlowState;
        Class[] parameterTypes = new Class[]{};
        Class<Collection> returnType = Collection.class;
        boolean methodPublic = true;
        String methodName = GET_SELECTED_IDS_METHOD_NAME;
        MResourceManager mapping = this;
        this.getSelectedIdsMethod = mapping.Y(methodName, methodPublic, returnType, parameterTypes);
    }

    public static int getPackRepositoryControlFlowState() {
        return packRepositoryControlFlowState;
    }

    static {
        MResourceManager.setPackRepositoryControlFlowState(0);
        GET_SELECTED_IDS_METHOD_NAME = "getSelectedIds";
    }

    public Object getSelectedIds(Object repositoryHandle) {
        return this.getSelectedIdsMethod.invokeObject(repositoryHandle, new Object[0]);
    }

    public static int getControlFlowConstant() {
        int controlFlowState = MResourceManager.getPackRepositoryControlFlowState();
        return 59;
    }


    public static void setPackRepositoryControlFlowState(int state) {
        packRepositoryControlFlowState = state;
    }
}

