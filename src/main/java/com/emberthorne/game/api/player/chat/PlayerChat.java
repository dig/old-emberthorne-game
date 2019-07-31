package com.emberthorne.game.api.player.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.emberthorne.game.Main;

public class PlayerChat implements Listener{
	
	Main game;
	public PlayerChat(Main instance){
		this.game = instance;
	}
	
    public static List<String> bannedWords = new ArrayList<>(Arrays.asList("shit", "fuck", "cunt", "bitch", "whore",
            "slut", "wank", "asshole", "cock",
            "dick", "clit", "homo", "fag", "faggot", "queer", "nigger", "nigga", "dike", "dyke", "retard", " " +
                    "motherfucker", "vagina", "boob", "pussy", "rape", "gay", "penis",
            "cunt", "titty", "anus", " faggot", "blowjob", "handjob", "bast", "minecade", "ass", "@ss", "mystic " +
                    "runes", "mysticrunes", "f@g", "d1ck", "titanrift", "wynncraft", "titan rift", "kys", "jigga",
            "jiggaboo", "hitler", "jews", "titanrift", "fucked", "dungeonrealms"));
    
    public String checkBannedWords(String message, String replacement){
    	for(String banned : bannedWords){
    		if(message.contains(banned)){
    			message = message.replaceAll(banned, replacement);
    		}
    	}
    	return message;
    }
	
	@EventHandler
    public void doChat(AsyncPlayerChatEvent event) {
    	Player p = event.getPlayer();
    	String message = event.getMessage();
    	message = checkBannedWords(message, "#$£%");
    	
    	
    }
}
