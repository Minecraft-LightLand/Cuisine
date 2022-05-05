package dev.xkmc.cuisine.content.flavor;

public class TastyFlavor extends Flavor {

	@Override
	public double getTastyFactor(double amount) {
		return amount < 0 ? 1.5 : amount >= 1 ? amount >= 2 ? 3 : 0.6 : 1;
	}

}
