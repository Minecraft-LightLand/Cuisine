package dev.xkmc.cuisine.content.misc;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class KnifeItem extends Item {

	private final Multimap<Attribute, AttributeModifier> map;

	public KnifeItem(Properties properties) {
		super(properties);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
				6, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				-2.4, AttributeModifier.Operation.ADDITION));
		map = builder.build();
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
		if (!player.level.isClientSide()) {
			BlockState state = player.level.getBlockState(pos);
			state.attack(player.level, pos, player);
		}
		return true;
	}

	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
		stack.hurtAndBreak(1, user, (u) -> u.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? map : ImmutableMultimap.of();
	}
}
