package com.emberthorne.game.api.item;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import com.google.common.collect.Maps;

import lombok.Getter;

public enum ItemTier {
	
    T1("Tier 1", ChatColor.WHITE, 0, false),
    T2("Tier 2", ChatColor.GREEN, 1, false),
    T3("Tier 3", ChatColor.AQUA, 2, false), 
    T4("Tier 4", ChatColor.LIGHT_PURPLE, 3, false),
    T5("Tier 5", ChatColor.YELLOW, 4, false), 
    MYTHIC(ChatColor.DARK_PURPLE + "" +ChatColor.ITALIC+ "Mythic", ChatColor.DARK_PURPLE, 5, true),
    LEGENDARY(ChatColor.GOLD + "" +ChatColor.ITALIC+ "Legendary", ChatColor.GOLD, 6, true), 
    EMBER(ChatColor.RED + "" +ChatColor.ITALIC+ "Ember", ChatColor.RED, 7, true);

    @Getter private String name;
    @Getter private ChatColor color;
    @Getter private boolean special;
    @Getter private int ladder;

    private HashMap<ItemType, Material> materials;

    ItemTier(String name, ChatColor color, int ladder, boolean special){
        this.name = name;
        this.color = color;
        this.ladder = ladder;
        this.special = special;
        this.materials = Maps.newHashMap();
        this.materials.put(ItemType.BOW, Material.BOW);
        this.materials.put(ItemType.SHIELD, Material.SHIELD);
        switch (ladder){
            case 0:
                materials.put(ItemType.SWORD, Material.WOOD_SWORD);
                materials.put(ItemType.AXE, Material.WOOD_AXE);
                materials.put(ItemType.SPADE, Material.WOOD_SPADE);
                materials.put(ItemType.BOOTS, Material.LEATHER_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.LEATHER_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.LEATHER_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.LEATHER_HELMET);
                break;
            case 1:
                materials.put(ItemType.SWORD, Material.STONE_SWORD);
                materials.put(ItemType.AXE, Material.STONE_AXE);
                materials.put(ItemType.SPADE, Material.STONE_SPADE);
                materials.put(ItemType.BOOTS, Material.CHAINMAIL_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.CHAINMAIL_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.CHAINMAIL_HELMET);
                break;
            case 2:
                materials.put(ItemType.SWORD, Material.IRON_SWORD);
                materials.put(ItemType.AXE, Material.IRON_AXE);
                materials.put(ItemType.SPADE, Material.IRON_SPADE);
                materials.put(ItemType.BOOTS, Material.IRON_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.IRON_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.IRON_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.IRON_HELMET);
                break;
            case 3:
                materials.put(ItemType.SWORD, Material.DIAMOND_SWORD);
                materials.put(ItemType.AXE, Material.DIAMOND_AXE);
                materials.put(ItemType.SPADE, Material.DIAMOND_SPADE);
                materials.put(ItemType.BOOTS, Material.DIAMOND_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.DIAMOND_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.DIAMOND_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.DIAMOND_HELMET);
                break;
            case 4:
                materials.put(ItemType.SWORD, Material.GOLD_SWORD);
                materials.put(ItemType.AXE, Material.GOLD_AXE);
                materials.put(ItemType.SPADE, Material.GOLD_SPADE);
                materials.put(ItemType.BOOTS, Material.GOLD_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.GOLD_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.GOLD_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.GOLD_HELMET);
                break;
            case 5:
                materials.put(ItemType.SWORD, Material.IRON_SWORD);
                materials.put(ItemType.AXE, Material.IRON_AXE);
                materials.put(ItemType.SPADE, Material.IRON_SPADE);
                materials.put(ItemType.BOOTS, Material.IRON_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.IRON_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.IRON_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.IRON_HELMET);
                break;
            case 6:
                materials.put(ItemType.SWORD, Material.DIAMOND_SWORD);
                materials.put(ItemType.AXE, Material.DIAMOND_AXE);
                materials.put(ItemType.SPADE, Material.DIAMOND_SPADE);
                materials.put(ItemType.BOOTS, Material.DIAMOND_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.DIAMOND_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.DIAMOND_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.DIAMOND_HELMET);
                break;
            case 7:
                materials.put(ItemType.SWORD, Material.GOLD_SWORD);
                materials.put(ItemType.AXE, Material.GOLD_AXE);
                materials.put(ItemType.SPADE, Material.GOLD_SPADE);
                materials.put(ItemType.BOOTS, Material.GOLD_BOOTS);
                materials.put(ItemType.LEGGINGS, Material.GOLD_LEGGINGS);
                materials.put(ItemType.CHESTPLATE, Material.GOLD_CHESTPLATE);
                materials.put(ItemType.HELMET, Material.GOLD_HELMET);
                break;
            default:
                break;
        }
    }

    public Material getMaterialFromType(ItemType itemType){
        return materials.get(itemType);
    }
    
    public static ItemTier getTier(String tier){
    	for(ItemTier e : ItemTier.values()){
    		if(e.toString().equalsIgnoreCase(tier)){
    			return e;
    		}
    	}
    	return null;
    }
}
