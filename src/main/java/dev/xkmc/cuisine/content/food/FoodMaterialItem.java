package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.init.data.CuisineTags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class FoodMaterialItem extends Item {

	public FoodMaterialItem(Properties p_41383_) {
		super(p_41383_);
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties() {
		return FoodConfig.collectAll(this).map(e -> e.toFoodProperty(true, CuisineTags.AllItemTags.MEAT.matches(this))).orElse(null);
	}

	@Override
	public boolean isEdible() {
		return FoodConfig.collectAll(this).isPresent();
	}
}
