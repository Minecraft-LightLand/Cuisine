package dev.xkmc.cuisine.content.tools.board;

import com.mojang.datafixers.util.Pair;
import dev.xkmc.cuisine.content.tools.base.RecipeContainer;
import dev.xkmc.cuisine.content.tools.base.tile.CuisineTile;
import dev.xkmc.cuisine.init.data.CuisineModConfig;
import dev.xkmc.cuisine.init.registrate.ProcessedMeat;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

@SerialClass
public class ChoppingBoardBlockEntity extends CuisineTile<ChoppingBoardBlockEntity> {

	public ChoppingBoardBlockEntity(BlockEntityType<ChoppingBoardBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state, e -> new RecipeContainer<>(e, 2)
				.setPredicate(x -> process(x).isPresent() && e.inventory.canAddWhileHaveSpace(x, 1)).add(e));
	}

	@Override
	public void notifyTile() {
		setChanged();
		sync();
	}

	public boolean leftClick() {
		if (level == null || level.isClientSide()) return true;
		ItemStack stack = inventory.getItem(0);
		Optional<Pair<ProcessedMeat.Meat, Optional<ProcessedMeat.Process>>> kind = process(stack);
		if (kind.isPresent()) {
			ProcessedMeat.Meat meat = kind.get().getFirst();
			Optional<ProcessedMeat.Process> process = kind.get().getSecond();
			ProcessedMeat.Process next = ProcessedMeat.Process.CUBED;
			int count = CuisineModConfig.COMMON.rawCut.get();
			if (process.isPresent()) {
				next = process.get().left;
				count = process.get().left_count.getAsInt();
			}
			if (next != null) {
				Item item = meat.items[next.ordinal()].get();
				return tryProcess(stack, item, count);
			}
		}
		return false;
	}

	public boolean rightClick() {
		if (level == null || level.isClientSide()) return true;
		ItemStack stack = inventory.getItem(0);
		Optional<Pair<ProcessedMeat.Meat, Optional<ProcessedMeat.Process>>> kind = process(stack);
		if (kind.isPresent()) {
			ProcessedMeat.Meat meat = kind.get().getFirst();
			Optional<ProcessedMeat.Process> process = kind.get().getSecond();
			ProcessedMeat.Process next = ProcessedMeat.Process.CUBED;
			int count = CuisineModConfig.COMMON.rawCut.get();
			if (process.isPresent()) {
				next = process.get().right;
				count = process.get().right_count.getAsInt();
			}
			if (next != null) {
				Item item = meat.items[next.ordinal()].get();
				return tryProcess(stack, item, count);
			}
		}
		return false;
	}

	private boolean tryProcess(ItemStack first, Item second, int count) {
		if (first.isEmpty()) return false;
		if (inventory.canRecipeAddItem(new ItemStack(second ,count))) {
			inventory.addItem(new ItemStack(second ,count));
			first.shrink(1);
			if (inventory.getItem(0).isEmpty()) {
				ItemStack stack = inventory.getItem(1);
				inventory.setItem(0, stack.copy());
				inventory.setItem(1, ItemStack.EMPTY);
			}
			inventory.setChanged();
			return true;
		}
		return false;
	}

	private static Optional<Pair<ProcessedMeat.Meat, Optional<ProcessedMeat.Process>>> process(ItemStack stack) {
		for (ProcessedMeat.Meat meat : ProcessedMeat.Meat.values()) {
			if (stack.getItem() == meat.original.get()) {
				return Optional.of(Pair.of(meat, Optional.empty()));
			}
			for (ProcessedMeat.Process process : ProcessedMeat.Process.values()) {
				if (stack.getItem() == meat.items[process.ordinal()].get())
					return Optional.of(Pair.of(meat, Optional.of(process)));
			}
		}
		return Optional.empty();
	}
}
