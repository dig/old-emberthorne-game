package com.emberthorne.game.api.mobs;

import lombok.Getter;
import lombok.Setter;

public enum MobTiers {
	
    T1(1, 
    		1, 7, 
    		10, 100, 
    		1, 10, 
    		0.7, 1.5,
    		5, 37), 
    T2(2, 
    		7, 18, 
    		75, 400, 
    		20, 100, 
    		0.7, 1,
    		47, 72), 
    T3(3, 
    		18, 29, 
    		400, 2050, 
    		200, 501, 
    		0.5, 1,
    		143, 222), 
    T4(4, 
    		29, 37, 
    		2050, 5000, 
    		500, 761, 
    		0.4, 1,
    		227, 335), 
    T5(5, 
    		37, 50, 
    		5000, 18500, 
    		666, 2501, 
    		2, 4,
    		513, 654);

    @Setter @Getter private int minLevel;
    @Setter @Getter private int maxLevel;
    
    @Setter @Getter private int minHealth;
    @Setter @Getter private int maxHealth;
    
    @Setter @Getter private int minDamage;
    @Setter @Getter private int maxDamage;
    
    @Setter @Getter private double minAttackSpeed;
    @Setter @Getter private double maxAttackSpeed;
    
    @Setter @Getter private int minExp;
    @Setter @Getter private int maxExp;
    
    MobTiers(int order, int minLevel, int maxLevel, int minHealth, int maxHealth, int minDamage, int maxDamage, 
    		double minAttackSpeed, double maxAttackSpeed, int minExp, int maxExp){
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.minAttackSpeed = minAttackSpeed;
        this.maxAttackSpeed = maxAttackSpeed;
        this.minExp = minExp;
        this.maxExp = maxExp;
    }
    
    public static MobTiers getTier(int tier){
    	MobTiers result = null;
    	
    	for(MobTiers t : MobTiers.values()){
    		if(t.toString().equals("T"+tier)){
    			result = t;
    		}
    	}
    	
    	return result;
    }
}
