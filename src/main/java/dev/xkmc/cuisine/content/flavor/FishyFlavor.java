package dev.xkmc.cuisine.content.flavor;

import java.util.Optional;
import java.util.function.Consumer;

public class FishyFlavor extends Flavor {

	@Override
	public double getTastyFactor(double amount) {
		return amount >= 1 ? 2 : 1;
	}

	@Override
	public Optional<String> getDisplayIdByAmount(double amount) {
		return amount >= 1 ? Optional.of("fishy") : Optional.empty();
	}

	@Override
	public void fillLangImpl(Consumer<String> cons) {
		cons.accept("fishy");
	}

}
