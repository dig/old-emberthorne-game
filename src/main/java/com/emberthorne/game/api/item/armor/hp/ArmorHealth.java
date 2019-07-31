package com.emberthorne.game.api.item.armor.hp;

import lombok.Getter;

public enum ArmorHealth {
	
	// Tier 1
	T1COMMON(15,27),
	T1UNCOMMON(18,52),
	T1RARE(21,54),
	T1UNIQUE(27,57),
	
	// Tier 2
	T2COMMON(35,74),
	T2UNCOMMON(59,143),
	T2RARE(101,175),
	T2UNIQUE(145,211),
	
	// Tier 3
	T3COMMON(200,245),
	T3UNCOMMON(250,310),
	T3RARE(275,445),
	T3UNIQUE(300,542),
	
	// Tier 4
	T4COMMON(325,710),
	T4UNCOMMON(350,781),
	T4RARE(409,910),
	T4UNIQUE(510,1005),
	
	// Tier 5
	T5COMMON(635,1121),
	T5UNCOMMON(721,1300),
	T5RARE(803,1400),
	T5UNIQUE(901,1500),
	
	// Mythic
	MYTHICNORMAL(1009,1550),
	MYTHICRARE(1101,1650),
	MYTHICUNIQUE(1201,1750),
	
	// Legendary
	LEGENDARYNORMAL(1307,1850),
	LEGENDARYRARE(1410,1950),
	LEGENDARYUNIQUE(1575,2000),
	
	// Ember
	EMBERNORMAL(1675,2100),
	EMBERRARE(1735,2300),
	EMBERUNIQUE(1892,2500);
	
	@Getter int minHp;
	@Getter int maxHp;
	
	ArmorHealth(int minDmg, int maxDmg){
		this.minHp = minDmg;
		this.maxHp = maxDmg;
	}
}
