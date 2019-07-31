package com.emberthorne.game.api.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.player.EmberPlayer;

public class PlayerListener implements Listener{
	
    Main game;
    public PlayerListener(Main instance){
        this.game = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
    	event.setDeathMessage("");
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDrop(PlayerDropItemEvent event) {
    	Player p = event.getPlayer();
    	EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
    	
    	if(!em.isCanDrop()){
    		event.setCancelled(true);
    	}
    }
}
