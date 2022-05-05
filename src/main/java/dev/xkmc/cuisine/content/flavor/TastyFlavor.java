package dev.xkmc.cuisine.content.flavor;

public class TastyFlavor extends Flavor {

	private final double n, p, pp;

	public TastyFlavor(double n, double p, double pp) {
		this.n = n;
		this.p = p;
		this.pp = pp;
	}

	@Override
	public double getTastyFactor(double amount) {
		return amount < 0 ? n : amount >= 1 ? amount >= 2 ? pp : p : 1;
	}

}
