package com.emberthorne.game.api.world.spawning;

import java.util.ArrayList;

import org.bukkit.Location;

import com.emberthorne.game.api.mobs.CreatureType;
import com.emberthorne.game.api.mobs.MobTiers;

public class SpawnerHandler {
	
	private static ArrayList<BaseMobSpawner> ALLSPAWNERS = new ArrayList<>();
	
    public static void stopAll() {
        ALLSPAWNERS.forEach(mobSpawner -> {
            mobSpawner.destroy();
            mobSpawner.getArmorstand().getBukkitEntity().remove();
            mobSpawner.getArmorstand().getWorld().removeEntity(mobSpawner.getArmorstand());
        });
    }
    
    public static void createSpawner(Location loc, CreatureType t, MobTiers tier, int maxspawn, int respawn, int minXZ, int maxXZ){
		BaseMobSpawner spawner = new BaseMobSpawner(loc, t, tier, maxspawn, respawn, minXZ, maxXZ);
		spawner.start();
    }
}
