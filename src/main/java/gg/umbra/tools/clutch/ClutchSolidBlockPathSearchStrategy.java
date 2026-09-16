package gg.umbra.tools.clutch;

import gg.umbra.tools.Clutch;
import gg.umbra.tools.clutch.BlockPathSearchStrategy;
import gg.umbra.tools.clutch.BlockPlacementNode;
import gg.umbra.utils.BlockUtil;
import gg.umbra.utils.datas.BlockData;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.World;
import java.util.Vector;

public class ClutchSolidBlockPathSearchStrategy
implements BlockPathSearchStrategy<BlockPlacementNode> {
    final World world;
    final Clutch clutch;

    @Override
    public int scorePath(Vector<BlockPlacementNode> path) {
        return this.clutch.computePathCost(this.world, path);
    }

    @Override
    public boolean isValidBlock(BlockData blockData) {
        Block block = this.world.getBlockByPos(blockData.D(), blockData.B(), blockData.G());
        return BlockUtil.f(block);
    }

    public ClutchSolidBlockPathSearchStrategy(Clutch clutch, World world) {
        this.clutch = clutch;
        this.world = world;
    }

    @Override
    public int getMaxDepth() {
        return 4;
    }
}

