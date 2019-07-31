package com.emberthorne.game.network;

import java.io.IOException;

import org.bukkit.Bukkit;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class NetworkManager {
	
	private Client client;
	private String authkey;
	
	private boolean connected;
	
	public NetworkManager(String ip, int port, int udp, String authkey){
		this.authkey = authkey;
		
		// Connecting..
	    client = new Client();
	    client.start();
	    
	    // Register classes
	    Kryo kryo = client.getKryo();
	    kryo.register(EMServer.class);
	    
	    try {
			client.connect(5000, ip, port);
			System.out.println("Connected to MASTER server!");
			connected = true;
		} catch (IOException e) {
			connected = false;
			System.out.println("Failed to connect to the MASTER server, shutting server down...");
			Bukkit.shutdown();
		} catch (Exception e) {
			connected = false;
			System.out.println("Failed to connect to the MASTER server, shutting server down...");
			Bukkit.shutdown();
		}
	}
	
	public void sendUpdate(String id, String ip, int port, ServerType type, int max_players, int players){
		if(connected){
			EMServer server = new EMServer();
			server.authkey = authkey;
			server.id = id;
			server.ip = ip;
			server.port = port;
			server.type = type.toString();
			server.max_players = max_players;
			server.players = players;
			client.sendTCP(server);
		}
	}
	
    public static class EMServer {
        public String authkey;
        public String id;
        public String ip;
        public int port;
        public String type;
        public int max_players;
        public int players;
     }
    
    public enum ServerType {
    	GAME,
    	BUNGEE,
    	HUB;
    }
}
