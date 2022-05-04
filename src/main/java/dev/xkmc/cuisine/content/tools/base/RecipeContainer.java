package dev.xkmc.cuisine.content.tools.base;

import dev.xkmc.cuisine.content.tools.base.tile.CuisineTile;
import dev.xkmc.l2library.base.BaseContainer;

public class RecipeContainer<T extends CuisineTile<T>> extends BaseContainer<RecipeContainer<T>> {

	private final T tile;

	public RecipeContainer(T tile, int size) {
		super(size);
		this.tile = tile;
	}

	public T getTile() {
		return tile;
	}

}
