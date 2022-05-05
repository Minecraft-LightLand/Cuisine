package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;

import java.util.HashMap;

public class FineFoodProperty {

	public float hunger, saturation;
	public float amount;

	public HashMap<Flavor, Float> flavors = new HashMap<>();

	public FineFoodProperty add(FoodMaterialProperty prop, int n) {
		hunger += (prop.can_cook ? prop.cooked_hunger : prop.raw_hunger) * prop.amount * n;
		saturation += (prop.can_cook ? prop.cooked_saturation : prop.raw_saturation) * prop.amount * n;
		amount += prop.amount * n;
		prop.flavors.forEach((k, v) -> flavors.put(k, flavors.getOrDefault(k, 0f) + v * prop.amount * n));
		return this;
	}

}
