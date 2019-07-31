package com.emberthorne.game.api.player.stamina;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.event.PlayerFinishLoadEvent;
import com.emberthorne.game.api.player.EmberPlayer;

public class PlayerStamina implements Listener{
	
	Main game;
	public PlayerStamina(Main instance){
		this.game = instance;
	}
	
	@EventHandler
	public void onStamina(PlayerFinishLoadEvent e){
		Player p = e.getPlayer();
		EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
		em.setStamina(em.getStamina());
	}
	
	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e){
		Player p = e.getPlayer();
		EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
		
		if(em.getStamina() < 0.20){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		Player p = (Player)e.getEntity();
		EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
		
		if(em.isTired()){
			e.setFoodLevel(1);
		}
	}
	
	// Player hitting any entity to fix a spigot bug -.-
	@EventHandler
	public void onDmg(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if(damager instanceof Player){
			Player p = (Player) damager;
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 1));
		}
	}
	
}
