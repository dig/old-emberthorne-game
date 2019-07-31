package com.emberthorne.game.api.mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.emberthorne.game.api.mobs.type.MobBandit;
import com.emberthorne.game.api.mobs.type.MobGoblin;
import com.emberthorne.game.api.mobs.type.MobHare;

import lombok.Getter;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.World;

public enum CreatureType {
	
    GOBLIN("Goblin"),
	HARE("Hare"),
	BANDIT("Bandit");

    @Getter private String name;

    CreatureType(String name){
        this.name = name;
    }
    
    public Entity spawnExact(Location loc, MobTiers tier){
    	if(name.equalsIgnoreCase("goblin")){
    		World mcworld = (World)((CraftWorld)loc.getWorld()).getHandle();
    	    MobGoblin h = new MobGoblin(mcworld, "Goblin", tier);
    	    h.spawn(loc);
    	    return h;
    	}
    	else if(name.equalsIgnoreCase("hare")){
    		World mcworld = (World)((CraftWorld)loc.getWorld()).getHandle();
    	    MobHare h = new MobHare(mcworld, "Hare", tier);
    	    h.spawn(loc);
    	    return h;
    	}
    	else if(name.equalsIgnoreCase("bandit")){
    		World mcworld = (World)((CraftWorld)loc.getWorld()).getHandle();
    	    MobBandit h = new MobBandit(mcworld, "Bandit", tier);
    	    h.spawn(loc);
    	    return h;
    	}
    	return null;
    }
    
    public static CreatureType getMob(String s){
    	CreatureType result = null;
    	
    	for(CreatureType t : CreatureType.values()){
    		if(t.getName().equalsIgnoreCase(s)){
    			result = t;
    		}
    	}
    	
    	return result;
    }
}
