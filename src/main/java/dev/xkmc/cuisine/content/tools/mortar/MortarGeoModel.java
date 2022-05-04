package dev.xkmc.cuisine.content.tools.mortar;

import dev.xkmc.cuisine.content.tools.mill.MillBlockEntity;
import dev.xkmc.cuisine.init.Cuisine;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MortarGeoModel extends AnimatedGeoModel<MortarBlockEntity> {

	@Override
	public ResourceLocation getModelLocation(MortarBlockEntity object) {
		return new ResourceLocation(Cuisine.MODID, "geo/mortar.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MortarBlockEntity object) {
		return new ResourceLocation(Cuisine.MODID, "textures/gecko/mortar.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MortarBlockEntity animatable) {
		return new ResourceLocation(Cuisine.MODID, "animations/mortar.animation.json");
	}
}
