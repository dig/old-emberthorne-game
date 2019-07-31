package com.emberthorne.game.api.item;

import org.bukkit.ChatColor;

import com.emberthorne.game.api.utils.WeightedCollection;

import lombok.Getter;

public enum ItemRarities {
	
	NORMAL("", 0),
    COMMON(ChatColor.BLUE + "" + ChatColor.ITALIC + "Common", 0),
    UNCOMMON(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC +"Uncommon", 1),
    RARE(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC +"Rare", 2),
    UNIQUE(ChatColor.GOLD + "" + ChatColor.ITALIC +"Unique", 3);

    @Getter private String name;
    @Getter private int ladder;

    public static WeightedCollection<ItemRarities> weightedCollection = new WeightedCollection<ItemRarities>();
    public static WeightedCollection<ItemRarities> otherCollection = new WeightedCollection<ItemRarities>();
    public static boolean generated = false;

    ItemRarities(String name, int ladder){
        this.name = name;
        this.ladder = ladder;
    }
    
    public static ItemRarities random(ItemTier t){
        if (generated){
        	if(t.isSpecial()){
        		return otherCollection.next();
        	}
            return weightedCollection.next();
        } 
        else{
            generate();
        	if(t.isSpecial()){
        		return otherCollection.next();
        	}
            return weightedCollection.next();
        }
    }

    private static void generate(){
    	weightedCollection.add(0.684, ItemRarities.COMMON);
    	weightedCollection.add(0.211, ItemRarities.UNCOMMON);
    	weightedCollection.add(0.02971, ItemRarities.RARE);
    	weightedCollection.add(0.0052, ItemRarities.UNIQUE);
    	
    	otherCollection.add(0.35971, ItemRarities.NORMAL);
    	otherCollection.add(0.02971, ItemRarities.RARE);
    	otherCollection.add(0.0052, ItemRarities.UNIQUE);
    	
        generated = true;
    }
}
