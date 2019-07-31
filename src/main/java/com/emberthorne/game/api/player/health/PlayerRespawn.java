package com.emberthorne.game.api.player.health;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.player.EmberPlayer;

public class PlayerRespawn implements Listener{
	
	Main game;
	public PlayerRespawn(Main instance){
		this.game = instance;
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
		em.setHealth(em.getMaxHealth()+em.getArmor());
		em.setTired(false);
		em.resetCombat();
		
		em.updateBar();
	}

}
