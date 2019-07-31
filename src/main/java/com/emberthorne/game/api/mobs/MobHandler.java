package com.emberthorne.game.api.mobs;

import com.emberthorne.game.GameHandler;
import com.emberthorne.game.Main;
import com.emberthorne.game.api.mobs.type.MobBandit;
import com.emberthorne.game.api.mobs.type.MobGoblin;
import com.emberthorne.game.api.mobs.type.MobHare;
import com.emberthorne.game.api.utils.Reflection;

import java.util.Map;

public class MobHandler implements GameHandler{
	
    Main game;
    public MobHandler(Main instance){
        this.game = instance;
    }
	
    public void registerEntity(Class clazz, String name, int id){
        ((Map) Reflection.getPrivateField("c", net.minecraft.server.v1_10_R1.EntityTypes.class, null)).put(name, clazz);
        ((Map) Reflection.getPrivateField("d", net.minecraft.server.v1_10_R1.EntityTypes.class, null)).put(clazz, name);
        ((Map) Reflection.getPrivateField("f", net.minecraft.server.v1_10_R1.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
    }

	@Override
	public String getName() {
		return "HANDLER:MOBS";
	}

	@Override
	public void onEnable() {
		this.registerEntity(MobGoblin.class, "EMGoblin", 54);
		this.registerEntity(MobHare.class, "EMHare", 101);
		this.registerEntity(MobBandit.class, "EMBandit", 51);
	}
}
