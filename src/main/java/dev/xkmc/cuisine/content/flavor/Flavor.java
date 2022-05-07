package dev.xkmc.cuisine.content.flavor;

import dev.xkmc.cuisine.content.food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;
import dev.xkmc.l2library.base.NamedEntry;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class Flavor extends NamedEntry<Flavor> {

	public Flavor() {
		super(CuisineFlavor.FLAVOR);
	}

	public double getTastyFactor(double amount) {
		return 1;
	}

	public Optional<TasteEffect> getEffect(double amount) {
		return Optional.empty();
	}

	public Optional<Flavor> getCancellationFlavor() {
		return Optional.empty();
	}

	protected abstract Optional<String> getDisplayIdByAmount(double amount);

	protected abstract void fillLangImpl(Consumer<String> cons);

	public void fillLang(Consumer<String> cons) {
		fillLangImpl(e -> cons.accept(parse(e)));
	}

	public Optional<TranslatableComponent> getDescriptionByAmount(double amount) {
		return getDisplayIdByAmount(amount).map(e -> new TranslatableComponent(parse(e)));
	}

	private String parse(String str) {
		return "flavor." + getRegistryName().getNamespace() + "." + str;
	}

}
