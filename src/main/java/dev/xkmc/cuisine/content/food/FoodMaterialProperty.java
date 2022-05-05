package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;

import java.util.HashMap;

public class FoodMaterialProperty {

	public float raw_hunger, raw_saturation;
	public boolean can_cook;
	public float cooked_hunger, cooked_saturation;
	public float amount;

	public HashMap<Flavor, Float> flavors = new HashMap<>();

	public void merge(FoodPropertyEntry pop) {
		if (pop.raw_hunger != null) raw_hunger = pop.raw_hunger;
		if (pop.raw_saturation != null) raw_saturation = pop.raw_saturation;
		if (pop.can_cook != null) can_cook = pop.can_cook;
		if (pop.cooked_hunger != null) cooked_hunger = pop.cooked_hunger;
		if (pop.cooked_saturation != null) cooked_saturation = pop.cooked_saturation;
		if (pop.amount != null) amount = pop.amount;
		flavors.putAll(pop.flavors);
	}

}
