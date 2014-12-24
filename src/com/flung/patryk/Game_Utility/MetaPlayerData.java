package com.flung.patryk.Game_Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

import com.flung.*;
import com.flung.patryk.Game.GameManager;
import com.flung.patryk.GameOver_Activity.*;
import com.flung.patryk.MainActivity.MainActivity;
import com.flung.patryk.SettingsActivity.SettingsActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


/**
 * Class which holds data about the player... persists from game to game.
 * @author Patryk
 *
 */
final public class MetaPlayerData {

	public  String UserName= "NOT IMPLEMENTED";
	public  int highScore=0;
	public  float DifficultyPreference = 1;
	public boolean UseGravity =false ;
	public boolean ClickChallenge = false;
	
	public MetaPlayerData(){}
	
	
	/**
	 * Used to load player data into GameManager.SELF
	 * @param context
	 */
	public static void LoadPlayerData(Context context)
	{
		String path = context.getFilesDir().getAbsolutePath();
		File file = new File(path + GameConstants.fileName);
		
		if(!file.exists()) return;
		
			int length = (int) file.length();

			byte[] bytes = new byte[length];

			
			try {
				FileInputStream in = new FileInputStream(file);
			    in.read(bytes);
			    in.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
			String contents = new String(bytes);   

			int index = 0;
			
			Field field;
			String returnedString[];
			
			while(true)
			{
				index = ReflectionHelper.getNext(index, contents);
				
				returnedString = ReflectionHelper.cleanParse(contents.substring(0 , index));
				field = ReflectionHelper.getField(GameManager.SELF, returnedString[0]);
				
				
				ReflectionHelper.SetField(field, GameManager.SELF, returnedString[1]);				
				contents = contents.substring(index+3);
				
				index =0;
				if(contents.length() <=3)
				{
					break;
					
				}
			}
		
			playerToSettings();
	}

	/**
	 * Used to update all the settings of the Game based upon player's preferences. Called on save. WIll also save to local file
	 */
	public static void playerToSettings()
	{
		GameManager.GameDifficulty = GameManager.SELF.DifficultyPreference;
		GameManager.SWIPE_LIMIT = (GameManager.SELF.ClickChallenge) ? GameManager.SWIPE_CONSTANT : -1;
		GameManager.usingGravity = (GameManager.SELF.UseGravity) ? GameConstants.gravity : 0;
	
	}
	
	
	/**
	 * Method is used to parse the GameManager.SELF and save it into GameConstants.fileName directory
	 * @param context
	 */
	public static void SavePlayerData(Context context)
	{
		String path = context.getFilesDir().getAbsolutePath();
		File file = new File(path + GameConstants.fileName);
		
		
		try {
			FileOutputStream stream = new FileOutputStream(file);
			byte bytearray[]= ReflectionHelper.createParsedString_as_bytes(GameManager.SELF);
			Log.d("PreWRITE", new String(bytearray));
			stream.write(bytearray);
		    stream.close();
		    
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	
	
}
