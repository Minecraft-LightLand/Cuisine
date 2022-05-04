package dev.xkmc.cuisine.content.tools.mortar;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.cuisine.content.tools.base.ContentRenderer;
import dev.xkmc.cuisine.content.tools.base.tile.TileInfoOverlay;
import dev.xkmc.cuisine.content.tools.pan.PanBlockEntity;
import dev.xkmc.l2library.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MortarRenderer extends GeoBlockRenderer<MortarBlockEntity> {

	public MortarRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		super(rendererDispatcherIn, new MortarGeoModel());
	}

	@Override
	public void render(BlockEntity tile, float ptick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
		super.render(tile, ptick, pose, buffer, light, overlay);
		MortarBlockEntity mortar = (MortarBlockEntity) tile;
		ContentRenderer.renderBasinContent(new ContentRenderer.Context(pose, buffer, light, overlay, ptick,
				1 / 16f, 15 / 16f, 1 / 16f, 7 / 16f, 1 / 16f, 15 / 16f,
				0.125f, 0.5f,true,
				mortar, mortar.getBlockPos(), mortar.getLevel(), 0));
	}

}
