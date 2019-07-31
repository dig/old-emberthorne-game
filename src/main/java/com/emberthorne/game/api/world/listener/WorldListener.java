package com.emberthorne.game.api.world.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.mobs.CreatureType;
import com.emberthorne.game.api.player.rank.EmberRank;

public class WorldListener implements Listener{
	
	public final List<String> blockedCommands = Arrays.asList("/pl", "/bukkit:", "/ver", "/icanhasbukkit", "/bukkit", "/?");
	
    Main game;
    public WorldListener(Main instance){
        this.game = instance;
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAchievementAward(PlayerAchievementAwardedEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeashEvent(PlayerLeashEntityEvent event){
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(e.getSpawnReason() == SpawnReason.CUSTOM){
			e.setCancelled(false);
		}
		else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChange(EntityChangeBlockEvent e){
		e.setCancelled(true);
	}
	
    @EventHandler
    public void onCommandPerform(PlayerCommandPreprocessEvent event){
        for (String string : blockedCommands){
            if (event.getMessage().toLowerCase().startsWith(string)){
                if(EmberRank.hasPerms(event.getPlayer(), 
                		game.onlinePlayers.get(event.getPlayer().getUniqueId().toString()).getRank(), 
                		EmberRank.DEVELOPER)){
                    return;
                } 
                else{
                    event.setCancelled(true);
                }
            }
        }
    }
}
