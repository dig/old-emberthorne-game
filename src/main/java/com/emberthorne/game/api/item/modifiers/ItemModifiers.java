package com.emberthorne.game.api.item.modifiers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.emberthorne.game.api.item.nbt.NBTItem;

import lombok.Getter;

public enum ItemModifiers {
	
	SOULBOUND(ChatColor.RED+"Soulbound");
	
	@Getter private String name;
	
	ItemModifiers(String name){
		this.name = name;
	}
	
	public static ItemStack giveModifier(ItemStack i, ItemModifiers mod, String result){
		NBTItem item = new NBTItem(i);
		item.setString(mod.toString(), result);
		return item.getItem();
	}
	
	public static boolean hasModifier(ItemStack i, ItemModifiers mod){
		if(i != null && i.getType() != Material.AIR){
			NBTItem item = new NBTItem(i);
			if(item.hasKey(mod.toString())){
				return true;
			}
		}
		return false;
	}
	
	public static String getModifier(ItemStack i, ItemModifiers mod){
		if(i != null && i.getType() != Material.AIR){
			NBTItem item = new NBTItem(i);
			if(item.hasKey(mod.toString())){
				return item.getString(mod.toString());
			}
		}
		return null;
	}
}
