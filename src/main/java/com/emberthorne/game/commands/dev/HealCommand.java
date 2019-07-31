package com.emberthorne.game.commands.dev;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.commands.EmberCommand;

public class HealCommand extends EmberCommand{
	
    Main game;
    public HealCommand(Main instance, String command, String usage, String description){
        super(command, usage, description);
    	this.game = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            EmberPlayer em = game.onlinePlayers.get(player.getUniqueId().toString());
            
            if(EmberRank.hasPerms(player, em.getRank(), EmberRank.GAMEMASTER)){
            	em.setHealth(em.getMaxHealth()+em.getArmor());
            	em.updateBar();
            	player.sendMessage(ChatColor.GREEN+"Healed");
            }
        }
		return true;
    }
}
