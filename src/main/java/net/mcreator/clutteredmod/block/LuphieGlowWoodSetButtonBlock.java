
package net.mcreator.clutteredmod.block;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.clutteredmod.init.LuphieclutteredmodModBlocks;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import java.util.List;
import java.util.Collections;

public class LuphieGlowWoodSetButtonBlock extends WoodButtonBlock {
	public static BlockBehaviour.Properties PROPERTIES = FabricBlockSettings.of(Material.WOOD).sound(SoundType.WOOD).strength(2f, 3f)
			.lightLevel(s -> 1).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true);

	public LuphieGlowWoodSetButtonBlock() {
		super(PROPERTIES);
		FlammableBlockRegistry.getDefaultInstance().add(this, 5, 0);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		BlockRenderLayerMap.INSTANCE.putBlock(LuphieclutteredmodModBlocks.LUPHIE_GLOW_WOOD_SET_BUTTON, RenderType.solid());
	}
}
