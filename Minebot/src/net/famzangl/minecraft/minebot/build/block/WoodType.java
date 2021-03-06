/*******************************************************************************
 * This file is part of Minebot.
 *
 * Minebot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Minebot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minebot.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package net.famzangl.minecraft.minebot.build.block;

import net.famzangl.minecraft.minebot.ai.path.world.BlockMetaSet;
import net.famzangl.minecraft.minebot.ai.path.world.BlockSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;

/**
 * A type of wood used in the game.
 * 
 * @see LogItemFilter
 * @see WoodItemFilter
 * @author michael
 *
 */
public enum WoodType {
	OAK(Blocks.log, 0, EnumType.OAK), SPRUCE(Blocks.log, 1, EnumType.SPRUCE), BIRCH(
			Blocks.log, 2, EnumType.BIRCH), JUNGLE(Blocks.log, 3,
			EnumType.JUNGLE), ACACIA(Blocks.log2, 0, EnumType.ACACIA), DARK_OAK(
			Blocks.log2, 1, EnumType.DARK_OAK);

	public final Block block;
	public final int lowerBits;
	public final EnumType plankType;

	private WoodType(Block block, int lowerBits, EnumType plankType) {
		this.block = block;
		this.lowerBits = lowerBits;
		this.plankType = plankType;
	}

	/**
	 * Returns a block set with all blocks of this type.
	 * 
	 * @return
	 */
	public BlockSet getLogBlocks() {
		return new BlockMetaSet(block, lowerBits).unionWith(block,
				lowerBits | 0x4).unionWith(block, lowerBits | 0x8);
	}
}