package com.raptorbk.CyanWarriorSwordsRedux.core.init;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;

public interface SurroundEffect {
    void execute(Level world, Player entity, InteractionHand handIn, Block blk);
}