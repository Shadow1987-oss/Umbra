package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.utils.datas.BlockData;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MAbstractBlockState
extends Mapping {
    private MappingMethod getShapeMethod;
    private MappingMethod isSuffocatingMethod;

    public MAbstractBlockState() {
        this(BlockData.W());
    }

    private MAbstractBlockState(String[] controlFlowState) {
        super(MappedClasses.Fj);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_20_6.d()) {
                this.getShapeMethod = this.Y("getShape", true, MappedClasses.la, new Class[]{MappedClasses.zJ, MappedClasses.lf});
            } else {
                this.getShapeMethod = this.Y("func_196954_c", Wrapper.isNativeAvailable, MappedClasses.la, new Class[]{MappedClasses.zJ, MappedClasses.lf});
                this.isSuffocatingMethod = this.Y("func_229980_m_", Wrapper.isNativeAvailable, Boolean.TYPE, new Class[]{MappedClasses.zJ, MappedClasses.lf});
            }
            return;
        }
        this.isSuffocatingMethod = this.Y("func_229980_m_", Wrapper.isNativeAvailable, Boolean.TYPE, new Class[]{MappedClasses.zJ, MappedClasses.lf});
    }

    public Object getShape(Object blockState, Object blockReader, Object blockPosition) {
        return this.getShapeMethod.invokeObject(blockState, blockReader, blockPosition);
    }

    public boolean isSuffocating(Object blockState, Object blockReader, Object blockPosition) {
        return this.isSuffocatingMethod.invokeBoolean(blockState, blockReader, blockPosition);
    }

}
