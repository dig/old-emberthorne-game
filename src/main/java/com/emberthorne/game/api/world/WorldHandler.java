package com.emberthorne.game.api.world;

import com.emberthorne.game.GameHandler;
import com.emberthorne.game.Main;
import com.emberthorne.game.api.world.listener.WorldListener;

public class WorldHandler implements GameHandler{
	
    Main game;
    public WorldHandler(Main instance){
        this.game = instance;
    }

	@Override
	public String getName() {
		return "HANDLER:WORLD";
	}

	@Override
	public void onEnable() {
		game.getServer().getPluginManager().registerEvents(new WorldListener(game), game);
	}
	
}