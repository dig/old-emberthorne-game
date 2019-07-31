package com.emberthorne.game.api.item.weapon.damage;

import lombok.Getter;

public enum WeaponDamage {
	
	// Tier 1
	T1COMMON(1,10),
	T1UNCOMMON(3,13),
	T1RARE(5,15),
	T1UNIQUE(10,20),
	
	// Tier 2
	T2COMMON(13,22),
	T2UNCOMMON(15,27),
	T2RARE(18,31),
	T2UNIQUE(21,37),
	
	// Tier 3
	T3COMMON(35,43),
	T3UNCOMMON(40,51),
	T3RARE(49,58),
	T3UNIQUE(55,69),
	
	// Tier 4
	T4COMMON(69,99),
	T4UNCOMMON(100,119),
	T4RARE(129,210),
	T4UNIQUE(239,321),
	
	// Tier 5
	T5COMMON(350,421),
	T5UNCOMMON(397,441),
	T5RARE(405,463),
	T5UNIQUE(415,483),
	
	// Mythic
	MYTHICNORMAL(498,535),
	MYTHICRARE(525,610),
	MYTHICUNIQUE(543,647),
	
	// Legendary
	LEGENDARYNORMAL(543,625),
	LEGENDARYRARE(641,672),
	LEGENDARYUNIQUE(655,687),
	
	// Ember
	EMBERNORMAL(635,671),
	EMBERRARE(710,755),
	EMBERUNIQUE(810,855);
	
	@Getter int minDmg;
	@Getter int maxDmg;
	
	WeaponDamage(int minDmg, int maxDmg){
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
	}
}
