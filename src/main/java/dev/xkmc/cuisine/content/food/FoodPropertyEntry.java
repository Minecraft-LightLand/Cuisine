package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.content.flavor.Flavor;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

@SerialClass
public class FoodPropertyEntry {

	@SerialClass.SerialField
	public Float raw_hunger, raw_saturation;
	@SerialClass.SerialField
	public Boolean can_cook;
	@SerialClass.SerialField
	public Float cooked_hunger, cooked_saturation;
	@SerialClass.SerialField
	public Float amount;
	@SerialClass.SerialField
	public ResourceLocation parent;
	@SerialClass.SerialField
	public HashMap<Flavor, Float> flavors = new HashMap<>();

	FoodMaterialProperty cache;

	public FoodPropertyEntry() {

	}

	public FoodPropertyEntry setDefault() {
		raw_hunger = 0f;
		raw_saturation = 0f;
		can_cook = false;
		cooked_hunger = 0f;
		cooked_saturation = 0f;
		amount = 1f;
		return this;
	}

	public FoodPropertyEntry setRawHunger(float raw_hunger) {
		this.raw_hunger = raw_hunger;
		return this;
	}

	public FoodPropertyEntry setRawSaturationMod(float raw_saturation) {
		this.raw_saturation = raw_hunger * raw_saturation * 2;
		return this;
	}

	public FoodPropertyEntry setCanCook(boolean can_cook) {
		this.can_cook = can_cook;
		return this;
	}

	public FoodPropertyEntry setCookedHunger(float cooked_hunger) {
		this.cooked_hunger = cooked_hunger;
		return this;
	}

	public FoodPropertyEntry setCookedSaturationMod(float cooked_saturation) {
		this.cooked_saturation = cooked_hunger * cooked_saturation * 2;
		return this;
	}

	public FoodPropertyEntry setAmount(float amount) {
		this.amount = amount;
		return this;
	}

	public FoodPropertyEntry setParent(ResourceLocation parent) {
		this.parent = parent;
		return this;
	}
}
