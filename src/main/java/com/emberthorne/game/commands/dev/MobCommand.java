package com.emberthorne.game.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.item.ItemRarities;
import com.emberthorne.game.api.item.ItemTier;
import com.emberthorne.game.api.item.ItemType;
import com.emberthorne.game.api.mobs.CreatureType;
import com.emberthorne.game.api.mobs.MobTiers;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.api.utils.GameUtil;
import com.emberthorne.game.commands.EmberCommand;

import net.minecraft.server.v1_10_R1.Entity;

public class MobCommand extends EmberCommand{
	
    Main game;
    public MobCommand(Main instance, String command, String usage, String description){
        super(command, usage, description);
    	this.game = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            EmberPlayer em = game.onlinePlayers.get(player.getUniqueId().toString());
            
            if(EmberRank.hasPerms(player, em.getRank(), EmberRank.DEVELOPER)){
            	int amount = 0;
            	
            	if(args.length == 1){
            		amount = 1;
            	}
            	else if(args.length == 3){
            		if(GameUtil.isInteger(args[1])){
            			if(amount <= 100){
            				amount = Integer.parseInt(args[1]);
            			}
            			else{
                			player.sendMessage(ChatColor.RED+"/mob <type> [amount] [tier]");
                			return true;
            			}
            		}
            		else{
            			player.sendMessage(ChatColor.RED+"/mob <type> [amount] [tier]");
            			return true;
            		}
            	}
            	else{
            		player.sendMessage(ChatColor.RED+"/mob <type> [amount] [tier]");
            		return true;
            	}
            	
        		if(CreatureType.getMob(args[0]) != null){
        			if(MobTiers.valueOf(args[2].toUpperCase()) != null){
            			CreatureType ct = CreatureType.getMob(args[0]);
            			MobTiers tier = MobTiers.valueOf(args[2].toUpperCase());
            			
            			for(int i = 0; i<amount; i++){
            				Entity get = ct.spawnExact(player.getLocation(), tier);
            			}
            			
            			player.sendMessage(ChatColor.GREEN+"Spawned "+amount+"x "+ct.toString()+"!");	
        			}
        		}
        		else if(EntityType.valueOf(args[0].toUpperCase()) != null){
        			EntityType type = EntityType.valueOf(args[0]);
        			for(int i = 0; i<amount; i++){
        				player.getWorld().spawnEntity(player.getLocation(), type);
        			}
        			player.sendMessage(ChatColor.GREEN+"Spawned "+amount+"x "+type.toString()+"!");
        		}
        		else{
        			player.sendMessage(ChatColor.RED+"/mob <type> [amount] [tier]");
        			return true;
        		}
            }
			
        }
		return true;
    }

}
