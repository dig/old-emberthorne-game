package com.emberthorne.game.commands.dev;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.commands.EmberCommand;

public class SetRankCommand extends EmberCommand{
	
    Main game;
    public SetRankCommand(Main instance, String command, String usage, String description){
        super(command, usage, description);
    	this.game = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            EmberPlayer em = game.onlinePlayers.get(player.getUniqueId().toString());
            
            if(EmberRank.hasPerms(player, em.getRank(), EmberRank.GAMEMASTER)){
            	if(args.length == 2){
            		Player target = Bukkit.getPlayer(args[0]);
            		
            		if(target != null && target.isOnline()){
            			if(EmberRank.valueOf(args[1].toUpperCase()) != null){
            				EmberRank rank = EmberRank.valueOf(args[1].toUpperCase());
            				
            				EmberPlayer tar = game.onlinePlayers.get(target.getUniqueId().toString());
            				tar.setRank(rank);
            				player.sendMessage(ChatColor.GREEN+"Set "+target.getName()+" to "+rank.toString()+"!");
            			}
            			else{
            				fail(player);
            			}
            		}
            		else{
            			player.sendMessage(ChatColor.RED+"Cannot find that player?");
            		}
            	}
            	else{
            		fail(player);
            	}
            }
        }
		return true;
    }
    
    public void fail(Player player){
		player.sendMessage(ChatColor.RED+"/setrank <name> <rank>");
		List<String> ranks = new ArrayList<String>();
		
		for(EmberRank r : EmberRank.values()){
			if(r != EmberRank.LEADERSHIP && r != EmberRank.DEVELOPER){
				ranks.add(r.toString().toLowerCase());
			}
		}
		
		player.sendMessage(ChatColor.RED+"Ranks: "+ranks.toString());
    }
}
