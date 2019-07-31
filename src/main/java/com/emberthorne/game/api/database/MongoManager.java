package com.emberthorne.game.api.database;

import java.net.UnknownHostException;

import com.emberthorne.game.Main;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoManager {
	
	Main game;
	public MongoManager(Main instance, String ip, int port, String username, String password){
		this.game = instance;
		try {
			this.connect(ip, port, username, password);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Error while connecting to the Mongo database!");
		}
	}
	
    private MongoClient client;
    private DB mcserverdb;
    
    public void connect(String ip, int port, String username, String password) throws UnknownHostException{
    	System.out.println("Connecting to the mongo database...");
        client = new MongoClient(ip, port);
        mcserverdb = client.getDB("mainserver");
        System.out.println("Connected, attempting to authenticate...");
        mcserverdb.authenticate(username, password.toCharArray());
        
        if(this.isAuthenticated()){
        	System.out.println("MongoDB connected & authenticated! Ready to start server.");	
        	game.isConnected = true;
        }
    }
    
    public MongoClient getClient(){
    	return client;
    }
    
    public DB getDB(){
    	return mcserverdb;
    }
    
    public boolean isAuthenticated(){
    	return mcserverdb.isAuthenticated();
    }
}
