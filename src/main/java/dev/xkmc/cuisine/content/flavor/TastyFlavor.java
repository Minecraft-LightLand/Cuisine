package dev.xkmc.cuisine.content.flavor;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

public class TastyFlavor extends Flavor {

	private final double n, p, pp;
	private final String sn, sp, spp;

	public TastyFlavor(double n, double p, double pp, @Nullable String sn, String sp, String spp) {
		this.n = n;
		this.p = p;
		this.pp = pp;
		this.sn = sn;
		this.sp = sp;
		this.spp = spp;
	}


	public TastyFlavor(double p, String sp) {
		this(1, p, p, null, sp, sp);
	}

	@Override
	public double getTastyFactor(double amount) {
		return amount < 0 ? n : amount >= 1 ? amount >= 2 ? pp : p : 1;
	}

	@Override
	public Optional<String> getDisplayIdByAmount(double amount) {
		return Optional.ofNullable(amount < 0 ? sn : amount >= 1 ? amount >= 2 ? spp : sp : null);
	}

	@Override
	public void fillLangImpl(Consumer<String> cons) {
		if (sn != null) cons.accept(sn);
		if (sp != null) cons.accept(sp);
		if (spp != null && !spp.equals(sp)) cons.accept(spp);
	}

}
