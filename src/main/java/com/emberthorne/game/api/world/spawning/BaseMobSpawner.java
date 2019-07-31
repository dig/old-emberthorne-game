package com.emberthorne.game.api.world.spawning;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.FixedMetadataValue;

import com.emberthorne.game.api.mobs.CreatureType;
import com.emberthorne.game.api.mobs.MobTiers;
import com.emberthorne.game.api.utils.GameUtil;
import com.emberthorne.game.Main;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.World;

public class BaseMobSpawner implements EmberSpawner{
	
    @Getter @Setter private Location loc;
    @Getter @Setter private EntityArmorStand armorstand;
    @Getter @Setter private CreatureType type;
    @Getter @Setter private MobTiers tier;
    @Getter @Setter private List<Entity> SPAWNED_MONSTERS = new CopyOnWriteArrayList<>();
    @Getter @Setter private Map<Entity, Integer> RESPAWN_TIMES = new ConcurrentHashMap<>();
    @Getter @Setter private int spawnAmount;
    @Getter @Setter private int timerID = -1;
    @Getter @Setter private boolean firstSpawn = true;
    @Getter @Setter private int respawnDelay;
    @Getter @Setter private int counter;
    @Getter @Setter private int mininmumXZ;
    @Getter @Setter private int maximumXZ;
    
    @Getter @Setter private boolean removed;
    
    public BaseMobSpawner(Location location, CreatureType type, MobTiers tier, int spawnAmount, int respawnDelay, int mininmumXZ, int maximumXZ) {
        this.spawnAmount = spawnAmount;
        this.loc = location;
        this.type = type;
        this.tier = tier;
        this.respawnDelay = respawnDelay;
        this.counter = 0;
        this.mininmumXZ = mininmumXZ;
        this.maximumXZ = maximumXZ;
        
        World world = ((CraftWorld) location.getWorld()).getHandle();
        armorstand = new EntityArmorStand(world);
        armorstand.setInvisible(true);
        
        armorstand.getBukkitEntity().setMetadata("type", new FixedMetadataValue(Main.getInstance(), "spawner"));
        armorstand.getBukkitEntity().setMetadata("tier", new FixedMetadataValue(Main.getInstance(), tier.toString()));
        armorstand.getBukkitEntity().setMetadata("mobtype", new FixedMetadataValue(Main.getInstance(), type.toString()));
        
        armorstand.setPosition(loc.getX(), loc.getY(), loc.getZ());
        world.addEntity(armorstand, SpawnReason.CUSTOM);
        armorstand.setPosition(loc.getX(), loc.getY(), loc.getZ());
    }
    
    private Location getRandomLocation(Location location, double xMin, double xMax, double zMin, double zMax) {
        org.bukkit.World world = location.getWorld();
        double randomX;
        double randomZ;
        double x;
        double y;
        double z;
        randomX = xMin + (int) (Math.random() * (xMax - xMin + 1));
        randomZ = zMin + (int) (Math.random() * (zMax - zMin + 1));
        x = randomX;
        y = location.getY() + 1;
        z = randomZ;
        x = x + 0.5;
        z = z + 0.5;
        y = y + 2.0;
        return new Location(world, x, y, z);
    }
    
    private boolean canMobSpawn() {
        if (!RESPAWN_TIMES.isEmpty()) {
            for (Map.Entry<Entity, Integer> entry : RESPAWN_TIMES.entrySet()) {
                int respawnTime = entry.getValue();
                Entity entity = entry.getKey();
                if (respawnTime > 0) {
                    respawnTime--;
                    RESPAWN_TIMES.put(entity, respawnTime);
                } else {
                    RESPAWN_TIMES.remove(entity);
                    return true;
                }
            }
        } else {
            if (counter >= respawnDelay) {
                return true;
            } else {
                counter++;
                return false;
            }
        }
        return false;
    }
    
    public List<Entity> getSpawnedMonsters() {
        return SPAWNED_MONSTERS;
    }
    
    private void next(){
		if (SPAWNED_MONSTERS.size() < spawnAmount) {
            if (!firstSpawn) {
                if (!canMobSpawn()) {
                    return;
                }
            }
            else{
            	RESPAWN_TIMES.clear();
            }
            
            Location location = getRandomLocation(loc, ((loc.getX() - mininmumXZ) - maximumXZ), ((loc.getX() + mininmumXZ) + maximumXZ),
                    ((loc.getZ() - mininmumXZ) - maximumXZ), ((loc.getZ() + mininmumXZ) + maximumXZ));
            
            if (location.getBlock().getType() != Material.AIR) {
                if (location.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                    location.add(0, 1, 0);
                } else if (location.clone().add(0, 2, 0).getBlock().getType() == Material.AIR) {
                    location.add(0, 2, 0);
                } else {
                    counter = respawnDelay;
                    return;
                }
            }
            
            World world = armorstand.getWorld();
            Location loc = new Location(Bukkit.getWorld(world.getWorld().getName()), location.getX(), location.getY(), location.getZ(), 1, 1);
            
            Entity e = type.spawnExact(loc, this.tier);
            SPAWNED_MONSTERS.add(e);
		}
    }

	@Override
	public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            boolean playersNearby = GameUtil.arePlayersNearby(loc, 32);
            if (playersNearby) {
                if (timerID == -1) {
                    timerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                        if (removed) {
                            Bukkit.getScheduler().cancelTask(timerID);
                        } else
                            next();
                    }, 0L, 35L);
                }
            } else {
                if (timerID != -1) {
                    Bukkit.getScheduler().cancelTask(timerID);
                    firstSpawn = true;
                    timerID = -1;
                }
            }
            
            for(Entity e : SPAWNED_MONSTERS){
            	if(e.dead){
            		SPAWNED_MONSTERS.remove(e);
            	}
            	else{
                    double distance = armorstand.getBukkitEntity().getLocation().distance(e.getBukkitEntity().getLocation());
                    if (distance > 30) {
                        Location location = getRandomLocation(loc, ((loc.getX() - mininmumXZ) - maximumXZ), ((loc.getX() + mininmumXZ) + maximumXZ),
                                ((loc.getZ() - mininmumXZ) - maximumXZ), ((loc.getZ() + mininmumXZ) + maximumXZ));
                    	e.getBukkitEntity().teleport(location);
                    	
                    	if(e.getBukkitEntity().hasMetadata("health")){
                            e.getBukkitEntity().removeMetadata("health", Main.getInstance());
                            e.getBukkitEntity().setCustomName(
                            		ChatColor.translateAlternateColorCodes('&', e.getBukkitEntity().getMetadata("name").get(0).asString()));
                    	}
            		}
            	}
            }
        }, 0L, 40L);
	}

	@Override
	public void destroy() {
        if (SPAWNED_MONSTERS.size() > 0)
            for (Entity spawnedMonster : SPAWNED_MONSTERS) {
                spawnedMonster.getBukkitEntity().remove();
                spawnedMonster.dead = true;
                armorstand.getWorld().kill(spawnedMonster);
            }
        firstSpawn = true;
        SPAWNED_MONSTERS.clear();
        
        armorstand.getWorld().removeEntity(armorstand);
        armorstand.getBukkitEntity().remove();
        removed = true;
	}
}
