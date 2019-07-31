package com.emberthorne.game.api.listeners.damage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.item.nbt.NBTItem;
import com.emberthorne.game.api.mobs.MobTiers;
import com.emberthorne.game.api.mobs.data.NameUtil;
import com.emberthorne.game.api.player.EmberPlayer;

public class DamageListener implements Listener{
	
    Main game;
    public DamageListener(Main instance){
        this.game = instance;
    }
	
	// Entities hitting players
	@EventHandler
	public void onDmg(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		
		if(event.getEntity() instanceof Player && damager.hasMetadata("damage")){
			Player p = (Player) event.getEntity();
			EmberPlayer em = game.onlinePlayers.get(p.getUniqueId().toString());
			event.setDamage(0);
			
			if(damager.hasMetadata("lastAttack")){
				long last = damager.getMetadata("lastAttack").get(0).asLong();
				if(System.currentTimeMillis() <= last){
					event.setDamage(0);
					event.setCancelled(true);
					return;
				}
				else{
					int seconds = (int)damager.getMetadata("attackspeed").get(0).asDouble();
					int milli = (int)((damager.getMetadata("attackspeed").get(0).asDouble() - seconds) * 1000);
					damager.removeMetadata("lastAttack", game);
					damager.setMetadata("lastAttack", new FixedMetadataValue(Main.getInstance(), 
							System.currentTimeMillis()+milli));
				}
			}
			
			int damage = 2;
			if(damager.hasMetadata("damage")){
				damage = Integer.parseInt(damager.getMetadata("damage").get(0).asString());
			}
			em.checkArmor();
			em.startCombat();
			
			// Stamina
			if(p.isBlocking()){
				if(em.getStamina() >= em.getStaminaShieldDecrease()){ // Can block
					damage = 0;
			    	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**BLOCKED**"));
			    	em.setStamina(em.getStamina()-em.getStaminaShieldDecrease());
			    	return;
				}
				else{ // Not enough stamina
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**FAIL BLOCK**"));
				}
			}
			
			em.doDamage(damage);
			
	    	// Message
			if(em.isMobDamage()){
		    	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "   &c-"+damage+" &c&lHP &7|&a "
		    			+em.getHealth()+"&lHP"));
			}
		}
	}
	
	// Players vs Players
	@EventHandler
	public void onPVP(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		
		if(damager instanceof Player){
			if(entity instanceof Player){
				Player attacker = (Player)damager;
				Player defender = (Player)entity;
				event.setDamage(0);
				
				int minDmg = 1;
				int maxDmg = 1;
				if(attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR){
					NBTItem item = new NBTItem(attacker.getItemInHand());
					if(item.hasKey("minDmg") && item.hasKey("maxDmg")){
						if(attacker.getItemInHand().getType() != Material.BOW){
							minDmg = item.getInteger("minDmg");
							maxDmg = item.getInteger("maxDmg");
						}
					}
				}
				
				Random r = new Random();
				EmberPlayer def = game.onlinePlayers.get(defender.getUniqueId().toString());
				EmberPlayer att = game.onlinePlayers.get(attacker.getUniqueId().toString());
				def.checkArmor();
				att.checkArmor();
				
				def.startCombat();
				att.startCombat();
				
				int damage = r.nextInt(maxDmg - minDmg + 1) + minDmg;
				
				// Stamina
				if(defender.isBlocking()){
					if(def.getStamina() >= def.getStaminaShieldDecrease()){ // Can block
						damage = 0;
				    	defender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**BLOCKED**"));
				    	attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**DEFENDER BLOCKED**"));
				    	def.setStamina(def.getStamina()-def.getStaminaShieldDecrease());
				    	return;
					}
					else{ // Not enough stamina
						defender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**FAIL BLOCK**"));
					}
				}
				if(att.getStamina() < att.getStaminaHitDecrease()){ // Not enough stamina to hit
					damage = 0;
					attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 2*20, 3));
					attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**FAIL ATTACK**"));
					return;
				}
				else{
					att.setStamina(att.getStamina()-att.getStaminaHitDecrease());
				}
				
				def.doDamage(damage);
				
				// Send hurt message to defender
				if(def.isPlayerDamage()){
			    	defender.sendMessage(ChatColor.translateAlternateColorCodes('&', "   &c-"+damage+" &c&lHP &7|&a "
			    			+def.getHealth()+"&lHP"));
				}
				
				// Send damage given message to attacker
				if(att.isPlayerDamage()){
			    	attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "   &c"+damage+" &lDMG &c-> &f"+
					    	ChatColor.stripColor(defender.getName())
					    	+" &7|&c "+(int)def.getHealth()+"&lHP"));
				}
			}
		}
	}
	
	// Player hitting entity
	@EventHandler
	public void onPlayerHitEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		
		if(damager instanceof Player){
			if(!(entity instanceof Player)){
				if(entity.hasMetadata("EMCustom")){
					Player attacker = (Player)damager;
					LivingEntity le = (LivingEntity) entity;
					EmberPlayer em = game.onlinePlayers.get(attacker.getUniqueId().toString());
					
					int minDmg = 1;
					int maxDmg = 1;
					if(attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR){
						NBTItem item = new NBTItem(attacker.getItemInHand());
						if(item.hasKey("minDmg") && item.hasKey("maxDmg")){
							if(attacker.getItemInHand().getType() != Material.BOW){
								minDmg = item.getInteger("minDmg");
								maxDmg = item.getInteger("maxDmg");
							}
						}
					}
					
					Random r = new Random();
					int damage = r.nextInt(maxDmg - minDmg + 1) + minDmg;
					String name = entity.getMetadata("name").get(0).asString();
					
					int maxhealth = 0;
					int health = 0;
					if(entity.hasMetadata("health")){
						health = entity.getMetadata("health").get(0).asInt();
						maxhealth = entity.getMetadata("maxHealth").get(0).asInt();
					}
					else{
						if(entity.hasMetadata("maxHealth")){
							health = entity.getMetadata("maxHealth").get(0).asInt();
							maxhealth = entity.getMetadata("maxHealth").get(0).asInt();
						}
					}
					
					// Stamina
					if(em.getStamina() < em.getStaminaHitDecrease()){ // Not enough stamina to hit
						damage = 0;
						attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 2*20, 3));
						attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c**FAIL ATTACK**"));
						return;
					}
					else{
						em.setStamina(em.getStamina()-em.getStaminaHitDecrease());
					}
					
					// Damage
					event.setDamage(0);
					health = health - damage;
					
					entity.setCustomName(NameUtil.generateOverheadBar(entity, health, 
							maxhealth, MobTiers.valueOf(entity.getMetadata("tier").get(0).asString())));
					
					if(health <= 0){
						le.setHealth(0);
					}
					else{
						if(entity.hasMetadata("health")){
							entity.removeMetadata("health", Main.getInstance());
						}
						entity.setMetadata("health", new FixedMetadataValue(Main.getInstance(), health));
					}
					em.startCombat();
					
					// Blood Particles
					if(em.isBloodParticles()){
				    	attacker.playEffect(entity.getLocation().add(0.0D, 1.0D, 0.0D), 
				    	        Effect.STEP_SOUND, 152);
					}
			    	
			    	// Message
			    	if(em.isMobDamage()){
				    	attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "   &c"+damage+" &lDMG &c-> &f"+
						    	ChatColor.stripColor(name)
						    	+" &7|&c "+(int)health+"&lHP"));
			    	}
				}
			}
		}
	}
	
}
