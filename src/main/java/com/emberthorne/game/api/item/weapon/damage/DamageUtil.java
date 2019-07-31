package com.emberthorne.game.api.item.weapon.damage;

import java.util.Random;

import com.emberthorne.game.api.item.ItemRarities;
import com.emberthorne.game.api.item.ItemTier;
import com.emberthorne.game.api.item.ItemType;

public class DamageUtil {
	public static int getMinDamage(ItemTier tier, ItemRarities rare){
		String construct = tier.toString().toUpperCase()+rare.toString().toUpperCase();
		if(WeaponDamage.valueOf(construct) != null){
			WeaponDamage wd = WeaponDamage.valueOf(construct);
			return wd.minDmg;
		}
		return 1;
	}
	
	public static int getMaxDamage(ItemTier tier, ItemRarities rare){
		String construct = tier.toString().toUpperCase()+rare.toString().toUpperCase();
		if(WeaponDamage.valueOf(construct) != null){
			WeaponDamage wd = WeaponDamage.valueOf(construct);
			return wd.maxDmg;
		}
		return 2;
	}
	
	public static int generateMaxDamage(ItemTier tier, ItemRarities rare){
		int min = getMinDamage(tier,rare);
		int max = getMaxDamage(tier,rare);
		
		Random r = new Random();
		int newmax = r.nextInt(max-(min+1)) + (min+1);
		
		return newmax;
	}
	
	public static boolean canHaveDamage(ItemType t){
		if(t == ItemType.AXE || t == ItemType.SWORD || t == ItemType.SPADE){
			return true;
		}
		return false;
	}
}
