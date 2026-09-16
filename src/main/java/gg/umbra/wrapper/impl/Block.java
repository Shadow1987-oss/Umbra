package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MBlock;
import gg.umbra.wrapper.Wrapper;

public class Block
extends Wrapper {
    public float c() {
        return MBlock.w(Block.umbraInstance.getMappings().qg, this.I);
    }

    public static Block t(String string) {
        if (ForgeVersion.MC_1_16_5.d()) {
            if (!string.startsWith("block.minecraft.")) {
                string = "block.minecraft." + string;
            }
            Iterable iterable = (Iterable)MBlock.N(Block.umbraInstance.getMappings().qg);
            for (Object t : iterable) {
                Block block = new Block(t);
                if (block.U() == null || !block.U().equalsIgnoreCase(string)) continue;
                return block;
            }
            return null;
        }
        Object object = Block.umbraInstance.getMappings().qg.a(string);
        if (object == null) {
            return null;
        }
        return new Block(object);
    }

    public int d(int n, int n2, int n3) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Umbra.notifyNativeStackTrace();
            return 0;
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            return MBlock.Q(Block.umbraInstance.getMappings().qg, this.I, Minecraft.theWorld().getObject(), n, n2, n3);
        }
        if (ForgeVersion.MC_1_8_9.L()) {
            return MBlock.V(Block.umbraInstance.getMappings().qg, this.I, Minecraft.theWorld().getObject(), BlockPos.create(n, n2, n3).getObject());
        }
        if (ForgeVersion.MC_1_12_2.L()) {
            return MBlock.W(Block.umbraInstance.getMappings().qg, this.I, Minecraft.theWorld().getBlockState(BlockPos.create(n, n2, n3)).getObject());
        }
        return 0;
    }

    public Boolean J(BlockState blockState, boolean bl) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return MBlock.X(Block.umbraInstance.getMappings().qg, this.I);
        }
        return Block.umbraInstance.getMappings().qg.C(this.I, blockState, bl);
    }

    public Material H() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return new Material(MBlock.k(Block.umbraInstance.getMappings().qg, this.I));
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return new Material(MBlock.z(Block.umbraInstance.getMappings().qg, this.I, null));
        }
        return new Material(MBlock.h(Block.umbraInstance.getMappings().qg, this.I));
    }

    public ItemStack Z(World world, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(MBlock.Q(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), blockPos.getObject(), blockState.getObject()));
    }

    public AxisAlignedBB Q(World world, int n, int n2, int n3) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Umbra.notifyNativeStackTrace();
            return null;
        }
        return new AxisAlignedBB(MBlock.J(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), n, n2, n3));
    }

    public boolean p(BlockState blockState) {
        if (ForgeVersion.MC_1_20_6.d()) {
            if (blockState == null || blockState.isNull()) {
                return false;
            }
            return blockState.g() && blockState.Y();
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return Block.umbraInstance.getMappings().qg.A(this.I);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return Block.umbraInstance.getMappings().qg.E(this.I, blockState.getObject());
        }
        return Block.umbraInstance.getMappings().qg.F$src$Z$6w97vr(this.I);
    }

    public String U() {
        return MBlock.l(Block.umbraInstance.getMappings().qg, this.I);
    }

    public AxisAlignedBB M(World world, int n, int n2, int n3) {
        return new AxisAlignedBB(MBlock.d(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), n, n2, n3));
    }

    public BlockStatePredicate a() {
        return new BlockStatePredicate(MBlock.N(Block.umbraInstance.getMappings().qg, this.I));
    }

    public boolean y(World world, BlockPos blockPos, BlockState blockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float f, float f2, float f3) {
        if (ForgeVersion.MC_1_16_5.d()) {
            RayTraceResult rayTraceResult = RayTraceResult.create(null, Vec3.create(f, f2, f3), enumFacing, blockPos);
            Object object = MBlock.E(Block.umbraInstance.getMappings().qg, this.I, Minecraft.theWorld().getBlockState(blockPos).getObject(), world.getObject(), blockPos.getObject(), entityPlayer.getObject(), enumFacing.getObject(), rayTraceResult.getObject());
            EnumActionResult enumActionResult = new EnumActionResult(object);
            return enumActionResult.equals(EnumActionResult.success());
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return MBlock.G(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), blockPos.getObject(), blockState.getObject(), entityPlayer.getObject(), EnumHand.mainHand().getObject(), enumFacing.getObject(), f, f2, f3);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            return MBlock.L(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), blockPos.getObject(), blockState.getObject(), entityPlayer.getObject(), enumFacing.getObject(), f, f2, f3);
        }
        return MBlock.w(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), entityPlayer.getObject(), enumFacing.Y(), f, f2, f3);
    }

    public Block(Object object) {
        super(object);
    }

    public void W(World world, int n, int n2, int n3, Entity entity) {
        MBlock.v(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), n, n2, n3, entity.getObject());
    }

    public static boolean r(Entity entity) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return MBlock.k$src$Z$8ngnn6(Block.umbraInstance.getMappings().qg, entity.getObject());
        }
        return true;
    }

    public int M() {
        return MBlock.E(Block.umbraInstance.getMappings().qg, this.I);
    }

    public void r(World world, Entity entity) {
        if (ForgeVersion.MC_1_21_4.d()) {
            if (ForgeVersion.MC_26_2.d()) {
                BlockPos blockPos = entity.C$src$Lgg_umbra_wrapper_impl_BlockPos_$y7f4vu();
                MBlock.u(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), world.getBlockState(blockPos).getObject(), blockPos.getObject(), entity.getObject(), entity.M$src$F$ff28gb());
            } else {
                MBlock.y(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), entity.getObject());
            }
        }
    }

    public float P() {
        if (ForgeVersion.MC_1_20_6.d()) {
            throw new UnsupportedOperationException("Unimplemented");
        }
        return Block.umbraInstance.getMappings().qg.F(this.I);
    }

    public BlockState Z() {
        if (ForgeVersion.c() >= 35) {
            return this.a();
        }
        return new BlockState(MBlock.N(Block.umbraInstance.getMappings().qg, this.I));
    }

    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
    }

    public AxisAlignedBB Y(BlockState blockState, World world, BlockPos blockPos) {
        return new AxisAlignedBB(MBlock.y(Block.umbraInstance.getMappings().qg, this.I, blockState.getObject(), world.getObject(), blockPos.getObject()));
    }

    public static int R(Block block) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Object object = MBlock.N(Block.umbraInstance.getMappings().qg, block.getObject());
            return Block.umbraInstance.getMappings().qg.h(object);
        }
        return Block.umbraInstance.getMappings().qg.h(block.getObject());
    }

    public boolean X(BlockState blockState) {
        if (ForgeVersion.MC_1_20_6.d()) {
            if (blockState == null || blockState.isNull()) {
                return false;
            }
            return blockState.g() && blockState.Y();
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return Block.umbraInstance.getMappings().qg.A(this.I);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return Block.umbraInstance.getMappings().qg.r(this.I, blockState.getObject());
        }
        return Block.umbraInstance.getMappings().qg.Z(this.I);
    }

    public AxisAlignedBB Q(World world, BlockPos blockPos) {
        return new AxisAlignedBB(MBlock.d(Block.umbraInstance.getMappings().qg, this.I, world.getObject(), blockPos.getObject()));
    }
}
