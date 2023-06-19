
package net.mcreator.clutteredmod.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.RenderType;

import net.mcreator.clutteredmod.init.LuphieclutteredmodModBlocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import java.util.List;
import java.util.Collections;

public class LuphieHoneycombLampBlock extends Block {
	public static BlockBehaviour.Properties PROPERTIES = FabricBlockSettings.of(Material.DECORATION).sound(SoundType.WOOL).strength(1f, 10f)
			.lightLevel(s -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public LuphieHoneycombLampBlock() {
		super(PROPERTIES);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return (switch (state.getValue(FACING)) {
			default -> Shapes.or(box(7, 1, 6, 9, 9, 8), box(4, 0, 3, 12, 1, 11), box(2, 9, 1, 14, 19, 13), box(3.5, 5, 5.5, 5.5, 7, 8.5));
			case NORTH -> Shapes.or(box(7, 1, 8, 9, 9, 10), box(4, 0, 5, 12, 1, 13), box(2, 9, 3, 14, 19, 15), box(10.5, 5, 7.5, 12.5, 7, 10.5));
			case EAST -> Shapes.or(box(6, 1, 7, 8, 9, 9), box(3, 0, 4, 11, 1, 12), box(1, 9, 2, 13, 19, 14), box(5.5, 5, 10.5, 8.5, 7, 12.5));
			case WEST -> Shapes.or(box(8, 1, 7, 10, 9, 9), box(5, 0, 4, 13, 1, 12), box(3, 9, 2, 15, 19, 14), box(7.5, 5, 3.5, 10.5, 7, 5.5));
		}).move(offset.x, offset.y, offset.z);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
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
		BlockRenderLayerMap.INSTANCE.putBlock(LuphieclutteredmodModBlocks.LUPHIE_HONEYCOMB_LAMP, RenderType.solid());
	}
}
