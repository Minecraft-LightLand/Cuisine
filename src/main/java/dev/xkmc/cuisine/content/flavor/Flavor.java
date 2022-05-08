package dev.xkmc.cuisine.content.flavor;

import food.TasteEffect;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;
import dev.xkmc.l2library.base.NamedEntry;
import dev.xkmc.l2library.effects.EffectBuilder;
import dev.xkmc.l2library.effects.EffectProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class Flavor extends NamedEntry<Flavor> {

	public static final EffectProperties PROP = new EffectProperties();

	static {
		PROP.noCounter = true;
		PROP.showIcon = true;
		PROP.visible = false;
		PROP.ambient = false;
	}

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

	public Optional<Component> getDescriptionByAmount(double amount) {
		double factor = getTastyFactor(amount);
		return getDisplayIdByAmount(amount).map(e -> new TranslatableComponent(parse(e)).withStyle(
				factor < 1 ? ChatFormatting.GREEN : factor > 1 ? ChatFormatting.RED : ChatFormatting.WHITE
		));
	}

	private String parse(String str) {
		return "flavor." + getRegistryName().getNamespace() + "." + str;
	}

	public Optional<MobEffectInstance> getEffectInstance(double v) {
		return getEffect(v).map(e -> new EffectBuilder(e).setAmplifier(0).setDuration(TasteEffect.DURATION)
				.setAmbient(false).setVisible(false).setNoCounter(true).setShowIcon(true).ins);
	}
}
