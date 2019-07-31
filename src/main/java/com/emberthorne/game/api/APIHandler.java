package com.emberthorne.game.api;

import java.util.ArrayList;

import com.emberthorne.game.GameHandler;
import com.emberthorne.game.Main;
import com.emberthorne.game.api.listeners.damage.DamageListener;
import com.emberthorne.game.api.listeners.damage.DeathListener;
import com.emberthorne.game.api.player.armor.ArmorListener;
import com.emberthorne.game.api.player.chat.PlayerChat;
import com.emberthorne.game.api.player.health.PlayerJoinHealth;
import com.emberthorne.game.api.player.health.PlayerRespawn;
import com.emberthorne.game.api.player.load.PlayerInventoryLoad;
import com.emberthorne.game.api.player.load.PlayerJoin;
import com.emberthorne.game.api.player.load.PlayerQuit;
import com.emberthorne.game.api.player.location.PlayerJoinLocation;
import com.emberthorne.game.api.player.stamina.PlayerStamina;
import com.emberthorne.game.api.player.stamina.StaminaUtil;

public class APIHandler implements GameHandler{
	
	Main game;
	public APIHandler(Main instance){
		this.game = instance;
	}

	public String getName() {
		return "HANDLE:API";
	}

	public void onEnable() {
		game.getServer().getPluginManager().registerEvents(new PlayerJoin(game), game);
		game.getServer().getPluginManager().registerEvents(new PlayerQuit(game), game);
		
		game.getServer().getPluginManager().registerEvents(new PlayerJoinHealth(game), game);
		game.getServer().getPluginManager().registerEvents(new PlayerJoinLocation(game), game);
		game.getServer().getPluginManager().registerEvents(new PlayerRespawn(game), game);
		game.getServer().getPluginManager().registerEvents(new PlayerInventoryLoad(game), game);
		game.getServer().getPluginManager().registerEvents(new PlayerChat(game), game);
		game.getServer().getPluginManager().registerEvents(new PlayerStamina(game), game);
		
		game.getServer().getPluginManager().registerEvents(new ArmorListener(new ArrayList<String>(), game), game);
		game.getServer().getPluginManager().registerEvents(new DamageListener(game), game);
		game.getServer().getPluginManager().registerEvents(new DeathListener(game), game);
		
		// Start stamina task
		StaminaUtil stamina = new StaminaUtil(game);
	}
	
}