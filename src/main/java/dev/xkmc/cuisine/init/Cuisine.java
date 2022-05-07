package dev.xkmc.cuisine.init;

import dev.xkmc.cuisine.content.food.FoodMaterialItem;
import dev.xkmc.cuisine.content.food.TasteEffect;
import dev.xkmc.cuisine.content.misc.CuisineBottleItem;
import dev.xkmc.cuisine.content.tools.base.tile.TileInfoOverlay;
import dev.xkmc.cuisine.content.veges.CornBlock;
import dev.xkmc.cuisine.init.data.*;
import dev.xkmc.cuisine.init.registrate.*;
import dev.xkmc.cuisine.init.templates.WoodType;
import dev.xkmc.l2library.base.LcyRegistrate;
import dev.xkmc.l2library.network.PacketHandlerWithConfig;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(Cuisine.MODID)
public class Cuisine {

	public static final String MODID = "cuisine";
	public static final CuisineRegistrate REGISTRATE = new CuisineRegistrate(MODID);
	public static final PacketHandlerWithConfig NETWORK = new PacketHandlerWithConfig(
			new ResourceLocation(MODID, "main"), 1, "cuisine_config");

	public Cuisine() {
		CuisineBlocks.register();
		CuisineItems.register();
		CuisineFluids.register();
		CuisineFlavor.register();
		CuisineTags.register();
		CuisineModConfig.init();
		CuisineRecipes.register(FMLJavaModLoadingContext.get().getModEventBus());
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);

		FMLJavaModLoadingContext.get().getModEventBus().register(Cuisine.class);
		FMLJavaModLoadingContext.get().getModEventBus().register(CuisineRendering.class);
		MinecraftForge.EVENT_BUS.register(CornBlock.class);
		MinecraftForge.EVENT_BUS.register(CuisineBottleItem.class);
		MinecraftForge.EVENT_BUS.register(FoodMaterialItem.class);
		MinecraftForge.EVENT_BUS.register(TasteEffect.class);
	}

	@SubscribeEvent
	public static void onDataGen(GatherDataEvent event) {
		event.getGenerator().addProvider(new CuisineConfigGen(event.getGenerator()));
	}

	@SubscribeEvent
	public static void onCommonInit(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			WoodType.onCommonInit();
			CuisineWorldGen.init();
		});
	}

	@SubscribeEvent
	public static void onClientInit(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			WoodType.onClientInit();
			CuisineBottleItem.onClientInit();
			OverlayRegistry.registerOverlayAbove(ForgeIngameGui.CROSSHAIR_ELEMENT, "Block Info", new TileInfoOverlay());
		});
	}

}
