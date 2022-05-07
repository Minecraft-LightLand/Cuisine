package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;
import net.minecraft.world.food.FoodProperties;

import java.util.HashMap;

public class FoodMaterialProperty {

	public double raw_hunger, raw_saturation;
	public boolean can_cook;
	public double cooked_hunger, cooked_saturation;
	public double amount;

	public HashMap<Flavor, Double> flavors = new HashMap<>();

	public void merge(FoodPropertyEntry pop) {
		if (pop.raw_hunger != null) raw_hunger = pop.raw_hunger;
		if (pop.raw_saturation != null) raw_saturation = pop.raw_saturation;
		if (pop.can_cook != null) can_cook = pop.can_cook;
		if (pop.cooked_hunger != null) cooked_hunger = pop.cooked_hunger;
		if (pop.cooked_saturation != null) cooked_saturation = pop.cooked_saturation;
		if (pop.amount != null) amount = pop.amount;
		flavors.putAll(pop.flavors);
	}

	public FoodProperties toFoodProperty(boolean raw, boolean meat) {
		FoodProperties.Builder prop = new FoodProperties.Builder();
		if (meat) prop.meat();
		int hunger = (int) ((raw ? raw_hunger : cooked_hunger) * amount);
		prop.nutrition(hunger);
		double sat = hunger == 0 ? 0 : (raw ? raw_saturation / 2 : cooked_saturation / 2) * amount / hunger;
		prop.saturationMod((float) sat);
		flavors.forEach((k, v) -> k.getEffectInstance(v).ifPresent(e -> prop.effect(() -> e, 1)));
		return prop.build();
	}

	public boolean isEdible() {
		return raw_hunger * amount >= 1;
	}

}
