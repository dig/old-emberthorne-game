package com.emberthorne.game.api.mobs.data;

import com.emberthorne.game.api.mobs.MobTiers;

public interface EquipEntity extends Entity{
    void generate();

    int getLevel();
    
    int getDamage();
    
    double getAttackSpeed();
    
    MobTiers getTier();
}
