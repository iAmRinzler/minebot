package net.famzangl.minecraft.minebot.ai.tools.rate;

import net.famzangl.minecraft.minebot.ai.path.world.BlockFloatMap;
import net.famzangl.minecraft.minebot.ai.tools.ToolRater;
import net.minecraft.item.ItemStack;

public class OrRater extends Rater {
	private final Rater[] raters;

	public OrRater(BlockFloatMap values, Rater... raters) {
		super(createName(raters, "|"), values);
		this.raters = raters;
	}

	@Override
	protected double getPow(ItemStack item, int forBlockAndMeta) {
		double pow = 1;
		for (Rater r : raters) {
			pow = Math.max(r.getPow(item, forBlockAndMeta), pow);
		}
		return pow;
	}

	@Override
	protected boolean isAppleciable(ItemStack item, int forBlockAndMeta) {
		for (Rater rater : raters) {
			if (rater.isAppleciable(item, forBlockAndMeta)) {
				return true;
			}
		}
		return false;
	}
}