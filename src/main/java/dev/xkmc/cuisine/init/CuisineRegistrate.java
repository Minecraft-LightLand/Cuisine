package dev.xkmc.cuisine.init;

import dev.xkmc.cuisine.content.flavor.Flavor;
import dev.xkmc.l2library.base.LcyRegistrate;
import dev.xkmc.l2library.repack.registrate.builders.AbstractBuilder;
import dev.xkmc.l2library.repack.registrate.builders.BuilderCallback;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonnullType;
import org.jetbrains.annotations.NotNull;

public class CuisineRegistrate extends LcyRegistrate {

	public CuisineRegistrate(String modid) {
		super(modid);
	}


	public <T extends Flavor> FlavorBuilder<T> flavor(String id, NonNullSupplier<T> sup) {
		return this.entry(id, (cb) -> new FlavorBuilder<>(this, id, cb, sup));
	}


	public static class FlavorBuilder<T extends Flavor> extends AbstractBuilder<Flavor, T, CuisineRegistrate, FlavorBuilder<T>> {
		private final NonNullSupplier<T> sup;

		FlavorBuilder(CuisineRegistrate parent, String name, BuilderCallback callback, NonNullSupplier<T> sup) {
			super(parent, parent, name, callback, Flavor.class);
			this.sup = sup;
		}

		@NonnullType
		@NotNull
		protected T createEntry() {
			return this.sup.get();
		}

		public FlavorBuilder<T> defaultLang() {
			return this.setData(ProviderType.LANG, (ctx, prov) -> ctx.get().fillLang(s -> prov.add(s, RegistrateLangProvider.toEnglishName(s))));
		}

	}

}
