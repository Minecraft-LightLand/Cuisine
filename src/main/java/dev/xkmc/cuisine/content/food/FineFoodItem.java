package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;
import dev.xkmc.l2library.serial.codec.TagCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class FineFoodItem extends Item {

	public static FineFoodProperty readData(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("cuisine_food_data")) {
			return TagCodec.fromTag(tag.getCompound("cuisine_food_data"), FineFoodProperty.class);
		}
		return new FineFoodProperty();
	}

	public static void writeData(ItemStack stack, FineFoodProperty data) {
		if (!(stack.getItem() instanceof FineFoodItem)) return;
		stack.getOrCreateTag().put("cuisine_food_data", TagCodec.toTag(new CompoundTag(), data));
	}

	@Nullable
	static FoodProperties getFoodPropertiesHook(Item item, ItemStack stack) {
		return readData(stack).toFoodProperty();
	}

	static int getUseDurationHook(Item item, ItemStack stack) {
		FineFoodProperty prop = readData(stack);
		if (prop.hunger >= 1) {
			double ans = 32;
			for (Map.Entry<Flavor, Double> ent : prop.flavors.entrySet()) {
				ans *= ent.getKey().getTastyFactor(ent.getValue());
			}
			return (int) Math.ceil(ans);
		}
		return 0;
	}

	public FineFoodItem(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		return getFoodPropertiesHook(this, stack);
	}

	@Override
	public boolean isEdible() {
		return true;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return getUseDurationHook(this, stack);
	}

	@SubscribeEvent
	public static void appendTooltip(ItemTooltipEvent event) {
		List<Component> list = event.getToolTip();
		FineFoodProperty prop = readData(event.getItemStack());
		prop.flavors.forEach((k, v) -> k.getDescriptionByAmount(v).ifPresent(list::add));
	}

}
