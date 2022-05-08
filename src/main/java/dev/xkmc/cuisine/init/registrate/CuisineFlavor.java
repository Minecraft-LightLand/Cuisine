package dev.xkmc.cuisine.init.registrate;

import dev.xkmc.cuisine.content.flavor.*;
import food.TasteEffect;
import dev.xkmc.l2library.repack.registrate.builders.NoConfigBuilder;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2library.serial.handler.RLClassHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static dev.xkmc.cuisine.init.Cuisine.REGISTRATE;

public class CuisineFlavor {

	public static final Supplier<IForgeRegistry<Flavor>> FLAVOR = REGISTRATE.makeRegistry("flavor", Flavor.class,
			() -> new RegistryBuilder<Flavor>().addCallback((IForgeRegistry.CreateCallback<Flavor>) (owner, stage) -> regSerializer(owner)));

	public static final RegistryEntry<TasteEffect> EFF_SWEET = genTaste("sweet", () -> new TasteEffect.Config(
			MobEffectCategory.BENEFICIAL, 0xffffff, 0, 0,
			new TasteEffect.Attribs(Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.MULTIPLY_TOTAL, 0.1f)
	));

	public static final RegistryEntry<TasteEffect> EFF_SPICY = genTaste("spicy", () -> new TasteEffect.Config(
			MobEffectCategory.BENEFICIAL, 0xffffff, 0, 0,
			new TasteEffect.Attribs(Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_TOTAL, 0.1f)
	));

	public static final RegistryEntry<TasteEffect> EFF_SOUR = genTaste("sour", () -> new TasteEffect.Config(
			MobEffectCategory.BENEFICIAL, 0xffffff, 0, 0,
			new TasteEffect.Attribs(Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADDITION, 0.1f)
	));

	public static final RegistryEntry<TasteEffect> EFF_NUMB = genTaste("numb", () -> new TasteEffect.Config(
			MobEffectCategory.BENEFICIAL, 0xffffff, 0, 0,
			new TasteEffect.Attribs(Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.ADDITION, 0.1f)
	));

	public static final RegistryEntry<SweetFlavor> SWEET = REGISTRATE.flavor("sweet", SweetFlavor::new).defaultLang().register();
	public static final RegistryEntry<TastyFlavor> SWEET_SPICY = REGISTRATE.flavor("sweet_spicy", () -> new TastyFlavor(0.6, "sweet_spicy")).defaultLang().register();
	public static final RegistryEntry<TastyFlavor> SALTY = REGISTRATE.flavor("salty", () -> new TastyFlavor(1.5, 0.6, 3, "no_salt", "salty", "too_salty")).defaultLang().register();
	public static final RegistryEntry<TastyFlavor> GREASY = REGISTRATE.flavor("greasy", () -> new TastyFlavor(1, 0.6, 3, "no_oil", "greasy", "too_greasy")).defaultLang().register();
	public static final RegistryEntry<SourFlavor> SOUR = REGISTRATE.flavor("sour", SourFlavor::new).defaultLang().register();
	public static final RegistryEntry<FishyFlavor> FISHY = REGISTRATE.flavor("fishy", FishyFlavor::new).defaultLang().register();
	public static final RegistryEntry<TastyFlavor> TASTY = REGISTRATE.flavor("tasty", () -> new TastyFlavor(0.6, "tasty")).defaultLang().register();
	public static final RegistryEntry<TastyFlavor> SMELLY = REGISTRATE.flavor("smelly", () -> new TastyFlavor(0.6, "smelly")).defaultLang().register();
	public static final RegistryEntry<NumbFlavor> NUMB = REGISTRATE.flavor("numb", NumbFlavor::new).defaultLang().register();

	public static void register() {

	}

	@SuppressWarnings({"rawtypes"})
	private static <T extends IForgeRegistryEntry<T>> IForgeRegistry regSerializer(IForgeRegistry<T> r) {
		new RLClassHandler<>(r.getRegistrySuperType(), () -> r);
		return r;
	}

	public static RegistryEntry<TasteEffect> genTaste(String name, Supplier<TasteEffect.Config> config) {
		return genEffect(name, () -> new TasteEffect(config.get(), name));
	}

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(REGISTRATE, REGISTRATE, name, cb, MobEffect.class, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

}
