package dev.xkmc.cuisine.content.flavor;

import dev.xkmc.cuisine.content.food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;

import java.util.Optional;

public class SourFlavor extends Flavor {

	@Override
	public double getTastyFactor(double amount) {
		return amount >= 1 ? amount >= 2 ? 2 : 0.8 : 1;
	}

	@Override
	public Optional<TasteEffect> getEffect(double amount) {
		return amount >= 1 && amount < 2 ? Optional.of(CuisineFlavor.EFF_SOUR.get()) : Optional.empty();
	}
}
