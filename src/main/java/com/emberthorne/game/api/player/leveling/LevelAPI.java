package com.emberthorne.game.api.player.leveling;

public class LevelAPI {
	
	// Will get the exp needed for the players next level
	// currentlevel = players current level
	
	public static int generateSkillsExp(int currentlevel){
		double result = (currentlevel/0.06929646439);
		return (int)result^2;
	}
	
	public static int generateLevelExp(int currentlevel){
		//(CURRENTLVL/0.049)^2*10%+CURRENTLVL*5
		double result = (currentlevel/0.049);
		result = result*result;
		result = result*0.10;
		result = result+ (currentlevel*5);
		return (int)result;
	}
}
