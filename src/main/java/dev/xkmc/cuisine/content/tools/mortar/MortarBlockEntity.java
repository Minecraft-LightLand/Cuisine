package dev.xkmc.cuisine.content.tools.mortar;

import dev.xkmc.cuisine.content.tools.base.PauseableAnimationController;
import dev.xkmc.cuisine.content.tools.base.RecipeContainer;
import dev.xkmc.cuisine.content.tools.base.handlers.StepHandler;
import dev.xkmc.cuisine.content.tools.base.tile.AnimatePauseTile;
import dev.xkmc.cuisine.content.tools.base.tile.CuisineTile;
import dev.xkmc.cuisine.content.tools.base.tile.StepTile;
import dev.xkmc.cuisine.init.registrate.CuisineRecipes;
import dev.xkmc.l2library.block.TickableBlockEntity;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SerialClass
public class MortarBlockEntity extends CuisineTile<MortarBlockEntity> implements TickableBlockEntity, StepTile,
		AnimatePauseTile {

	public static final int ROTATE_TIME = 10, ROTATE_ALLOW = 5;
	@SerialClass.SerialField
	protected int rotate_time = 0;

	@SerialClass.SerialField(toClient = true)
	private final StepHandler<MortarBlockEntity, MortarRecipe> stepHandler = new StepHandler<>(this, CuisineRecipes.RT_MORTAR.get());

	protected final LazyOptional<IItemHandlerModifiable> itemCapability;

	private final AnimationFactory manager = new AnimationFactory(this);

	public MortarBlockEntity(BlockEntityType<MortarBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state, t -> new RecipeContainer<>(t, 4)
				.setPredicate(stack -> t.inventory.canAddWhileHaveSpace(stack, 2)).add(t));
		itemCapability = LazyOptional.of(() -> new InvWrapper(inventory));
	}

	@Override
	public void notifyTile() {
		stepHandler.resetProgress();
		this.setChanged();
		this.sync();
	}

	public void tick() {
		if (rotate_time > 0) {
			rotate_time--;
		}
	}

	@Override
	public boolean step() {
		if (rotate_time <= ROTATE_ALLOW && stepHandler.step()) {
			rotate_time += ROTATE_TIME;
			return true;
		}
		return false;
	}

	@Override
	public int rotateTime() {
		return rotate_time;
	}

	@Override
	public void registerControllers(AnimationData data) {
		PauseableAnimationController<MortarBlockEntity> controller = new PauseableAnimationController<>(this);
		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return manager;
	}
}
