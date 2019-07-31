package com.emberthorne.game.commands;

import com.emberthorne.game.GameHandler;
import com.emberthorne.game.Main;
import com.emberthorne.game.commands.dev.CheckItemCommand;
import com.emberthorne.game.commands.dev.FeedCommand;
import com.emberthorne.game.commands.dev.HealCommand;
import com.emberthorne.game.commands.dev.MobCommand;
import com.emberthorne.game.commands.dev.RandomItemCommand;
import com.emberthorne.game.commands.dev.SetRankCommand;
import com.emberthorne.game.commands.dev.SpawnerCommand;
import com.emberthorne.game.commands.dev.TestCommand;

public class CommandHandler implements GameHandler{
	
    Main game;
    public CommandHandler(Main instance){
        this.game = instance;
    }
    
	@Override
	public String getName() {
		return "HANDLER:CMD";
	}
	
	@Override
	public void onEnable() {
		this.registerCommand(new MobCommand(game, "mob", "/<command> [args]", "Spawns our custom mob or bukkit mobs"));
		this.registerCommand(new SpawnerCommand(game, "spawner", "/<command> [args]", "Creates a new world spawner"));
		this.registerCommand(new RandomItemCommand(game, "randomitem", "/<command> [args]", "Generates a random item"));
		this.registerCommand(new CheckItemCommand(game, "checkitem", "/<command> [args]", "Checks the item"));
		this.registerCommand(new HealCommand(game, "heal", "/<command> [args]", "Heals yourself"));
		this.registerCommand(new FeedCommand(game, "feed", "/<command> [args]", "Feed yourself"));
		this.registerCommand(new SetRankCommand(game, "setrank", "/<command> [args]", "Sets a players rank"));
		this.registerCommand(new TestCommand(game, "test", "/<command> [args]", "Test command"));
	}
	
    public void registerCommand(EmberCommand command) {
        command.register();
    }

}
