package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

@SerialClass
public class FoodPropertyEntry {

	@SerialClass.SerialField
	public Double raw_hunger, raw_saturation;
	@SerialClass.SerialField
	public Boolean can_cook;
	@SerialClass.SerialField
	public Double cooked_hunger, cooked_saturation;
	@SerialClass.SerialField
	public Double amount;
	@SerialClass.SerialField
	public ResourceLocation parent;
	@SerialClass.SerialField
	public HashMap<Flavor, Double> flavors = new HashMap<>();

	FoodMaterialProperty cache;

	public FoodPropertyEntry() {

	}

	public FoodPropertyEntry setDefault() {
		raw_hunger = 0d;
		raw_saturation = 0d;
		can_cook = false;
		cooked_hunger = 0d;
		cooked_saturation = 0d;
		amount = 1d;
		return this;
	}

	public FoodPropertyEntry setRawHunger(double raw_hunger) {
		this.raw_hunger = raw_hunger;
		return this;
	}

	public FoodPropertyEntry setRawSaturationMod(double raw_saturation) {
		this.raw_saturation = raw_hunger * raw_saturation * 2;
		return this;
	}

	public FoodPropertyEntry setCanCook(boolean can_cook) {
		this.can_cook = can_cook;
		return this;
	}

	public FoodPropertyEntry setCookedHunger(double cooked_hunger) {
		this.cooked_hunger = cooked_hunger;
		return this;
	}

	public FoodPropertyEntry setCookedSaturationMod(double cooked_saturation) {
		this.cooked_saturation = cooked_hunger * cooked_saturation * 2;
		return this;
	}

	public FoodPropertyEntry setAmount(double amount) {
		this.amount = amount;
		return this;
	}

	public FoodPropertyEntry setParent(ResourceLocation parent) {
		this.parent = parent;
		return this;
	}

	public FoodPropertyEntry put(Flavor f, double v){
		flavors.put(f, v);
		return this;
	}
}
