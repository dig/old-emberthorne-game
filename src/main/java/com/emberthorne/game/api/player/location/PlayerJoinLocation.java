package com.emberthorne.game.api.player.location;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.event.PlayerFinishLoadEvent;
import com.emberthorne.game.api.player.EmberPlayer;

public class PlayerJoinLocation implements Listener{
	
	Main game;
	public PlayerJoinLocation(Main instance){
		this.game = instance;
	}
	
	@EventHandler
	public void onHealthJoin(PlayerFinishLoadEvent e){
		Player p = e.getPlayer();
		EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
		p.teleport(em.getLoadedLocation());
	}
}
