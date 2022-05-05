package dev.xkmc.cuisine.content.flavor;

public class FishyFlavor extends Flavor {

	@Override
	public double getTastyFactor(double amount) {
		return amount >= 1 ? 2 : 1;
	}

}
