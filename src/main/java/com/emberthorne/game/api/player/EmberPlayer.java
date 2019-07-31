package com.emberthorne.game.api.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.emberthorne.game.api.item.nbt.NBTItem;
import com.emberthorne.game.api.player.leveling.LevelAPI;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.api.utils.CenterMsg;

import lombok.Getter;
import lombok.Setter;

public class EmberPlayer {
	
	// General
    private String uniqueId;
    @Getter @Setter private EmberRank rank;
    @Getter @Setter private BossBar bar;
    
    // Health
    // MAXHEALTH = MAXHEALTH + ARMOR
    private int maxHealth;
    private int health;
    
    // Stamina
    private float stamina;
    @Getter @Setter private int foodLevel;
    @Getter @Setter private float staminaRegen;
    @Getter @Setter private float staminaRegenCombat;
    @Getter @Setter private float staminaRunDecrease;
    @Getter @Setter private float staminaHitDecrease;
    @Getter @Setter private float staminaShieldDecrease;
    
    // Armor
    @Setter private int helm;
    @Setter private int chest;
    @Setter private int legs;
    @Setter private int boots;
    
    // Inventory
    @Getter @Setter private boolean starterGear;
    @Getter @Setter private String inventory;
    @Getter @Setter private String offhand;
    @Getter @Setter private List<String> armorContents;
    
    // Toggles
    @Getter @Setter private boolean mobDamage;
    @Getter @Setter private boolean playerDamage;
    @Getter @Setter private boolean bloodParticles;
    
    // Game Toggles
    @Getter @Setter private boolean canDrop;
    @Getter @Setter private boolean canTakeDamage;
    @Getter @Setter private boolean tired;
    
    // Combat
    private long combatTime;
    private boolean inCombat;
    
    // Experience
    private int exp;
    private int level;
    
    // Location
    @Getter @Setter private String world;
    @Getter @Setter private double x;
    @Getter @Setter private double y;
    @Getter @Setter private double z;
    @Getter @Setter private float yaw;
    @Getter @Setter private float pitch;

    public EmberPlayer(String uuid){
    	this.bar = Bukkit.createBossBar("Loading your player data...", BarColor.GREEN, BarStyle.SOLID);
    			
    	// General
    	this.uniqueId = uuid;
    	this.rank = EmberRank.PLAYER;
    	this.exp = 0;
    	this.level = 1;
    	this.maxHealth = 25;
    	this.health = 29;
    	this.stamina = 1f;
    	
    	// Spawn point
    	this.world = "world";
    	this.x = 0;
    	this.y = 90;
    	this.z = 0;
    	
    	// Toggles
    	this.mobDamage = true;
    	this.playerDamage = true;
    	this.bloodParticles = true;
    	
    	// Game Toggles
    	this.canDrop = true;
    	this.canTakeDamage = true;
    	this.inCombat = true;
    	
    	// Stamina
    	this.staminaRegenCombat = 0.03f;
    	this.staminaRegen = 0.22f;
    	this.staminaHitDecrease = 0.17f;
    	this.staminaRunDecrease = 0.10f;
    	this.staminaShieldDecrease = 0.75f;
    	
    	// Inventory
    	this.armorContents = new ArrayList<String>();
    	this.starterGear = true;
    }
    
    public void setMaxHealth(int to){
        this.maxHealth = to;
    }

    public void addMaxHealth(int amount){
        this.maxHealth += amount;
    }

    public void removeMaxHealth(int amount){
        this.maxHealth -= amount;
    }

    public void setHealth(int to){
        this.health = to;
    }

    public void addHealth(int amount){
        this.health += amount;
    }

    public void removeHealth(int amount){
        this.health -= amount;
    }
    
    public int addHeart(){
    	int maxHealth = this.maxHealth + this.getArmor();
    	double result = maxHealth/10;
    	
    	if(this.health < maxHealth){ // Check if the players health needs regenning
    		if((this.health+result) < maxHealth){ // Check if health+new heart is greater than maxHp
    			this.health = this.health+(int)result;
    		}
    		else{
    			this.health = maxHealth;
    		}
    	}
    	this.updateBar();
    	return (int)result;
    }

    public void updateHealth(){
        getPlayer().setMaxHealth(this.maxHealth + this.getArmor());
        getPlayer().setHealth(this.health);
        getPlayer().setHealthScale(20);
    }
    
    public void doDamage(int amount){
    	if((this.health-amount) <= 0){
    		this.health = 0;
    		getPlayer().setHealth(0);
    		this.updateBar();
    		return;
    	}
    	
    	if(bloodParticles){
        	getPlayer().playEffect(getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), 
        	        Effect.STEP_SOUND, 152);
    	}
    	this.removeHealth(amount);
    	this.updateBar();
    }
    
	public void checkArmor(){
		if(getPlayer().getInventory().getHelmet() != null){
			NBTItem item = new NBTItem(getPlayer().getInventory().getHelmet());
			if(item.hasKey("health")){
				int hp = item.getInteger("health");
				this.setHelm(hp);
			}
			else{
				this.setHelm(0);
			}
		}
		else{
			this.setHelm(0);
		}
		
		if(getPlayer().getInventory().getChestplate() != null){
			NBTItem item = new NBTItem(getPlayer().getInventory().getChestplate());
			if(item.hasKey("health")){
				int hp = item.getInteger("health");
				this.setChest(hp);
			}
			else{
				this.setChest(0);
			}
		}
		else{
			this.setChest(0);
		}
		
		if(getPlayer().getInventory().getLeggings() != null){
			NBTItem item = new NBTItem(getPlayer().getInventory().getLeggings());
			if(item.hasKey("health")){
				int hp = item.getInteger("health");
				this.setLegs(hp);
			}
			else{
				this.setLegs(0);
			}
		}
		else{
			this.setLegs(0);
		}
		
		if(getPlayer().getInventory().getBoots() != null){
			NBTItem item = new NBTItem(getPlayer().getInventory().getBoots());
			if(item.hasKey("health")){
				int hp = item.getInteger("health");
				this.setBoots(hp);
			}
			else{
				this.setBoots(0);
			}
		}
		else{
			this.setBoots(0);
		}
		
    	if(this.getHealth() > this.getMaxHealth()+this.getArmor()){
    		this.setHealth(this.getMaxHealth()+this.getArmor());
    	}
		
		this.updateBar();
	}
	
	public void setStamina(float to){
		if(to > 0){
			this.stamina = to;
			getPlayer().setExp(to);	
		}
		else{
			this.stamina = 0;
			getPlayer().setExp(0);
		}
	}
	
	public void setLoadStamina(float to){
		this.stamina = to;
	}
	
	public float getStamina(){
		return this.stamina;
	}
    
    public void addExp(int amount){
        this.exp += amount;
    }
    
    public void addLevel(int amount){
        this.level += amount;
        this.exp = 0;
        int next = LevelAPI.generateLevelExp(this.level);
        
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        getPlayer().sendMessage(" ");
        CenterMsg.sendCenteredMessage(getPlayer(), 
        		ChatColor.translateAlternateColorCodes('&', ChatColor.LIGHT_PURPLE+"================================"));
        getPlayer().sendMessage(" ");
        CenterMsg.sendCenteredMessage(getPlayer(), 
        		ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN+""+ChatColor.BOLD+"NEW LEVEL UP!"));
        getPlayer().sendMessage(" ");
        CenterMsg.sendCenteredMessage(getPlayer(), 
        		ChatColor.translateAlternateColorCodes('&', ChatColor.GRAY+"Level: "+ChatColor.GREEN+this.level));
        CenterMsg.sendCenteredMessage(getPlayer(), 
        		ChatColor.translateAlternateColorCodes('&', ChatColor.GRAY+"EXP needed for next level: "+ChatColor.GREEN+next));
        getPlayer().sendMessage(" ");
        CenterMsg.sendCenteredMessage(getPlayer(), 
        		ChatColor.translateAlternateColorCodes('&', ChatColor.LIGHT_PURPLE+"================================"));
    }
    
    public void setLevel(int amount){
    	this.level = amount;
    }
    
    public void setExp(int amount){
    	this.exp = amount;
    }

    public void removeExp(int amount){
        this.exp -= amount;
        if(exp < 0){
            exp = 0;
        }
    }
    
    public Player getPlayer(){
        return Bukkit.getPlayer(UUID.fromString(uniqueId));
    }
    
    public String getStringId(){
    	return uniqueId;
    }
    
    public UUID getUniqueId(){
    	return UUID.fromString(uniqueId);
    }
    
    public int getArmor(){
        return helm+chest+legs+boots;
    }
    
    public int getExp(){
        return exp;
    }
    
    public int getHealth(){
        return health;
    }

    public int getLevel(){
        return level;
    }

    public int getMaxHealth(){
        return maxHealth;
    }
    
    public Location getLoadedLocation(){
    	Location l = new Location(Bukkit.getWorld(world),x,y,z);
    	l.setPitch(pitch);
    	l.setYaw(yaw);
    	
    	return l;
    }
    
    public int getExpPercentage(){
    	int next = LevelAPI.generateLevelExp(this.level);
    	double result = ((double)this.exp/(double)next) *100;
    	return (int)result;
    }
    
    public void addExperience(int amount){
    	int next = LevelAPI.generateLevelExp(this.level);
    	
    	if(this.exp+amount >= next){ // Level Up!
    		getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "      &"
    				+ "6&l+"+amount+" EXP &7|&c &7["+(next)+"/"+(next)+" EXP]"));
    		this.addLevel(1);
    	}
    	else{ // Add to exp
    		getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "      &"
    				+ "6&l+"+amount+" EXP &7|&c &7["+(this.exp+amount)+"/"+(next)+" EXP]"));
    		this.setExp(this.exp+amount);
    	}
    	this.updateBar();
    }
    
    public boolean inCombat(){
    	if(this.inCombat){
    		if(this.combatTime < System.currentTimeMillis()){
    			this.inCombat = false;
    			return false;
    		}
    		else{
    			return true;
    		}
    	}
    	else{
    		return false;
    	}
    }
    
    public void startCombat(){
    	this.inCombat = true;
    	this.combatTime = System.currentTimeMillis() 
    			+ TimeUnit.SECONDS.toMillis(9);
    }
    
    public void resetCombat(){
    	this.inCombat = false;
    }
    
    public void updateBar(){
    	if(!this.bar.getPlayers().contains(getPlayer())){
    		this.bar.addPlayer(getPlayer());
    	}
    	
    	int maxhp = this.maxHealth+this.getArmor();
    	this.bar.setTitle(ChatColor.translateAlternateColorCodes('&', 
    			"&a&lLVL &f"+this.level+" &8- &c&lHP &f"+this.health+"&7/&f"+maxhp+" &8- &6&lXP &f"+getExpPercentage()+"%"));
    }
}
