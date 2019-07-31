package com.emberthorne.game.api.item.armor.hp;

import java.util.Random;

import com.emberthorne.game.api.item.ItemRarities;
import com.emberthorne.game.api.item.ItemTier;
import com.emberthorne.game.api.item.ItemType;

public class HealthUtil {
	public static int getMinHealth(ItemTier tier, ItemRarities rare){
		String construct = tier.toString().toUpperCase()+rare.toString().toUpperCase();
		if(ArmorHealth.valueOf(construct) != null){
			ArmorHealth wd = ArmorHealth.valueOf(construct);
			return wd.minHp;
		}
		return 1;
	}
	
	public static int getMaxHealth(ItemTier tier, ItemRarities rare){
		String construct = tier.toString().toUpperCase()+rare.toString().toUpperCase();
		if(ArmorHealth.valueOf(construct) != null){
			ArmorHealth wd = ArmorHealth.valueOf(construct);
			return wd.maxHp;
		}
		return 2;
	}
	
	public static int generateRandomHealth(ItemTier tier, ItemRarities rare){
		int min = getMinHealth(tier,rare);
		int max = getMaxHealth(tier,rare);
		
		Random r = new Random();
		int newmax = r.nextInt(max-(min)) + (min);
		
		return newmax;
	}
	
	public static boolean canHaveHealth(ItemType t){
		if(t == ItemType.HELMET || t == ItemType.CHESTPLATE || t == ItemType.LEGGINGS || t == ItemType.BOOTS){
			return true;
		}
		return false;
	}
}
