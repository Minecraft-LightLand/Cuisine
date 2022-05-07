package dev.xkmc.cuisine.content.food;

import dev.xkmc.cuisine.init.Cuisine;
import dev.xkmc.cuisine.init.data.CuisineModConfig;
import dev.xkmc.cuisine.util.DamageUtil;
import dev.xkmc.l2library.effects.EffectUtil;
import dev.xkmc.l2library.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class TasteEffect extends MobEffect {

	public static class Attribs {

		private final Attribute attrib;
		private final AttributeModifier.Operation op;
		private final float amount;

		public Attribs(Attribute attrib, AttributeModifier.Operation op, float amount) {
			this.attrib = attrib;
			this.op = op;
			this.amount = amount;
		}

	}

	public record Config(MobEffectCategory category, int color, int period, int damage, Attribs... attribs) {
	}

	public static final int DURATION = 24 * 60 * 60 * 20;

	public static final DamageSource SOURCE = new DamageSource("food_poison");

	private final Config config;

	public TasteEffect(Config config, String name) {
		super(config.category, config.color);
		this.config = config;
		UUID id = MathHelper.getUUIDfromString(Cuisine.MODID + ":" + name);
		for (Attribs a : config.attribs) {
			addAttributeModifier(a.attrib, id.toString(), a.amount, a.op);
		}
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int lv) {
		if (entity instanceof Player player) {
			if (player.getFoodData().getFoodLevel() < CuisineModConfig.COMMON.minHunger.get()) {
				player.removeEffect(this);
				return;
			}
			if (config.damage > 0) DamageUtil.dealDamage(entity, SOURCE, config.damage);
			if (config.damage < 0) entity.heal(-config.damage);
		}
	}

	@Override
	public boolean isDurationEffectTick(int tick, int lv) {
		return tick % (config.period > 0 ? Math.min(20, config.period) : 20) == 0;
	}

	@SubscribeEvent
	public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntityLiving() instanceof Player player) {
			if (player.level.isClientSide)
				return;
			EffectUtil.removeEffect(player, e -> e.getEffect() instanceof TasteEffect && e.getDuration() < DURATION);
		}
	}

}
