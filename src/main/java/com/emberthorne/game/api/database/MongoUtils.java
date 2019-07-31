package com.emberthorne.game.api.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoUtils {
    public static DBObject updateField(DBObject found, String field, String update){
    	DBObject obj = new BasicDBObject();
    	obj.putAll(found);
    	obj.removeField(field);
    	obj.put(field, update);
    	return obj;
    }
    
    public static DBObject updateField(DBObject found, String field, int update){
    	DBObject obj = new BasicDBObject();
    	obj.putAll(found);
    	obj.removeField(field);
    	obj.put(field, update);
    	return obj;
    }
    
    public static DBObject updateField(DBObject found, String field, double update){
    	DBObject obj = new BasicDBObject();
    	obj.putAll(found);
    	obj.removeField(field);
    	obj.put(field, update);
    	return obj;
    }
    
    public static DBObject updateField(DBObject found, String field, long update){
    	DBObject obj = new BasicDBObject();
    	obj.putAll(found);
    	obj.removeField(field);
    	obj.put(field, update);
    	return obj;
    }
    
    public static DBObject updateField(DBObject found, String field, boolean update){
    	DBObject obj = new BasicDBObject();
    	obj.putAll(found);
    	obj.removeField(field);
    	obj.put(field, update);
    	return obj;
    }
}
