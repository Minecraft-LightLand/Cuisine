package dev.xkmc.cuisine.content.flavor;

import dev.xkmc.cuisine.content.food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;

import java.util.Optional;
import java.util.function.Consumer;

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


	@Override
	public Optional<String> getDisplayIdByAmount(double amount) {
		return amount <= -1 ? amount <= -2 ? Optional.of("too_spicy") : Optional.of("spicy") :
				amount >= 1 ? amount >= 2 ? Optional.of("too_sweet") : Optional.of("sweet") :
						Optional.empty();
	}

	@Override
	public void fillLangImpl(Consumer<String> cons) {
		cons.accept("spicy");
		cons.accept("too_spicy");
		cons.accept("sweet");
		cons.accept("too_sweet");
	}

}
