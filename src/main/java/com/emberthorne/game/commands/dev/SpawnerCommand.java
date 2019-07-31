package com.emberthorne.game.commands.dev;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;
import com.emberthorne.game.api.mobs.CreatureType;
import com.emberthorne.game.api.mobs.MobTiers;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.rank.EmberRank;
import com.emberthorne.game.api.utils.GameUtil;
import com.emberthorne.game.api.world.spawning.BaseMobSpawner;
import com.emberthorne.game.commands.EmberCommand;

public class SpawnerCommand extends EmberCommand{
	
    Main game;
    public SpawnerCommand(Main instance, String command, String usage, String description){
        super(command, usage, description);
    	this.game = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            EmberPlayer em = game.onlinePlayers.get(player.getUniqueId().toString());
            
            if(EmberRank.hasPerms(player, em.getRank(), EmberRank.DEVELOPER)){
            	if(args.length == 6){
            		if(CreatureType.getMob(args[0]) != null){
            			if(GameUtil.isInteger(args[1]) && MobTiers.getTier(Integer.parseInt(args[1])) != null){
            				if(GameUtil.isInteger(args[2]) && GameUtil.isInteger(args[3]) 
            						&& GameUtil.isInteger(args[4]) && GameUtil.isInteger(args[5])){
            					BaseMobSpawner spawn = new BaseMobSpawner(player.getLocation(), 
            							CreatureType.getMob(args[0]), MobTiers.getTier(Integer.parseInt(args[1])), 
            							Integer.parseInt(args[2]), Integer.parseInt(args[3]), 
            							Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            					spawn.start();
            					
            					player.sendMessage(ChatColor.GREEN+"Started a "+CreatureType.getMob(args[0]).toString()+" spawner at your location!");
            					return true;
            				}
            			}
            		}
            	}
            	player.sendMessage(ChatColor.RED+"/spawner <type> <tier> <spawnAmount> <respawnDelay> <minXZ> <maxXZ>");
            }
			
        }
		return true;
    }
}
