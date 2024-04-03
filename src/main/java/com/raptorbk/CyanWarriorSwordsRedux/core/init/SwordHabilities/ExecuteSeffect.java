package com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ExecuteSeffect implements SurroundEffect {

    public static BlockHitResult raytraceFromEntity(Entity e, double distance, boolean fluids) {
        Vec3 Vec3 = e.getEyePosition(1);
        Vec3 Vec31 = e.getViewVector(1);
        Vec3 Vec32 = Vec3.add(Vec31.x * distance, Vec31.y * distance, Vec31.z * distance);
        return e.level().clip(new ClipContext(Vec3, Vec32, ClipContext.Block.OUTLINE, fluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, e));
    }


    public void TargetFromPlayer(Level world, Player entity, Block blk, InteractionHand handIn){

        BlockHitResult TargetPos = raytraceFromEntity(entity, 10, true);
        ItemStack currentSword = entity.getItemInHand(handIn);

        if (TargetPos.getType() != HitResult.Type.BLOCK) {
            return;
        }

            /*
            if (!world.canMineBlockBody(entity, TargetPos.getPos())) {
                return;
            }*/

        if (!entity.mayUseItemAt(TargetPos.getBlockPos(), TargetPos.getDirection(), currentSword)) {
            return;
        }

        BlockPos pos0 = new BlockPos((int) TargetPos.getLocation().x() + 2, (int) TargetPos.getLocation().y() + 2, (int) TargetPos.getLocation().z() - 0);
        BlockPos pos1 = new BlockPos((int) TargetPos.getLocation().x() - 2, (int) TargetPos.getLocation().y() + 2, (int) TargetPos.getLocation().z() - 0);
        BlockPos pos2 = new BlockPos((int) TargetPos.getLocation().x() - 0, (int) TargetPos.getLocation().y() + 2, (int) TargetPos.getLocation().z() + 2);
        BlockPos pos3 = new BlockPos((int) TargetPos.getLocation().x() - 0, (int) TargetPos.getLocation().y() + 2, (int) TargetPos.getLocation().z() - 2);

        //Mid Blocks
        BlockPos pos4 = new BlockPos((int) TargetPos.getLocation().x() + 2, (int) TargetPos.getLocation().y() + 1, (int) TargetPos.getLocation().z() - 0);
        BlockPos pos5 = new BlockPos((int) TargetPos.getLocation().x() - 2, (int) TargetPos.getLocation().y() + 1, (int) TargetPos.getLocation().z() - 0);
        BlockPos pos6 = new BlockPos((int) TargetPos.getLocation().x() - 0, (int) TargetPos.getLocation().y() + 1, (int) TargetPos.getLocation().z() + 2);
        BlockPos pos7 = new BlockPos((int) TargetPos.getLocation().x() - 0, (int) TargetPos.getLocation().y() + 1, (int) TargetPos.getLocation().z() - 2);

        //Lower Blocks
        BlockPos pos8 = new BlockPos((int) TargetPos.getLocation().x() + 2, (int) TargetPos.getLocation().y(), (int) TargetPos.getLocation().z() - 0);
        BlockPos pos9 = new BlockPos((int) TargetPos.getLocation().x() - 2, (int) TargetPos.getLocation().y(), (int) TargetPos.getLocation().z() - 0);
        BlockPos pos10 = new BlockPos((int) TargetPos.getLocation().x() - 0, (int) TargetPos.getLocation().y(), (int) TargetPos.getLocation().z() + 2);
        BlockPos pos11 = new BlockPos((int) TargetPos.getLocation().x() - 0, (int) TargetPos.getLocation().y(), (int) TargetPos.getLocation().z() - 2);


        if (world.isEmptyBlock(pos0)) {
            world.setBlock(pos0, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos4)) {
            world.setBlock(pos4, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos8) || world.getBlockState(pos11).getBlock() == Blocks.SNOW || world.getBlockState(pos11).getBlock() == Blocks.GRASS_BLOCK) {
            world.setBlock(pos8, blk.defaultBlockState(),3);
        }


        if (world.isEmptyBlock(pos1)) {
            world.setBlock(pos1, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos5)) {
            world.setBlock(pos5, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos9) || world.getBlockState(pos11).getBlock() == Blocks.SNOW || world.getBlockState(pos11).getBlock() == Blocks.GRASS_BLOCK) {
            world.setBlock(pos9, blk.defaultBlockState(),3);
        }


        if (world.isEmptyBlock(pos2)) {
            world.setBlock(pos2, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos6)) {
            world.setBlock(pos6, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos10) || world.getBlockState(pos11).getBlock() == Blocks.SNOW || world.getBlockState(pos11).getBlock() == Blocks.GRASS_BLOCK) {
            world.setBlock(pos10, blk.defaultBlockState(),3);
        }


        if (world.isEmptyBlock(pos3)) {
            world.setBlock(pos3, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos7)) {
            world.setBlock(pos7, blk.defaultBlockState(),3);
        }

        if (world.isEmptyBlock(pos11) || world.getBlockState(pos11).getBlock() == Blocks.SNOW || world.getBlockState(pos11).getBlock() == Blocks.GRASS_BLOCK) {
            world.setBlock(pos11, blk.defaultBlockState(),3);
        }

    }



    public void SurroundPlayer(Level world, Player entity, Block blk){
        //Top Blocks
        BlockPos pos0 = new BlockPos((int) (int) entity.getX(), (int) entity.getY()+2, (int) entity.getZ()-1);
        BlockPos pos1 = new BlockPos((int) entity.getX(), (int) entity.getY()+2, (int) entity.getZ()+1);
        BlockPos pos2 = new BlockPos((int) entity.getX()+1, (int) entity.getY()+2, (int) entity.getZ());
        BlockPos pos3 = new BlockPos((int) entity.getX()-1, (int) entity.getY()+2, (int) entity.getZ());

        //Mid Blocks
        BlockPos pos4 = new BlockPos((int) entity.getX(), (int) entity.getY()+1, (int) entity.getZ()-1);
        BlockPos pos5 = new BlockPos((int) entity.getX(), (int) entity.getY()+1, (int) entity.getZ()+1);
        BlockPos pos6 = new BlockPos((int) entity.getX()+1, (int) entity.getY()+1, (int) entity.getZ());
        BlockPos pos7 = new BlockPos((int) entity.getX()-1, (int) entity.getY()+1, (int) entity.getZ());

        //Lower Blocks
        BlockPos pos8 = new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()-1);
        BlockPos pos9 = new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()+1);
        BlockPos pos10 = new BlockPos((int) entity.getX()+1, (int) entity.getY(), (int) entity.getZ());
        BlockPos pos11 = new BlockPos((int) entity.getX()-1, (int) entity.getY(), (int) entity.getZ());

        if(world.isEmptyBlock(pos0)){
            world.setBlock(pos0, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos1)){
            world.setBlock(pos1, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos2)){
            world.setBlock(pos2, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos3)){
            world.setBlock(pos3, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos4)){
            world.setBlock(pos4, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos5)){
            world.setBlock(pos5, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos6)){
            world.setBlock(pos6, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos7)){
            world.setBlock(pos7, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos8) || world.getBlockState(pos8).getBlock() == Blocks.SNOW){
            world.setBlock(pos8, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos9) || world.getBlockState(pos9).getBlock() == Blocks.SNOW){
            world.setBlock(pos9, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos10) || world.getBlockState(pos10).getBlock() == Blocks.SNOW){
            world.setBlock(pos10, blk.defaultBlockState(),3);
        }

        if(world.isEmptyBlock(pos11) || world.getBlockState(pos11).getBlock() == Blocks.SNOW){
            world.setBlock(pos11, blk.defaultBlockState(),3);
        }
    }



    @Override
    public void execute(Level world, Player entity, InteractionHand handIn, Block blk) {
        TargetFromPlayer(world, entity, blk,handIn);
    }
}
