package dev.xkmc.cuisine.content.flavor;

import dev.xkmc.cuisine.content.food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;
import dev.xkmc.l2library.base.NamedEntry;

import java.util.Optional;

public class Flavor extends NamedEntry<Flavor> {

	public Flavor() {
		super(CuisineFlavor.FLAVOR);
	}

	public double getTastyFactor(double amount) {
		return 1;
	}

	public Optional<TasteEffect> getEffect(double amount) {
		return Optional.empty();
	}

	public Optional<Flavor> getCancellationFlavor(){
		return Optional.empty();
	}

}
