package com.emberthorne.game.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.item.ItemGenerator;
import com.emberthorne.game.api.item.ItemRarities;
import com.emberthorne.game.api.item.ItemTier;
import com.emberthorne.game.api.item.ItemType;
import com.emberthorne.game.api.item.weapon.damage.DamageUtil;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.api.utils.GameUtil;
import com.emberthorne.game.commands.EmberCommand;

public class RandomItemCommand extends EmberCommand{
	
    Main game;
    public RandomItemCommand(Main instance, String command, String usage, String description){
        super(command, usage, description);
    	this.game = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            EmberPlayer em = game.onlinePlayers.get(player.getUniqueId().toString());
            
            if(EmberRank.hasPerms(player, em.getRank(), EmberRank.DEVELOPER)){
            	if(args.length == 1){
            		if(ItemTier.getTier(args[0]) != null){
            			player.getInventory().addItem(ItemGenerator.getItem(ItemTier.getTier(args[0])));
            		}
                	else{
                		player.sendMessage(ChatColor.RED+"/randomitem <tier>");
                	}
            	}
            	else{
            		player.sendMessage(ChatColor.RED+"/randomitem <tier>");
            	}
            }
        }
		return true;
    }
}
