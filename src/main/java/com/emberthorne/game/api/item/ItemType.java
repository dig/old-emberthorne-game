package com.emberthorne.game.api.item;

import com.emberthorne.game.api.utils.WeightedCollection;

import lombok.Getter;

public enum ItemType {
	
    SWORD("Sword", "Shortsword", "Broadsword", "Blessed Sword", "Gilded Sword", "Mythical Sword", "Eternal Sword", "Embered Sword"),
    AXE("Axe", "Lumber Axe", "Hatchet", "Great Axe", "Gilded Axe", "Forged Axe", "Eternal Axe", "Embered Axe"), 
    SPADE("Spear", "Wood Spike", "Long Spear", "Battle Spear", "Gilded Spear", "Forged Spear", "Eternal Spear", "Embered Spear"),
    SHIELD("Defender", "Buckler", "Great Shield", "Ancient Shield", "Forged Shield", "Mythical Shield", "Eternal Shield", "Embered Shield"), 
    BOW("Shortbow","Longbow","Magic Bow", "Great Bow", "Ancient Bow", "Forged Bow", "Eternal Bow", "Embered Bow"), 
    HELMET("Coif","Medium Helm", "Full Helm", "Ancient Helm", "Gilded Helm", "Mythical Helm", "Eternal Helm", "Embered Helm"), 
    CHESTPLATE("Leather Chest", "Chainmail", "Platemail", "Magic Platemail", "Ancient Chestplate", "Forged Chestplate", "Eternal Chestplate", "Embered Chestplate"), 
    LEGGINGS("Leather Legs", "Chainmail Leggings", "Platemail Leggings", "Magic Platemail Leggings", "Gilded Leggings", "Forged Legs", "Eternal Legs", "Embered Legs"), 
    BOOTS("Leather Boots", "Chain Boots", "Plate Boots", "Magic Boots", "Gilded Boots", "Mythical Boots", "Eternal Boots", "Embered Boots");
	
	@Getter private String T1Name;
	@Getter private String T2Name;
	@Getter private String T3Name;
	@Getter private String T4Name;
	@Getter private String T5Name;
	@Getter private String MythicName;
	@Getter private String LegendaryName;
	@Getter private String EmberName;
	
	ItemType(String T1, String T2, String T3, String T4, String T5, String Mythic, String Legendary, String Ember){
		this.T1Name = T1;
		this.T2Name = T2;
		this.T3Name = T3;
		this.T4Name = T4;
		this.T5Name = T5;
		this.MythicName = Mythic;
		this.LegendaryName = Legendary;
		this.EmberName = Ember;
	}
	
    public static WeightedCollection<ItemType> weightedCollection = new WeightedCollection<ItemType>();
    public static boolean generated = false;

    public static ItemType random(){
        if (generated){
            return weightedCollection.next();
        } else {
            generate();
            return weightedCollection.next();
        }
    }

    private static void generate(){
        weightedCollection.add(0.25, ItemType.SWORD);
        weightedCollection.add(0.10, ItemType.BOW);
        weightedCollection.add(0.21, ItemType.AXE);
        weightedCollection.add(0.02, ItemType.SHIELD);
        weightedCollection.add(0.11, ItemType.SPADE);
        weightedCollection.add(0.13, ItemType.HELMET);
        weightedCollection.add(0.15, ItemType.CHESTPLATE);
        weightedCollection.add(0.19, ItemType.LEGGINGS);
        weightedCollection.add(0.08, ItemType.BOOTS);
        generated = true;
    }
    
    public static String getItemName(ItemType t, ItemTier tier){
    	switch(tier){
    	case T1:
    		return t.getT1Name();
    	case T2:
    		return t.getT2Name();
    	case T3:
    		return t.getT3Name();
    	case T4:
    		return t.getT4Name();
    	case T5:
    		return t.getT5Name();
    	case MYTHIC:
    		return t.getMythicName();
    	case LEGENDARY:
    		return t.getLegendaryName();
    	case EMBER:
    		return t.getEmberName();
    	}
    	return null;
    }
}
