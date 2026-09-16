package gg.umbra.mapping.mappings;

import gg.umbra.Umbra;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MTileEntityMobSpawner
extends Mapping {
    private static int[] controlFlowState;
    private final MappingMethod getSpawnerBaseLogicMethod;

    public static int[] getMobSpawnerControlFlowState() {
        return controlFlowState;
    }

    public static void setMobSpawnerControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public Object getSpawnerBaseLogic(Object mobSpawnerTileEntity) {
        return this.getSpawnerBaseLogicMethod.invokeObject(mobSpawnerTileEntity, new Object[0]);
    }

    static {
        MTileEntityMobSpawner.setMobSpawnerControlFlowState(null);
    }


    public MTileEntityMobSpawner() {
        this(MTileEntityMobSpawner.getMobSpawnerControlFlowState());
    }

    private MTileEntityMobSpawner(int[] controlFlowState) {
        super(MappedClasses.MOB_SPAWNER_TILE_ENTITY);
        if (controlFlowState != null) {
            if (Umbra.INSTANCE.isVanillaMinecraftPresent()) {
                Class[] parameterTypes = new Class[]{};
                Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
                boolean remap = true;
                String methodName = "getSpawnerBaseLogic";
                this.Y(methodName, remap, returnType, parameterTypes);
            }
            Class[] parameterTypes = new Class[]{};
            Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
            boolean remap = Wrapper.isNativeAvailable;
            String methodName = "func_145881_a";
            this.getSpawnerBaseLogicMethod = this.Y(methodName, remap, returnType, parameterTypes);
            return;
        }
        if (Umbra.INSTANCE.isVanillaMinecraftPresent() && ForgeVersion.MC_1_7_10.Y()) {
            Class[] parameterTypes = new Class[]{};
            Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
            boolean remap = true;
            String methodName = "getSpawnerBaseLogic";
            this.getSpawnerBaseLogicMethod = this.Y(methodName, remap, returnType, parameterTypes);
        } else {
            Class[] parameterTypes = new Class[]{};
            Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
            boolean remap = Wrapper.isNativeAvailable;
            String methodName = "func_145881_a";
            this.getSpawnerBaseLogicMethod = this.Y(methodName, remap, returnType, parameterTypes);
        }
    }
}
