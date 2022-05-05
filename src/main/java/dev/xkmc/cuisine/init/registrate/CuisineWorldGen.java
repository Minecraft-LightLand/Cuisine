package dev.xkmc.cuisine.init.registrate;

import dev.xkmc.cuisine.init.templates.CuisineTreeType;

public class CuisineWorldGen {

	public static void init() {
		for (CuisineTreeType type : CuisineTreeType.values()) {
			type.registerFeature();
		}
	}


}
