package com.emberthorne.game.api.listeners;

import com.emberthorne.game.GameHandler;
import com.emberthorne.game.Main;

public class ListenHandler implements GameHandler{
	
    Main game;
    public ListenHandler(Main instance){
        this.game = instance;
    }
    
	@Override
	public String getName() {
		return "HANDLER:LISTENERS";
	}
	
	@Override
	public void onEnable() {
		game.getServer().getPluginManager().registerEvents(new MobListener(), game);
		game.getServer().getPluginManager().registerEvents(new PlayerListener(game), game);
	}
}
