package dev.xkmc.cuisine.content.tools.base;

import dev.xkmc.cuisine.content.tools.base.tile.AnimatePauseTile;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class PauseableAnimationController<T extends AnimatePauseTile> extends AnimationController<T> {

	private double last_tick = 0, frame = 0;
	private boolean paused = true;

	public PauseableAnimationController(T animatable) {
		super(animatable, "main", 0, PauseableAnimationController::test);
	}

	private static <T extends AnimatePauseTile> PlayState test(AnimationEvent<T> event) {
		if (event.getController() instanceof PauseableAnimationController controller) {
			controller.setAnimation(new AnimationBuilder().addAnimation("rotate", true));
			controller.testFrame(event.getAnimatable());
		}
		return PlayState.CONTINUE;
	}

	private void testFrame(T te) {
		paused = te.rotateTime() == 0;
	}

	@Override
	protected double adjustTick(double tick) {
		if (last_tick == 0) {
			last_tick = tick;
		}
		if (!paused) {
			frame += tick - last_tick;
		}
		last_tick = tick;
		if (currentAnimation != null && currentAnimation.animationLength != null)
			frame %= currentAnimation.animationLength;
		return frame;
	}
}
