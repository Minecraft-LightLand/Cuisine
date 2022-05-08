package dev.xkmc.cuisine.content.flavor;

import food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;

import java.util.Optional;
import java.util.function.Consumer;

public class SourFlavor extends Flavor {

	@Override
	public double getTastyFactor(double amount) {
		return amount >= 1 ? amount >= 2 ? 2 : 0.8 : 1;
	}

	@Override
	public Optional<TasteEffect> getEffect(double amount) {
		return amount >= 1 && amount < 2 ? Optional.of(CuisineFlavor.EFF_SOUR.get()) : Optional.empty();
	}

	@Override
	public Optional<String> getDisplayIdByAmount(double amount) {
		return amount >= 2 ? Optional.of("too_sour") : amount >= 1 ? Optional.of("sour") : Optional.empty();
	}

	@Override
	public void fillLangImpl(Consumer<String> cons) {
		cons.accept("sour");
		cons.accept("too_sour");
	}

}
