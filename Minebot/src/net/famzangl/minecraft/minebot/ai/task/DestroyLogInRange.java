package net.famzangl.minecraft.minebot.ai.task;

import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.path.world.BlockSet;
import net.famzangl.minecraft.minebot.ai.path.world.BlockSets;
import net.famzangl.minecraft.minebot.ai.path.world.WorldData;
import net.famzangl.minecraft.minebot.ai.utils.BlockArea;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

/**
 * This task attemtps to destroy any log in a given area.
 * 
 * @author michael
 *
 */
public class DestroyLogInRange extends DestroyInRangeTask {

	private static final BlockSet LEAVES_OR_LOGS = BlockSets.LEAVES.unionWith(BlockSets.LOGS).unionWith(new BlockSet(Blocks.vine));

	public DestroyLogInRange(BlockArea range) {
		super(range);
	}

	@Override
	protected boolean noDestructionRequired(WorldData world, int x, int y, int z) {
		if (super.noDestructionRequired(world, x, y, z)) {
			return true;
		} else {
			return !BlockSets.LOGS.isAt(world, x, y, z);
		}
	}

	@Override
	protected boolean isFacingAcceptableBlock(AIHelper h, BlockPos n, boolean isFacingRightDirection) {
		MovingObjectPosition hit = h.getObjectMouseOver();
		if (isFacingRightDirection && hit != null && hit.typeOfHit == MovingObjectType.BLOCK) {
			BlockPos pos = hit.getBlockPos();
			return pos.equals(n)
					|| (LEAVES_OR_LOGS.isAt(
							h.getWorld(), pos) && BlockSets.LOGS.isAt(h.getWorld(), n));
		}
		return super.isFacingAcceptableBlock(h, n, isFacingRightDirection);
	}
}
