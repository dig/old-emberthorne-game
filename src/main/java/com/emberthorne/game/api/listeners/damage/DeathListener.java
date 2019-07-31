package com.emberthorne.game.api.listeners.damage;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.mobs.MobTiers;
import com.emberthorne.game.api.player.EmberPlayer;

public class DeathListener implements Listener{
	
    Main game;
    public DeathListener(Main instance){
        this.game = instance;
    }
    
    // When a entity dies, not player, award player with xp
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
    	Entity en = e.getEntity();
    	
    	Player killer = e.getEntity().getKiller();
    	EmberPlayer em = game.onlinePlayers.get(killer.getUniqueId().toString());
    	
    	if(!(en instanceof Player)){
    		if(en.hasMetadata("EMCustom")){
    			MobTiers tier = MobTiers.valueOf(en.getMetadata("tier").get(0).asString());
    			
    			Random random = new Random();
    			int exp = random.nextInt((tier.getMaxExp() - tier.getMinExp()) + 1) + tier.getMinExp();
    			em.addExperience(exp);
    		}
    	}
    }

}
