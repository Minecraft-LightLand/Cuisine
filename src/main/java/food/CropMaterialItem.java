package food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class CropMaterialItem extends BlockItem {

	public CropMaterialItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties() {
		return FoodMaterialItem.getFoodPropertiesHook(this);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return FoodMaterialItem.getUseDurationHook(this, stack);
	}

	@Override
	public boolean isEdible() {
		return FoodMaterialItem.isEdibleHook(this);
	}
}
