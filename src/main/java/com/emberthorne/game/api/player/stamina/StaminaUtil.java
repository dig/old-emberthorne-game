package com.emberthorne.game.api.player.stamina;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.player.EmberPlayer;

public class StaminaUtil {
	
	int taskid;
	Main game;
	
	public StaminaUtil(Main instance){
		this.game = instance;
		
        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
        	for(EmberPlayer em : game.onlinePlayers.values()){
        		if(em.getPlayer().getGameMode() == GameMode.CREATIVE){
        			continue;
        		}
        		if(em.getPlayer().hasMetadata("tiredDelay")){
        			long delay = em.getPlayer().getMetadata("tiredDelay").get(0).asLong();
        			if(delay > System.currentTimeMillis()){
        				continue;
        			}
        			else{
        				em.getPlayer().removeMetadata("tiredDelay", Main.getInstance());
        			}
        		}
        		
        		if(em.getStamina() < 1f){
        			if(!em.getPlayer().isSprinting() && !em.inCombat() && !em.getPlayer().isBlocking()){
        				em.setStamina(em.getStamina()+em.getStaminaRegen());
        			}
        			else if(em.inCombat()){
        				em.setStamina(em.getStamina()+em.getStaminaRegenCombat());
        			}
        		}
        		
        		if(em.isTired()){
     			   if(em.getStamina() > 0.25f){
        				em.setTired(false);
        				em.getPlayer().setFoodLevel(em.getFoodLevel());
        				em.getPlayer().removePotionEffect(PotionEffectType.SLOW);
        			}
        		}
        		
        		if(em.getPlayer().isSprinting()){
        			em.setStamina(em.getStamina()-em.getStaminaRunDecrease());
        		}
        		
    			if(em.getStamina() <= 0.05){
    				em.getPlayer().setSprinting(false);
    				em.setFoodLevel(em.getPlayer().getFoodLevel());
    				
    				em.setTired(true);
    				em.getPlayer().setFoodLevel(1);
    				em.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 
    						Integer.MAX_VALUE, 1));
    				em.getPlayer().setMetadata("tiredDelay", new FixedMetadataValue(Main.getInstance(), 
    						System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(2)));
    				em.getPlayer().sendMessage(ChatColor.RED+"**TIRED**");
    			}
        	}
        }, 0L, 10L);
	}
	
	public void stopTask(){
		Bukkit.getScheduler().cancelTask(taskid);
	}
}
