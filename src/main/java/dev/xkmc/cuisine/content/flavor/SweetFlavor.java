package dev.xkmc.cuisine.content.flavor;

import dev.xkmc.cuisine.content.food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;

import java.util.Optional;

public class SweetFlavor extends Flavor {

	@Override
	public double getTastyFactor(double amount) {
		return amount <= -1 ? amount <= -2 ? 2 : 0.8 :
				amount >= 1 ? amount >= 2 ? 2 : 0.8 : 1;
	}

	@Override
	public Optional<TasteEffect> getEffect(double amount) {
		return amount <= -1 ? amount <= -2 ? Optional.empty() : Optional.of(CuisineFlavor.EFF_SPICY.get()) :
				amount >= 1 ? amount >= 2 ? Optional.empty() : Optional.of(CuisineFlavor.EFF_SWEET.get()) :
						Optional.empty();
	}

	@Override
	public Optional<Flavor> getCancellationFlavor() {
		return Optional.of(CuisineFlavor.SWEET_SPICY.get());
	}
}
