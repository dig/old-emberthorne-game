package com.emberthorne.game.api.player.load;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.event.PlayerFinishLoadEvent;
import com.emberthorne.game.api.item.ItemGenerator;
import com.emberthorne.game.api.item.ItemRarities;
import com.emberthorne.game.api.item.ItemTier;
import com.emberthorne.game.api.item.ItemType;
import com.emberthorne.game.api.item.nbt.NBTItem;
import com.emberthorne.game.api.player.EmberPlayer;

public class PlayerInventoryLoad implements Listener{
	
	Main game;
	public PlayerInventoryLoad(Main instance){
		this.game = instance;
	}
	
	@EventHandler
	public void onInventoryJoin(PlayerFinishLoadEvent e){
		Player p = e.getPlayer();
		EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
		
		p.getInventory().clear();
		
		if(em.isStarterGear()){
			p.getInventory().setChestplate(ItemGenerator.createItem(ItemTier.T1, ItemType.CHESTPLATE, 
					ItemRarities.COMMON, ChatColor.GRAY+"Training Chestplate"));
			p.getInventory().setBoots(ItemGenerator.createItem(ItemTier.T1, ItemType.BOOTS, 
					ItemRarities.COMMON, ChatColor.GRAY+"Training Boots"));
			p.getInventory().addItem(ItemGenerator.createItem(ItemTier.T1, ItemType.SWORD, 
					ItemRarities.COMMON, ChatColor.GRAY+"Training Sword"));
			p.getInventory().addItem(new ItemStack(Material.BREAD, 16));
			return;
		}
		
		// Inventory
		if(em.getInventory() != null){
			ItemStack[] load = InventoryUtil.fromString(em.getInventory(), 36).getContents();
			
			// Check for special items incase they lost their glow
			for(int i = 0; i<load.length; i++){
				if(load[i] != null && load[i].getType() != Material.AIR){
					NBTItem item = new NBTItem(load[i]);
					if(item.hasKey("EMItem") && item.hasKey("tier")){
						ItemTier tier = ItemTier.valueOf(item.getString("tier"));
						if(tier.isSpecial()){
							load[i] = ItemGenerator.addGlow(load[i]);
						}
					}
				}
			}
			
			if(load != null && load.length > 0){
				p.getInventory().setContents(load);
				p.updateInventory();
			}
		}
		
		// Off hand
		if(em.getOffhand() != null){
			ItemStack off = InventoryUtil.itemStackFromBase64(em.getOffhand());
			
			// Check for special item glow
			if(off != null && off.getType() != Material.AIR){
				NBTItem item = new NBTItem(off);
				if(item.hasKey("EMItem") && item.hasKey("tier")){
					ItemTier tier = ItemTier.valueOf(item.getString("tier"));
					if(tier.isSpecial()){
						off = ItemGenerator.addGlow(off);
					}
				}
			}
			
			p.getInventory().setItemInOffHand(off);
		}
		
		// Armor
		if(em.getArmorContents() != null && em.getArmorContents().size() > 0){
			for(String arm : em.getArmorContents()){
				String[] parts = arm.split(";");
				ItemStack i = InventoryUtil.itemStackFromBase64(parts[1]);
				
				// Check for special item glow
				if(i != null && i.getType() != Material.AIR){
					NBTItem item = new NBTItem(i);
					if(item.hasKey("EMItem") && item.hasKey("tier")){
						ItemTier tier = ItemTier.valueOf(item.getString("tier"));
						if(tier.isSpecial()){
							i = ItemGenerator.addGlow(i);
						}
					}
				}
				
				if(parts[0].equalsIgnoreCase("helm")){
					p.getInventory().setHelmet(i);
				}
				else if(parts[0].equalsIgnoreCase("chest")){
					p.getInventory().setChestplate(i);
				}
				else if(parts[0].equalsIgnoreCase("legs")){
					p.getInventory().setLeggings(i);
				}
				else if(parts[0].equalsIgnoreCase("boots")){
					p.getInventory().setBoots(i);
				}
			}
		}
		
		// Update armor
		em.checkArmor();
	}
}
