package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.world.food.FoodProperties;

import javax.annotation.Nullable;
import java.util.HashMap;

@SerialClass
public class FineFoodProperty {

	@SerialClass.SerialField
	public double hunger, saturation;
	@SerialClass.SerialField
	public double amount;

	@SerialClass.SerialField
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


	@Nullable
	public FoodProperties toFoodProperty() {
		if (hunger < 1) return null;
		FoodProperties.Builder prop = new FoodProperties.Builder();
		prop.nutrition((int) hunger);
		double sat = hunger < 1 ? 0 : saturation / (int) hunger;
		prop.saturationMod((float) sat);
		flavors.forEach((k, v) -> k.getEffectInstance(v).ifPresent(e -> prop.effect(() -> e, 1)));
		return prop.build();
	}

}
