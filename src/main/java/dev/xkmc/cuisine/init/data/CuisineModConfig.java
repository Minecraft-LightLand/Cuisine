package dev.xkmc.cuisine.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class CuisineModConfig {

	public static class Common {

		public final ForgeConfigSpec.IntValue minHunger;
		public final ForgeConfigSpec.IntValue rawCut;
		public final ForgeConfigSpec.IntValue cubeCut;
		public final ForgeConfigSpec.DoubleValue leafGrowChance;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("mechanics");
			minHunger = builder.comment("Minimum Hunger Points (0,20) for food effects to be effective")
					.worldRestart()
					.defineInRange("minHunger", 16, 1, 19);
			rawCut = builder.comment("Number of cubed materials to get from raw material")
					.worldRestart()
					.defineInRange("rawCut", 3, 1, 16);
			cubeCut = builder.comment("Number of materials to get from cubed material")
					.worldRestart()
					.defineInRange("cubeCut", 2, 1, 16);
			leafGrowChance = builder.comment("Chance for fruit tree leaves to grow per random tick")
					.worldRestart()
					.defineInRange("leafGrowChance", 0.1, 0.001, 1);
			builder.pop();
		}

	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	/** Registers any relevant listeners for config */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CuisineModConfig.COMMON_SPEC);
	}

}
