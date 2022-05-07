package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;
import dev.xkmc.cuisine.init.data.CuisineTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FoodMaterialItem extends Item {

	@Nullable
	static FoodProperties getFoodPropertiesHook(Item item) {
		return FoodConfig.collectAll(item).map(e -> e.toFoodProperty(true, CuisineTags.AllItemTags.MEAT.matches(item))).orElse(null);
	}

	static boolean isEdibleHook(Item item) {
		return FoodConfig.collectAll(item).map(FoodMaterialProperty::isEdible).orElse(false);
	}

	static int getUseDurationHook(Item item, ItemStack stack) {
		Optional<FoodMaterialProperty> prop = FoodConfig.collectAll(item);
		if (prop.isPresent() && prop.get().isEdible()) {
			double ans = 32;
			for (Map.Entry<Flavor, Double> ent : prop.get().flavors.entrySet()) {
				ans *= ent.getKey().getTastyFactor(ent.getValue());
			}
			return (int) Math.ceil(ans);
		}
		return 0;
	}

	public FoodMaterialItem(Properties p_41383_) {
		super(p_41383_);
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties() {
		return getFoodPropertiesHook(this);
	}

	@Override
	public boolean isEdible() {
		return isEdibleHook(this);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return getUseDurationHook(this, stack);
	}

	@SubscribeEvent
	public static void appendTooltip(ItemTooltipEvent event) {
		Item item = event.getItemStack().getItem();
		List<Component> list = event.getToolTip();
		FoodConfig.collectAll(item).ifPresent(e ->
				e.flavors.forEach((k, v) ->
						k.getDescriptionByAmount(v).ifPresent(list::add)));
	}

}
