package com.emberthorne.game.api.item.modifiers.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SoulboundListener {
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
    	event.setDeathMessage("");
    }
    
}
