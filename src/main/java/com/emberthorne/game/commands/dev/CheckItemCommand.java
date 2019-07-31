package com.emberthorne.game.commands.dev;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.item.nbt.NBTItem;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.commands.EmberCommand;

public class CheckItemCommand extends EmberCommand{
	
    Main game;
    public CheckItemCommand(Main instance, String command, String usage, String description){
        super(command, usage, description);
    	this.game = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            EmberPlayer em = game.onlinePlayers.get(player.getUniqueId().toString());
            
            if(EmberRank.hasPerms(player, em.getRank(), EmberRank.GAMEMASTER)){
            	if(args.length == 0){
            		if(player.getItemInHand() != null){
            			if(player.getItemInHand().getType() != Material.AIR){
            				NBTItem item = new NBTItem(player.getItemInHand());
            				
            				if(item.hasKey("name")){
                				player.sendMessage("NAME: "+item.getString("name"));
                				player.sendMessage("TYPE: "+item.getString("type"));
                				player.sendMessage("RARITY: "+item.getString("rarity"));
                				player.sendMessage("TIER: "+item.getString("tier"));
                				
                				if(item.hasKey("minDmg")){
                					player.sendMessage("MIN-DMG: "+item.getInteger("minDmg"));
                					player.sendMessage("MAX-DMG: "+item.getInteger("maxDmg"));
                				}
            				}
            				else{
            					player.sendMessage(ChatColor.RED+"That item is not a custom item, destroy it!");
            				}
            			}
            		}
            	}
            	else{
            		player.sendMessage(ChatColor.RED+"/checkitem");
            	}
            }
        }
		return true;
    }
}
