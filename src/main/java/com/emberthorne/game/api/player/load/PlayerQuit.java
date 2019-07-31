package com.emberthorne.game.api.player.load;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.database.MongoUtils;
import com.emberthorne.game.api.player.EmberPlayer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class PlayerQuit implements Listener{
	
    Main game;
    public PlayerQuit(Main instance){
        this.game = instance;
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
    	Player p = event.getPlayer();
        event.setQuitMessage("");
        
        if(game.onlinePlayers.containsKey(event.getPlayer().getUniqueId().toString())){
        	EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
        	
        	DBCollection coll = game.database.getDB().getCollection("player_data");
            DBObject r = new BasicDBObject("uuid", event.getPlayer().getUniqueId().toString());
            DBObject found = coll.findOne(r);
            
            if(found != null){
            	r.putAll(found);
            	r = save(em,p,r);
            	coll.update(found,r);
            }
            else{
            	r = save(em,p,r);
            	coll.insert(r);
            }
            
            game.onlinePlayers.remove(p.getUniqueId().toString());
            System.out.println("Saved player: "+p.getName());
        }
        else{
        	System.out.println("Failed to save for player: "+event.getPlayer().getName()+". No local data?");
        }
    }
    
    public DBObject save(EmberPlayer em, Player p, DBObject r){
    	// Lists
    	BasicDBObject info = new BasicDBObject();
    	BasicDBObject location = new BasicDBObject();
    	BasicDBObject inventory = new BasicDBObject();
    	BasicDBObject toggles = new BasicDBObject();
    	
    	BasicDBObject armor = new BasicDBObject();
    	
    	// Checking for old data..
    	if(r.containsField("info")){
    		info = (BasicDBObject) r.get("info");
    	}
    	if(r.containsField("location")){
    		location = (BasicDBObject) r.get("location");
    	}
    	if(r.containsField("inventory")){
    		inventory = (BasicDBObject) r.get("inventory");
    	}
    	if(inventory.containsField("armor")){
    		armor = (BasicDBObject) inventory.get("armor");
    	}
    	if(r.containsField("toggles")){
    		toggles = (BasicDBObject) r.get("toggles");
    	}
    	
    	// General
    	info = (BasicDBObject) MongoUtils.updateField(info, "username", p.getName());
    	info = (BasicDBObject) MongoUtils.updateField(info, "rank", em.getRank().getId());
    	info = (BasicDBObject) MongoUtils.updateField(info, "health", em.getHealth());
    	info = (BasicDBObject) MongoUtils.updateField(info, "maxhealth", em.getMaxHealth());
    	info = (BasicDBObject) MongoUtils.updateField(info, "level", em.getLevel());
    	info = (BasicDBObject) MongoUtils.updateField(info, "exp", em.getExp());
    	info = (BasicDBObject) MongoUtils.updateField(info, "stamina", em.getStamina());
    	if(!info.containsField("joindate")){
            long unixTime = System.currentTimeMillis() / 1000L;
    		info = (BasicDBObject) MongoUtils.updateField(info, "joindate", unixTime);
    	}
    	
    	// Location
    	location = (BasicDBObject) MongoUtils.updateField(location, "world", p.getLocation().getWorld().getName());
    	location = (BasicDBObject) MongoUtils.updateField(location, "posX", p.getLocation().getX());
    	location = (BasicDBObject) MongoUtils.updateField(location, "posY", p.getLocation().getY());
    	location = (BasicDBObject) MongoUtils.updateField(location, "posZ", p.getLocation().getZ());
    	location = (BasicDBObject) MongoUtils.updateField(location, "yaw", p.getLocation().getYaw());
    	location = (BasicDBObject) MongoUtils.updateField(location, "pitch", p.getLocation().getPitch());
    	
    	// Inventory
    	inventory = (BasicDBObject) MongoUtils.updateField(inventory, "inv", InventoryUtil.toString(p.getInventory()));
    	inventory = (BasicDBObject) MongoUtils.updateField(inventory, "offhand", 
    			InventoryUtil.itemStackToBase64(p.getInventory().getItemInOffHand()));
    	
    	// Armor
    	armor = (BasicDBObject) MongoUtils.updateField(armor, "helm", 
    			InventoryUtil.itemStackToBase64(p.getInventory().getHelmet()));
    	armor = (BasicDBObject) MongoUtils.updateField(armor, "chest", 
    			InventoryUtil.itemStackToBase64(p.getInventory().getChestplate()));
    	armor = (BasicDBObject) MongoUtils.updateField(armor, "legs", 
    			InventoryUtil.itemStackToBase64(p.getInventory().getLeggings()));
    	armor = (BasicDBObject) MongoUtils.updateField(armor, "boots", 
    			InventoryUtil.itemStackToBase64(p.getInventory().getBoots()));
    	
    	// Toggles
    	toggles = (BasicDBObject) MongoUtils.updateField(toggles, "mobDamage", em.isMobDamage());
    	toggles = (BasicDBObject) MongoUtils.updateField(toggles, "playerDamage", em.isPlayerDamage());
    	toggles = (BasicDBObject) MongoUtils.updateField(toggles, "bloodParticles", em.isBloodParticles());
    	
    	// Setup
    	r.removeField("info");
    	r.put("info", info);
    	
    	r.removeField("location");
    	r.put("location", location);
    	
    	r.removeField("inventory");
    	r.put("inventory", inventory);
    	
    	inventory.removeField("armor");
    	inventory.put("armor", armor);
    	
    	return r;
    }
}
