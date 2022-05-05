package dev.xkmc.cuisine.init.templates;

import dev.xkmc.cuisine.content.food.FoodMaterialItem;
import dev.xkmc.cuisine.init.Cuisine;
import dev.xkmc.cuisine.init.data.CuisineModConfig;
import dev.xkmc.cuisine.init.data.CuisineTags;
import dev.xkmc.cuisine.init.data.CuisineTags.AllItemTags;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static dev.xkmc.cuisine.init.Cuisine.REGISTRATE;

public class ProcessedMeat {

	public enum Process {
		PASTE(AllItemTags.PASTE, null, null),
		MINCED(AllItemTags.MINCED, PASTE, null),
		SHREDDED(AllItemTags.SHREDDED, MINCED, null),
		SLICED(AllItemTags.SLICED, MINCED, SHREDDED),
		DICED(AllItemTags.DICED, MINCED, null),
		CUBED(AllItemTags.CUBED, DICED, SLICED, CuisineModConfig.COMMON.cubeCut::get, CuisineModConfig.COMMON.cubeCut::get);

		private final AllItemTags tag;
		public final Process left, right;
		public final IntSupplier left_count, right_count;

		Process(AllItemTags tag, @Nullable Process left, @Nullable Process right) {
			this.tag = tag;
			this.left = left == null ? this : left;
			this.right = right == null ? this : right;
			this.left_count = () -> 1;
			this.right_count = () -> 1;
		}

		Process(AllItemTags tag, @Nullable Process left, @Nullable Process right, IntSupplier l, IntSupplier r) {
			this.tag = tag;
			this.left = left == null ? this : left;
			this.right = right == null ? this : right;
			this.left_count = l;
			this.right_count = r;
		}

		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}

	public enum Meat {
		CHICKEN(() -> Items.CHICKEN, () -> Items.COOKED_CHICKEN, 0xF2C9BD, AllItemTags.CHICKEN, AllItemTags.MEAT, AllItemTags.WHITE_MEAT),
		PORK(() -> Items.PORKCHOP, () -> Items.COOKED_PORKCHOP, 0xEF7070, AllItemTags.PORK, AllItemTags.MEAT, AllItemTags.GREASY, AllItemTags.WHITE_MEAT),
		BEEF(() -> Items.BEEF, () -> Items.COOKED_BEEF, 0xE03E35, AllItemTags.BEEF, AllItemTags.MEAT, AllItemTags.GREASY, AllItemTags.RED_MEAT),
		MUTTON(() -> Items.MUTTON, () -> Items.COOKED_MUTTON, 0xE27269, AllItemTags.MUTTON, AllItemTags.MEAT, AllItemTags.GREASY, AllItemTags.RED_MEAT),
		RABBIT(() -> Items.RABBIT, () -> Items.COOKED_RABBIT, 0xFEE5D2, AllItemTags.RABBIT, AllItemTags.MEAT, AllItemTags.WHITE_MEAT),
		SALMON(() -> Items.SALMON, () -> Items.COOKED_SALMON, 0xAB3533, AllItemTags.SALMON, AllItemTags.MEAT, AllItemTags.GREASY, AllItemTags.FISH, AllItemTags.SEAFOOD),
		COD(() -> Items.COD, () -> Items.COOKED_COD, 0xD6C5AD, AllItemTags.COD, AllItemTags.MEAT, AllItemTags.GREASY, AllItemTags.FISH, AllItemTags.SEAFOOD);

		public final Supplier<Item> original, cooked;
		public final int color;

		public final ItemEntry<FoodMaterialItem>[] items;

		@SuppressWarnings("unchecked")
		Meat(Supplier<Item> original, Supplier<Item> cooked, int color, AllItemTags... tags) {
			this.original = original;
			this.cooked = cooked;
			this.color = color;
			items = new ItemEntry[Process.values().length];
			for (Process process : Process.values()) {
				items[process.ordinal()] = REGISTRATE.item(process.getName() + "_" + getName(), FoodMaterialItem::new)
						.model((ctx, pvd) -> pvd.generated(ctx, new ResourceLocation(Cuisine.MODID, "item/" + process.getName())))
						.color(() -> () -> (stack, layer) -> color).tag(CuisineTags.map(process.tag, tags)).register();
			}
		}

		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}

		public static void register() {

		}

	}

}
