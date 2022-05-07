package dev.xkmc.cuisine.content.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import static dev.xkmc.cuisine.content.food.FoodMaterialItem.*;

public class CropMaterialItem extends BlockItem {

	public CropMaterialItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties() {
		return getFoodPropertiesHook(this);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return getUseDurationHook(this, stack);
	}

	@Override
	public boolean isEdible() {
		return isEdibleHook(this);
	}
}
