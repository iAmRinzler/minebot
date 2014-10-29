package net.famzangl.minecraft.minebot.ai.commands;

import net.famzangl.minecraft.minebot.ai.AIHelper;
import net.famzangl.minecraft.minebot.ai.BlockWhitelist;
import net.famzangl.minecraft.minebot.ai.command.AICommand;
import net.famzangl.minecraft.minebot.ai.command.AICommandInvocation;
import net.famzangl.minecraft.minebot.ai.command.AICommandParameter;
import net.famzangl.minecraft.minebot.ai.command.AICommandParameter.BlockFilter;
import net.famzangl.minecraft.minebot.ai.command.ParameterType;
import net.famzangl.minecraft.minebot.ai.command.SafeStrategyRule;
import net.famzangl.minecraft.minebot.ai.path.MineBySettingsPathFinder;
import net.famzangl.minecraft.minebot.ai.path.MineSinglePathFinder;
import net.famzangl.minecraft.minebot.ai.strategy.AIStrategy;
import net.famzangl.minecraft.minebot.ai.strategy.PathFinderStrategy;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

@AICommand(helpText = "Mines for ores.\n"
		+ "Uses the minebot.properties file to find ores."
		+ "If blockName is given, only the block that is given is searched for.", name = "minebot")
public class CommandMine {

	public static BlockWhitelist MINEABLE = new BlockWhitelist(Blocks.lava,
			Blocks.flowing_lava, Blocks.water, Blocks.flowing_water,
			Blocks.waterlily, Blocks.bedrock).invert();

	@AICommandInvocation(safeRule = SafeStrategyRule.DEFEND_MINING)
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "mine", description = "") String nameArg) {
		return new PathFinderStrategy(new MineBySettingsPathFinder(
				helper.getLookDirection(), helper.getPlayerPosition().y),
				"Mining ores");
	}

	public static final class MineBlockFilter extends BlockFilter {
		@Override
		public boolean matches(Block b) {
			return MINEABLE.contains(b);
		}
	}

	@AICommandInvocation(safeRule = SafeStrategyRule.DEFEND_MINING)
	public static AIStrategy run(
			AIHelper helper,
			@AICommandParameter(type = ParameterType.FIXED, fixedName = "mine", description = "") String nameArg,
			@AICommandParameter(type = ParameterType.BLOCK_NAME, description = "The block to mine.", blockFilter = MineBlockFilter.class) Block blockName) {
		return new PathFinderStrategy(new MineSinglePathFinder(blockName,
				helper.getLookDirection(), helper.getPlayerPosition().y),
				"Mining " + blockName.getLocalizedName());
	}

}
