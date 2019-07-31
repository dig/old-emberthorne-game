package com.emberthorne.game.api.player.rank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import lombok.Getter;

public enum EmberRank {
	
	PLAYER(0, 1, false, ""),
	PLAYERMOD(20, 8, true, ChatColor.AQUA+"PM"),
	GAMEMASTER(21, 9, true, ChatColor.BLUE+"GM"),
	DEVELOPER(25, 50, true, ChatColor.GREEN+"DEV"),
	LEADERSHIP(30, 100, true, ChatColor.RED+"LEADER");
	
	@Getter int id;
	@Getter int power;
	@Getter boolean staff;
	@Getter String prefix;
	@Getter ChatColor color;
	
	EmberRank(int identity, int power, boolean staff, String prefix){
		this.id=identity;
		this.power=power;
		this.staff=staff;
		this.prefix=prefix;
	}
	
	public static EmberRank getRankById(int id){
		for(EmberRank r : EmberRank.values()){
			if(r.getId() == id){
				return r;
			}
		}
		return null;
	}
	
	public static boolean hasPerms(Player p, EmberRank have, EmberRank need){
		boolean result = false;
		if(have.getPower()>=need.getPower()){
			result = true;
		}
		return result;
	}
	
}
