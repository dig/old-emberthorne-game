package com.emberthorne.game.api.player.load;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.event.PlayerFinishLoadEvent;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.api.utils.CenterMsg;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class PlayerJoin implements Listener{
	
    Main game;
    public PlayerJoin(Main instance){
        this.game = instance;
    }

    // load player data here
    @EventHandler
    public void beforeJoin(AsyncPlayerPreLoginEvent e){
    	
    	if(!game.isConnected){
    		e.setKickMessage(ChatColor.RED+"Server failed to connect to the database!");
    		e.disallow(Result.KICK_OTHER, ChatColor.RED+"Server failed to connect to the database!");
    		return;
    	}
    	
    	DBCollection coll = game.database.getDB().getCollection("player_data");
        DBObject r = new BasicDBObject("uuid", e.getUniqueId().toString());
        DBObject found = coll.findOne(r);
        
        if(found != null){
        	game.onlinePlayers.put(e.getUniqueId().toString(), load(e.getUniqueId().toString(), found));
        }
        else{
        	EmberPlayer pnew = new EmberPlayer(e.getUniqueId().toString());
        	game.onlinePlayers.put(e.getUniqueId().toString(), pnew);
        }
    }
    
    
    // check if data is loaded, if so call custom join event
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
    	e.setJoinMessage("");
    	
    	if(game.onlinePlayers.containsKey(e.getPlayer().getUniqueId().toString())){
    		game.getServer().getPluginManager().callEvent(new PlayerFinishLoadEvent(e.getPlayer()));
    	}
    	else{
    		e.getPlayer().kickPlayer(ChatColor.RED+"Failed to load your player data, try again soon!");
    	}
    	
    	this.sendJoinMessage(e.getPlayer());
    }
    
    public EmberPlayer load(String uuid, DBObject found){
    	BasicDBObject info = (BasicDBObject) found.get("info");
    	BasicDBObject location = (BasicDBObject) found.get("location");
    	BasicDBObject inventory = (BasicDBObject) found.get("inventory");
    	BasicDBObject toggles = (BasicDBObject) found.get("toggles");
    	
    	EmberPlayer load = new EmberPlayer(uuid);
    	
    	// Info
    	if(info != null){
        	if(info.containsField("rank")){
        		load.setRank(EmberRank.getRankById(info.getInt("rank")));
        	}
        	
        	if(info.containsField("health")){
        		load.setHealth((int)info.get("health"));
        	}
        	
        	if(info.containsField("maxhealth")){
        		load.setMaxHealth((int)info.get("maxhealth"));
        	}
        	
        	if(info.containsField("level")){
        		load.setLevel((int)info.get("level"));
        	}
        	
        	if(info.containsField("exp")){
        		load.setExp((int)info.get("exp"));
        	}
        	
        	if(info.containsField("stamina")){
        		load.setLoadStamina((float)info.getDouble("stamina"));
        	}
    	}
    	
    	// Location
    	if(location != null){
        	if(location.containsField("posX")){
        		String w = (String)location.get("world");
        		
        		double x = Double.parseDouble(location.get("posX")+"");
        		double y = Double.parseDouble(location.get("posY")+"");
        		double z = Double.parseDouble(location.get("posZ")+"");
        		
        		double yaw = Double.parseDouble(location.get("yaw")+"");
        		double pitch = Double.parseDouble(location.get("pitch")+"");
        		
        		load.setX(x);
        		load.setY(y);
        		load.setZ(z);
        		load.setYaw(Float.parseFloat(yaw+""));
        		load.setPitch(Float.parseFloat(pitch+""));
        		load.setWorld(w);
        	}
    	}
    	
    	// Inventory
    	if(inventory != null){
    		load.setStarterGear(false);
        	if(inventory.containsField("inv")){
        		load.setInventory((String)inventory.get("inv"));
        	}
        	
        	// Off hand
        	if(inventory.containsField("offhand")){
        		load.setOffhand((String)inventory.get("offhand"));
        	}
        	
        	// Armor
        	BasicDBObject armor = (BasicDBObject) inventory.get("armor");
        	if(armor != null){
        		for(String s : armor.keySet()){
        			// type;itemstring e.g helm;dsaidj82234
        			load.getArmorContents().add(s+";"+(String)armor.getString(s));
        		}
        	}
    	}
    	else{
    		load.setStarterGear(true);
    	}
    	
    	// Toggles
    	if(toggles != null){
    		if(toggles.containsField("mobDamage")){
    			load.setMobDamage(toggles.getBoolean("mobDamage"));
    		}
    		if(toggles.containsField("playerDamage")){
    			load.setPlayerDamage(toggles.getBoolean("playerDamage"));
    		}
    		if(toggles.containsField("bloodParticles")){
    			load.setBloodParticles(toggles.getBoolean("bloodParticles"));
    		}
    	}
    	
    	return load;
    }
    
    public void sendJoinMessage(Player p){
    	p.sendMessage(" ");
    	CenterMsg.sendCenteredMessage(p, "&6&lEmberThorne MMO");
    	CenterMsg.sendCenteredMessage(p, "&7http://emberthorne.com");
    	CenterMsg.sendCenteredMessage(p, "&7Patch "+Main.getInstance().patch);
    	p.sendMessage(" ");
    }
}
