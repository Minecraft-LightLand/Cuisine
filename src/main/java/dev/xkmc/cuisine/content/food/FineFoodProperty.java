package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;

import java.util.HashMap;

public class FineFoodProperty {

	public double hunger, saturation;
	public double amount;

	public HashMap<Flavor, Double> flavors = new HashMap<>();

	public FineFoodProperty add(FoodMaterialProperty prop, int n) {
		hunger += (prop.can_cook ? prop.cooked_hunger : prop.raw_hunger) * prop.amount * n;
		saturation += (prop.can_cook ? prop.cooked_saturation : prop.raw_saturation) * prop.amount * n;
		amount += prop.amount * n;
		prop.flavors.forEach((k, v) -> {
			double a = flavors.getOrDefault(k, 0d);
			double b = v * prop.amount * n;
			flavors.put(k, a + b);
			if (a * b < 0 && k.getCancellationFlavor().isPresent()) {
				Flavor cancel = k.getCancellationFlavor().get();
				flavors.put(cancel, flavors.getOrDefault(cancel, 0d) + Math.min(Math.abs(a), Math.abs(b)));
			}
		});
		return this;
	}

}
