package com.emberthorne.game;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.emberthorne.game.api.APIHandler;
import com.emberthorne.game.api.database.MongoManager;
import com.emberthorne.game.api.database.ServerDB;
import com.emberthorne.game.api.listeners.ListenHandler;
import com.emberthorne.game.api.mobs.MobHandler;
import com.emberthorne.game.api.player.EmberPlayer;
import com.emberthorne.game.api.player.load.PlayerQuit;
import com.emberthorne.game.api.world.WorldHandler;
import com.emberthorne.game.api.world.spawning.SpawnerHandler;
import com.emberthorne.game.commands.CommandHandler;
import com.emberthorne.game.network.NetworkManager;
import com.emberthorne.game.network.NetworkManager.ServerType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import lombok.Getter;

public class Main extends JavaPlugin{
	
	public static String patch = "1.0.1";
	public String serverid = null;
	
	public ConcurrentHashMap<String, EmberPlayer> onlinePlayers;
	public MongoManager database;
	public NetworkManager master_server;
	
	public boolean isConnected = false;
	
	public APIHandler api;
	public CommandHandler cmd;
	public MobHandler mob;
	public WorldHandler world;
	public ListenHandler listen;
	
	@Getter private static Main instance;
	
	public void onEnable(){
		instance = this;
		
		Bukkit.getServer().setWhitelist(true);
		serverid = getConfig().getString("server");
		
		System.out.println("### EMBERTHORNE SERVER START ###");
		System.out.println("# Running patch: "+patch);
		System.out.println("# Server Id: "+serverid);
		System.out.println("# Loading plugin...");
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.saveResource("config.yml", false);
		onlinePlayers = new ConcurrentHashMap<String, EmberPlayer>();
		
		api = new APIHandler(this);
		api.onEnable();
		cmd = new CommandHandler(this);
		cmd.onEnable();
		mob = new MobHandler(this);
		mob.onEnable();
		world = new WorldHandler(this);
		world.onEnable();
		listen = new ListenHandler(this);
		listen.onEnable();
		
		// Connect to database
		database = new MongoManager(this, getConfig().getString("database.ip"), 
				getConfig().getInt("database.port"), getConfig().getString("database.username"), 
				getConfig().getString("database.password"));
		
		// Delete old dats
        try {
            FileUtils.deleteDirectory(new File("world" + File.separator + "playerdata"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		System.out.println("### EMBERTHORNE PLUGIN READY ###");
		
		// Connect to MASTER server
		master_server = new NetworkManager(getConfig().getString("master.ip"), 
				getConfig().getInt("master.port"), 
				getConfig().getInt("master.udp"), 
				getConfig().getString("master.authkey"));
		master_server.sendUpdate(serverid, Bukkit.getIp(), Bukkit.getPort(), 
				ServerType.GAME, Bukkit.getMaxPlayers(), 0);
		
		// Update mongo
		ServerDB db = new ServerDB(this, database);
		db.removeServer(serverid.toUpperCase());
		db.addServer(serverid.toUpperCase(), Bukkit.getIp(), Bukkit.getPort());
		
		Bukkit.getServer().setWhitelist(false);
	}
	
	public void onDisable(){
		System.out.println("### EMBERTHORNE SHUTTING DOWN ###");
		
		// Remove from mongo
		ServerDB db = new ServerDB(this, database);
		db.removeServer(serverid.toUpperCase());
		
		// Save online players incase of crash
		PlayerQuit q = new PlayerQuit(this);
		for(String uuid : onlinePlayers.keySet()){
            Player p = Bukkit.getPlayer(UUID.fromString(uuid));
			EmberPlayer em = onlinePlayers.get(uuid);
			
        	DBCollection coll = database.getDB().getCollection("player_data");
            DBObject r = new BasicDBObject("uuid", uuid);
            DBObject found = coll.findOne(r);
            
            if(found != null){
            	r.putAll(found);
            	r = q.save(em,p,r);
            	coll.update(found,r);
            }
            else{
            	r = q.save(em,p,r);
            	coll.insert(r);
            }
            onlinePlayers.remove(uuid);
		}
		
		// Stop all spawners
		SpawnerHandler.stopAll();
		
		// Mongo close
		database.getClient().close();
	}

}
