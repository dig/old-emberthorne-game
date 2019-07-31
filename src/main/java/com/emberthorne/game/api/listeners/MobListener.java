package com.emberthorne.game.api.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobListener implements Listener{
	
	@EventHandler
	public void onMobCombust(EntityCombustEvent e){
		e.setCancelled(true);
	}
	
    @EventHandler
    public void onMobLoot(EntityDeathEvent e){
    	if(!(e.getEntity() instanceof Player)){
    		e.setDroppedExp(0);
    		e.getDrops().clear();
    	}
    }
}
