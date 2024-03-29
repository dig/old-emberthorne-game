package com.emberthorne.game.api.item.nbt;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private ItemStack bukkitItem;
    
    public NBTItem(ItemStack item) {
        bukkitItem = item.clone();
    }

    public ItemStack getItem() {
        return bukkitItem;
    }
    
    // So we can add lores then update the item :) -Digi
    public void updateItem(ItemStack item){
    	this.bukkitItem = item;
    }

    public void setString(String key, String value) {
        bukkitItem = NBTReflectionUtil.setString(bukkitItem, key, value);
    }

    public String getString(String key) {
        return NBTReflectionUtil.getString(bukkitItem, key);
    }

    public void setInteger(String key, int value) {
        bukkitItem = NBTReflectionUtil.setInt(bukkitItem, key, value);
    }

    public Integer getInteger(String key) {
        return NBTReflectionUtil.getInt(bukkitItem, key);
    }

    public void setDouble(String key, double value) {
        bukkitItem = NBTReflectionUtil.setDouble(bukkitItem, key, value);
    }

    public double getDouble(String key) {
        return NBTReflectionUtil.getDouble(bukkitItem, key);
    }

    public void setBoolean(String key, boolean value) {
        bukkitItem = NBTReflectionUtil.setBoolean(bukkitItem, key, value);
    }

    public boolean getBoolean(String key) {
        return NBTReflectionUtil.getBoolean(bukkitItem, key);
    }

    public boolean hasKey(String key) {
        return NBTReflectionUtil.hasKey(bukkitItem, key);
    }
    
	public static ItemStack addStringTags(ItemStack item, HashMap<String, String> tags){
		ItemStack result = item;
		NBTItem e = new NBTItem(result);
		for(String s : tags.keySet()){
			e.setString(s, tags.get(s));
		}
		return e.getItem();
	}
	
	public static ItemStack addIntegerTags(ItemStack item, HashMap<String, Integer> tags){
		ItemStack result = item;
		NBTItem e = new NBTItem(result);
		for(String s : tags.keySet()){
			e.setInteger(s, tags.get(s));
		}
		return e.getItem();
	}

}