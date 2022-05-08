package food;

import dev.xkmc.cuisine.init.Cuisine;
import dev.xkmc.cuisine.init.data.CuisineTags;
import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.*;

@SerialClass
public class FoodConfig extends BaseConfig {

	@SerialClass.SerialField
	public HashMap<ResourceLocation, FoodPropertyEntry> map = new HashMap<>();

	private static Optional<FoodPropertyEntry> collect(ResourceLocation item) {
		return Cuisine.NETWORK.CONFIGS.values().stream().filter(baseConfig -> baseConfig instanceof FoodConfig)
				.map(e -> ((FoodConfig) e).map.get(item)).filter(Objects::nonNull).findFirst();
	}

	public static Optional<FoodMaterialProperty> collectAll(Item item) {
		if (!CuisineTags.AllItemTags.CAN_COOK.matches(item)) return Optional.empty();
		Optional<FoodPropertyEntry> entry = collect(Objects.requireNonNull(item.getRegistryName()));
		if (entry.isEmpty()) return Optional.empty();
		if (entry.get().cache != null) {
			return Optional.of(entry.get().cache);
		}
		Stack<FoodPropertyEntry> stack = new Stack<>();
		stack.push(entry.get());
		while (stack.peek().parent != null) {
			Optional<FoodPropertyEntry> e = collect(stack.peek().parent);
			if (e.isEmpty()) return Optional.empty();
			stack.push(e.get());
		}
		FoodMaterialProperty base = new FoodMaterialProperty();
		while (!stack.isEmpty()) {
			base.merge(stack.pop());
		}
		entry.get().cache = base;
		return Optional.of(base);
	}

}
