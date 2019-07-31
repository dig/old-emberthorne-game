package com.emberthorne.game.api.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.emberthorne.game.api.item.armor.hp.HealthUtil;
import com.emberthorne.game.api.item.nbt.NBTItem;
import com.emberthorne.game.api.item.weapon.damage.DamageUtil;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;

public class ItemGenerator {

	public static ItemStack getItem(ItemTier tier){
		ItemType ty = ItemType.random();
		ItemRarities ir = ItemRarities.random(tier);
		String name = tier.getColor()+ItemType.getItemName(ty, tier);
		return createItem(tier,ty,ir,name);
	}
	
	public static ItemStack createItem(ItemTier tier, ItemType type, ItemRarities rare, String name){
		HashMap<String, String> tagS = new HashMap<String, String>();
		HashMap<String, Integer> tagI = new HashMap<String, Integer>();
		
		ItemStack item = new ItemStack(tier.getMaterialFromType(type),1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		List<String> lore = new ArrayList<String>();
		
		tagS.put("EMItem", "item");
		tagS.put("name", name);
		tagS.put("type", type.toString());
		tagS.put("rarity", rare.toString());
		tagS.put("tier", tier.toString());
		
		if(DamageUtil.canHaveDamage(type)){
			int minDmg = DamageUtil.getMinDamage(tier, rare);
			int maxDmg = DamageUtil.generateMaxDamage(tier, rare);
			
			if(name.contains("Training")){
				minDmg = 1;
				maxDmg = 5;
			}
			
			tagI.put("minDmg", minDmg);
			tagI.put("maxDmg", maxDmg);
			
			lore.add(ChatColor.translateAlternateColorCodes('&', "&aDamage &f"+minDmg+" - "+maxDmg+""));
		}
		
		if(HealthUtil.canHaveHealth(type)){
			int Hp = HealthUtil.generateRandomHealth(tier, rare);
			if(name.contains("Training")){
				Hp = 2;
			}
			tagI.put("health", Hp);
			
			lore.add(ChatColor.translateAlternateColorCodes('&', "&f+"+Hp+" &aHP"));
		}
		
		if(tier.isSpecial()){
			lore.add(tier.getName());
		}
		if(rare != ItemRarities.NORMAL){
			lore.add(rare.getName());
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		item = NBTItem.addIntegerTags(item, tagI);
		item = NBTItem.addStringTags(item, tagS);
		
		if(tier.isSpecial()){
			item = addGlow(item);
		}
		
		return item;
	}
	
    public static ItemStack addGlow(ItemStack item){
        net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }
}
