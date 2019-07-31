package com.emberthorne.game.api.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.emberthorne.game.Main;

public class GameUtil {
    public static boolean arePlayersNearby(Location location, int radius) {
        for (Player player : location.getWorld().getPlayers()) {
            if (!Main.getInstance().onlinePlayers.containsKey(player.getUniqueId().toString())) {
                continue;
            }
            if (location.distanceSquared(player.getLocation()) <= radius * radius) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
    
	 public static void log(String message, String file){
	    	try{
	            File dataFolder = Main.getInstance().getDataFolder();
	            if(!dataFolder.exists()){
	                dataFolder.mkdir();
	            }
	            File saveTo = new File(Main.getInstance().getDataFolder(), file);
	            if (!saveTo.exists()){
	                saveTo.createNewFile();
	            }
	            FileWriter fw = new FileWriter(saveTo, true);
	            PrintWriter pw = new PrintWriter(fw);
	            pw.println(message);
	            pw.flush();
	            pw.close();
	        } catch (IOException e){
	            e.printStackTrace();
	        }
	 
	}
}
