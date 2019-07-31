package com.emberthorne.game.api.database;

import com.emberthorne.game.Main;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class ServerDB {
	
	public Main plugin;
	public MongoManager db;
	public ServerDB(Main instance, MongoManager dinstance) {
		plugin = instance;
		db = dinstance;
	}
	
    public void removeServer(String id){
    	DBCollection coll = db.getDB().getCollection("server_data");
        DBObject r = new BasicDBObject("name", id);
        DBObject found = coll.findOne(r);
        if(found != null){
        	coll.remove(found);
        }
    }
    
    public void addServer(String id, String ip, int port){
    	DBCollection coll = db.getDB().getCollection("server_data");
        DBObject obj = new BasicDBObject("type", "GAME");
        obj.put("name", id);
        obj.put("ip", ip);
        obj.put("port", port);
        coll.insert(obj);
    }
	
}
