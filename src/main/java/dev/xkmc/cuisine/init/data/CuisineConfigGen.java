package dev.xkmc.cuisine.init.data;

import dev.xkmc.cuisine.content.food.FoodConfig;
import dev.xkmc.cuisine.content.food.FoodPropertyEntry;
import dev.xkmc.cuisine.init.Cuisine;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;
import dev.xkmc.cuisine.init.registrate.ProcessedMeat;
import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.network.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.Map;

public class CuisineConfigGen extends ConfigDataProvider {

	public CuisineConfigGen(DataGenerator generator) {
		super(generator, "data/" + Cuisine.MODID + "/cuisine_config/", "Cuisine Food Property Config");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		FoodConfig meat = new FoodConfig();
		addProcessedMaterials(meat);
		map.put("meat", meat);
	}

	@SuppressWarnings("ConstantConditions")
	public static void addProcessedMaterials(FoodConfig base) {
		for (ProcessedMeat.Meat meat : ProcessedMeat.Meat.values()) {
			Item ori = meat.original.get();
			Item cok = meat.cooked.get();
			FoodProperties fp_ori = ori.getFoodProperties(ori.getDefaultInstance(), null);
			FoodProperties fp_cok = cok.getFoodProperties(cok.getDefaultInstance(), null);
			FoodPropertyEntry entry = new FoodPropertyEntry()
					.setAmount(1f)
					.setRawHunger(fp_ori.getNutrition())
					.setRawSaturationMod(fp_ori.getSaturationModifier())
					.setCanCook(true)
					.setCookedHunger(fp_cok.getNutrition())
					.setCookedSaturationMod(fp_cok.getSaturationModifier());
			float greasy = meat == ProcessedMeat.Meat.BEEF || meat == ProcessedMeat.Meat.PORK || meat == ProcessedMeat.Meat.MUTTON ? 2f : 1f;
			entry.flavors.put(CuisineFlavor.GREASY.get(), greasy);
			base.map.put(ori.getRegistryName(), entry);
			for (ProcessedMeat.Process process : ProcessedMeat.Process.values()) {
				float amount = process == ProcessedMeat.Process.CUBED ? 1f : 0.5f;
				base.map.put(meat.items[process.ordinal()].get().getRegistryName(),
						new FoodPropertyEntry().setAmount(amount).setParent(ori.getRegistryName()));
			}
		}
	}

}
