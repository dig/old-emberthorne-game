package com.emberthorne.game.api.mobs.type;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.mobs.MobTiers;
import com.emberthorne.game.api.mobs.data.EquipEntity;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntityRabbit;
import net.minecraft.server.v1_10_R1.GenericAttributes;
import net.minecraft.server.v1_10_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_10_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_10_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_10_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_10_R1.World;

public class MobHare extends EntityRabbit implements EquipEntity{
	
    private String name;
    private MobTiers tier;
    private int maxHealth;
    private int level;
    private int damage;
    private double attackspeed;
    
    private final List<String> tags = Arrays.asList("Blind", "Undead", "Vile", "Angry", "Quick");
    
    private final List<Material> hand = Arrays.asList(Material.AIR);
    private final List<Material> secondhand = Arrays.asList(Material.AIR);
   
    private final List<Material> helmet = Arrays.asList(Material.AIR);
    private final List<Material> chestplate = Arrays.asList(Material.AIR);
    private final List<Material> leggings = Arrays.asList(Material.AIR);
    private final List<Material> boots = Arrays.asList(Material.AIR);
    
    public MobHare(World world, String name, MobTiers tier){
        super(world);
        Random random = new Random();
       
        this.tier = tier;
        level = random.nextInt((tier.getMaxLevel() - tier.getMinLevel()) + 1) + tier.getMinLevel();
        damage = random.nextInt((tier.getMaxDamage() - tier.getMinDamage()) + 1) + tier.getMinDamage();
        attackspeed = random.nextDouble()*(tier.getMaxAttackSpeed()-tier.getMinAttackSpeed()) + tier.getMinAttackSpeed();
        this.name = "&d[Lvl "+level+"] &f"+tags.get(new Random().nextInt(tags.size()))+" "+name;
        
        this.maxHealth = random.nextInt((tier.getMaxHealth() - tier.getMinHealth()) + 1) + tier.getMinHealth();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(maxHealth);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(20d);
        this.getAttributeInstance(GenericAttributes.c).setValue(1.00d);
        this.setHealth(getMaxHealth());
        this.noDamageTicks = 0;
        this.maxNoDamageTicks = 0;
    }

    @Override
    public void r(){
        try{
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);

            bField.set(goalSelector, new LinkedHashSet<PathfinderGoalSelector>());
            bField.set(targetSelector, new LinkedHashSet<PathfinderGoalSelector>());
            cField.set(goalSelector, new LinkedHashSet<PathfinderGoalSelector>());
            cField.set(targetSelector, new LinkedHashSet<PathfinderGoalSelector>());
        } catch (Exception e){
            e.printStackTrace();
        }
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 5.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, false, true));
    }

    @Override
    public void spawn(Location location){
        World craftWorld = ((CraftWorld) location.getWorld()).getHandle();
        this.setLocation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
        craftWorld.addEntity(this, SpawnReason.CUSTOM);
        generate();
    }
    
    @Override
    public void generate(){
        this.getBukkitEntity().setCustomName(ChatColor.translateAlternateColorCodes('&', this.name));
        this.getBukkitEntity().setCustomNameVisible(true);
        
        this.getBukkitEntity().setMetadata("EMCustom", new FixedMetadataValue(Main.getInstance(), "custom"));
        this.getBukkitEntity().setMetadata("name", new FixedMetadataValue(Main.getInstance(), this.name));
        this.getBukkitEntity().setMetadata("level", new FixedMetadataValue(Main.getInstance(), this.level));
        this.getBukkitEntity().setMetadata("maxHealth", new FixedMetadataValue(Main.getInstance(), maxHealth));
        this.getBukkitEntity().setMetadata("tier", new FixedMetadataValue(Main.getInstance(), this.tier.toString()));
        this.getBukkitEntity().setMetadata("damage", new FixedMetadataValue(Main.getInstance(), this.damage));
        this.getBukkitEntity().setMetadata("attackspeed", new FixedMetadataValue(Main.getInstance(), this.attackspeed));
        this.getBukkitEntity().setMetadata("lastAttack", new FixedMetadataValue(Main.getInstance(), (long)0));
        
        // Add armor here
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setHelmet(new ItemStack(
        		helmet.get(new Random().nextInt(helmet.size()))));
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setChestplate(new ItemStack(
        		chestplate.get(new Random().nextInt(chestplate.size()))));
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setLeggings(new ItemStack(
        		leggings.get(new Random().nextInt(leggings.size()))));
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setBoots(new ItemStack(
        		boots.get(new Random().nextInt(boots.size()))));
        
        // Add hand here
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setItemInMainHand(new ItemStack(
        		hand.get(new Random().nextInt(hand.size()))));
        ((LivingEntity) this.getBukkitEntity()).getEquipment().setItemInOffHand(new ItemStack(
        		secondhand.get(new Random().nextInt(secondhand.size()))));
    }

    @Override
    public int getLevel(){
        return level;
    }

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public double getAttackSpeed() {
		return attackspeed;
	}

	@Override
	public MobTiers getTier() {
		return tier;
	}
}