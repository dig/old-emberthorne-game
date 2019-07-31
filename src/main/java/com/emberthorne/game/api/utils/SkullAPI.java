package com.emberthorne.game.api.utils;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;

public class SkullAPI {

    public ItemStack getSkull(String id, String value){
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		           
		net.minecraft.server.v1_10_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
		           
		NBTTagList textures = new NBTTagList();
		textures.add(new NBTTagCompound());
		textures.get(0).setString("Value", value);
		           
		NBTTagCompound properties = new NBTTagCompound();
		properties.set("textures", textures);
		           
		NBTTagCompound skullowner = new NBTTagCompound();
		skullowner.setString("Id", id);
		skullowner.set("Properties", properties);
		           
		tag.set("SkullOwner", skullowner);
		           
		stack.setTag(tag);
		item = CraftItemStack.asBukkitCopy(stack);
		return item;
    }
}
