package dev.xkmc.cuisine.init.data;

import dev.xkmc.cuisine.content.food.FoodConfig;
import dev.xkmc.cuisine.content.food.FoodPropertyEntry;
import dev.xkmc.cuisine.init.Cuisine;
import dev.xkmc.cuisine.init.registrate.CuisineFlavor;
import dev.xkmc.cuisine.init.templates.CuisineCropType;
import dev.xkmc.cuisine.init.templates.CuisineTreeType;
import dev.xkmc.cuisine.init.templates.ProcessedMeat;
import dev.xkmc.cuisine.init.templates.SimpleItem;
import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.network.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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
		FoodConfig veges = new FoodConfig();
		addVegeMaterials(veges);
		map.put("veges", veges);
		FoodConfig misc = new FoodConfig();
		addMiscMaterials(misc);
		map.put("misc", misc);
	}

	@SuppressWarnings("ConstantConditions")
	public static void addProcessedMaterials(FoodConfig base) {
		for (ProcessedMeat.Meat meat : ProcessedMeat.Meat.values()) {
			Item ori = meat.original.get();
			Item cok = meat.cooked.get();
			FoodProperties fp_ori = ori.getFoodProperties(ori.getDefaultInstance(), null);
			FoodProperties fp_cok = cok.getFoodProperties(cok.getDefaultInstance(), null);
			FoodPropertyEntry entry = new FoodPropertyEntry()
					.setDefault()
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
				FoodPropertyEntry ent = new FoodPropertyEntry().setAmount(amount).setParent(ori.getRegistryName());
				if (meat == ProcessedMeat.Meat.COD || meat == ProcessedMeat.Meat.SALMON) {
					ent.flavors.put(CuisineFlavor.FISHY.get(), 2f);
					ent.flavors.put(CuisineFlavor.TASTY.get(), 1f);
				}
				base.map.put(meat.items[process.ordinal()].get().getRegistryName(), ent);
			}
		}
	}

	public static void addVegeMaterials(FoodConfig base) {
		// 相对饱和：金萝卜=1.2，肉类=0.8，主食=0.6，蔬菜=0.3
		{
			// 添加蔬菜食物属性，样板：玉米
			base.map.put(CuisineCropType.CORN.getSeed().getRegistryName(), new FoodPropertyEntry() //  注册创建食物属性
					.setDefault().setRawHunger(4f).setRawSaturationMod(0.6f) // 生食，4饱食，0.6相对饱和(1.2/0.8/0.6/0.3)
					.put(CuisineFlavor.GREASY.get(), -0.5f)); // 减少0.5点油腻

		}
		{
			// 样板：辣椒
			base.map.put(CuisineCropType.CHILI.getSeed().getRegistryName(), new FoodPropertyEntry()
					.setDefault().setRawHunger(1f).setRawSaturationMod(0.3f)
					.put(CuisineFlavor.GREASY.get(), -0.5f)
					.put(CuisineFlavor.SWEET.get(), -2f));// 辣是负的甜
		}
		{
			// 白菜
			base.map.put(CuisineCropType.CHINESE_CABBAGE.getSeed().getRegistryName(), new FoodPropertyEntry()
					.setDefault().setRawHunger(2f).setRawSaturationMod(0.3f)
					.put(CuisineFlavor.GREASY.get(), -1f));
		}
		// 剩余的蔬菜
		CuisineCropType.CUCUMBER.getSeed().getRegistryName();
		CuisineCropType.EGGPLANT.getSeed().getRegistryName();
		CuisineCropType.GARLIC.getSeed().getRegistryName();
		CuisineCropType.GINGER.getSeed().getRegistryName();
		CuisineCropType.GREEN_PEPPER.getSeed().getRegistryName();
		CuisineCropType.LEEK.getSeed().getRegistryName();
		CuisineCropType.LETTUCE.getSeed().getRegistryName();
		CuisineCropType.ONION.getSeed().getRegistryName();
		CuisineCropType.PEANUT.getSeed().getRegistryName();
		CuisineCropType.RED_PEPPER.getSeed().getRegistryName();
		CuisineCropType.RICE.getSeed().getRegistryName();
		CuisineCropType.SCALLION.getSeed().getRegistryName();
		CuisineCropType.SESAME.getSeed().getRegistryName();
		CuisineCropType.SICHUAN_PEPPER.getSeed().getRegistryName();
		CuisineCropType.SOYBEAN.getSeed().getRegistryName();
		CuisineCropType.SPINACH.getSeed().getRegistryName();
		CuisineCropType.TOMATO.getSeed().getRegistryName();
		CuisineCropType.TURNIP.getSeed().getRegistryName();
		Items.POTATO.getRegistryName();
		Items.BEETROOT.getRegistryName();
		Items.CARROT.getRegistryName();
		Items.BAMBOO.getRegistryName();
		Items.PUMPKIN.getRegistryName();
		Items.PUMPKIN_SEEDS.getRegistryName();
		Items.KELP.getRegistryName();
		Items.DRIED_KELP.getRegistryName();
		Items.SEAGRASS.getRegistryName();
		Items.BROWN_MUSHROOM.getRegistryName();
		Items.RED_MUSHROOM.getRegistryName();

		// 水果
		{
			base.map.put(CuisineTreeType.CITRON.fruit.get().getRegistryName(), new FoodPropertyEntry()
					.setDefault().setRawHunger(1f).setRawSaturationMod(0.3f)
					.put(CuisineFlavor.GREASY.get(), -1f)
					.put(CuisineFlavor.SOUR.get(), 2f));
		}
		{
			base.map.put(CuisineTreeType.MANDARIN.fruit.get().getRegistryName(), new FoodPropertyEntry()
					.setDefault().setRawHunger(1f).setRawSaturationMod(0.3f)
					.put(CuisineFlavor.GREASY.get(), -1f)
					.put(CuisineFlavor.SWEET.get(), 1f)
					.put(CuisineFlavor.SOUR.get(), 1f));
		}
		// 剩余的水果
		CuisineTreeType.GRAPEFRUIT.fruit.get().getRegistryName();
		CuisineTreeType.LEMON.fruit.get().getRegistryName();
		CuisineTreeType.LIME.fruit.get().getRegistryName();
		CuisineTreeType.ORANGE.fruit.get().getRegistryName();
		CuisineTreeType.POMELO.fruit.get().getRegistryName();
		Items.APPLE.getRegistryName();
		Items.CHORUS_FRUIT.getRegistryName();
		Items.SWEET_BERRIES.getRegistryName();
		Items.GLOW_BERRIES.getRegistryName();
		Items.MELON_SLICE.getRegistryName();
		Items.SUGAR_CANE.getRegistryName();

	}

	public static void addMiscMaterials(FoodConfig base) {
		Items.TROPICAL_FISH.getRegistryName();
		Items.PUFFERFISH.getRegistryName();
		Items.NETHER_WART.getRegistryName();
		Items.WARPED_FUNGUS.getRegistryName();
		Items.CRIMSON_FUNGUS.getRegistryName();
		Items.SPIDER_EYE.getRegistryName();
		Items.BONE.getRegistryName();
		Items.HONEYCOMB.getRegistryName();
		Items.COCOA_BEANS.getRegistryName();

		Items.SUGAR.getRegistryName();

		SimpleItem.CHILI_POWDER.item.get().getRegistryName();
		SimpleItem.SALT.item.get().getRegistryName();
		SimpleItem.SICHUAN_PEPPER_POWDER.item.get().getRegistryName();
		SimpleItem.TOFU.item.get().getRegistryName();
		SimpleItem.WHITE_RICE.item.get().getRegistryName();
	}

}
