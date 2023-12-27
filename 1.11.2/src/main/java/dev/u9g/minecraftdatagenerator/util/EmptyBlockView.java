package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.level.LevelGeneratorType;
import org.jetbrains.annotations.Nullable;

public class EmptyBlockView implements BlockView {
    public static EmptyBlockView INSTANCE = new EmptyBlockView();

    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return null;
    }

    @Override
    public int getLight(BlockPos pos, int minBlockLight) {
        return 0;
    }

    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isAir(BlockPos pos) {
        return false;
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return null;
    }

    @Override
    public int getStrongRedstonePower(BlockPos pos, Direction direction) {
        return 0;
    }

    @Override
    public LevelGeneratorType getGeneratorType() {
        return null;
    }
}
