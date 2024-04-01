package com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities;

import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Tier;

public class METEOR_CLASS_SWORD extends SWORD_CWSR {

    public METEOR_CLASS_SWORD(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public boolean delayMeteor=false;
    public LargeFireball delayedMeteor=null;

    public void setDelayMeteor(boolean delayMeteor) {
        this.delayMeteor = delayMeteor;
    }

    public boolean getDelayMeteor(){
        return this.delayMeteor;
    }


    public LargeFireball getDelayedMeteor() {
        return this.delayedMeteor;
    }

    public void setDelayedMeteor(LargeFireball delayedMeteor) {
        this.delayedMeteor = delayedMeteor;
    }
}