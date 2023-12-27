package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.class_5093;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;

public class EmptyBlockView implements BlockView {
    public static EmptyBlockView INSTANCE = new EmptyBlockView();

    @Override
    public Block getBlock(int x, int y, int z) {
        return null;
    }

    @Override
    public BlockEntity getBlockEntity(int x, int y, int z) {
        return null;
    }

    @Override
    public int getBlockData(int x, int y, int z) {
        return 0;
    }

    @Override
    public boolean isAir(int x, int y, int z) {
        return false;
    }

    @Override
    public Biome getBiome(int x, int z) {
        return null;
    }

    @Override
    public int method_3771() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public class_5093 method_22399() {
        return null;
    }

    @Override
    public int method_3719(int i, int j, int k, int l) {
        return 0;
    }

    @Override
    public int method_3778(int i, int j, int k, int l) {
        return 0;
    }
}
