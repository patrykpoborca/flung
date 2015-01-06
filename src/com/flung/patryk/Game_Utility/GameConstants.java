package com.flung.patryk.Game_Utility;

import java.util.ArrayList;

import com.flung.patryk.Game.*;

import android.util.Log;


final public class GameConstants {
	
	//This is for all objects being managed at any point of the game
	final public static ArrayList<FloatingObject> floatingStructures = new ArrayList<FloatingObject>();
	final public static ArrayList<GameObject> PowerUps= new ArrayList<GameObject>();
	final public static ArrayList<TriggerGameObject> borders = new ArrayList<TriggerGameObject>();
	final public static float gravity = 0.981f/4f;
	public static float ScrollSpeed = 10f;
	
	
	
	public static int screenSizeX;
	public static int screenSizeY;
	public static float screenSizeDiagonal;
	public static float maxForceX; //used for thresholds on flings
	public static float maxForceY; //used for thresholds on flings
	public static float maxSpeed = 20f;//used to indicate maximum amount of speed that may be applied
	
	
	
	public static String fileName = "/FLUNG_savedPreferences.txt";
	
	public static int LifeSize = 70;
	
	//private static ArrayList<FloatingObject> removeStructures = new ArrayList<FloatingObject>();
	
	public static float Percent_Max = 0.7f;
	public static float Percent_Med = 0.4f;
	
	
	
	
	public static void clearLists()
	{
		GameConstants.floatingStructures.clear();
		GameConstants.borders.clear();
		GameConstants.PowerUps.clear();
	}
	
		
	//gets you the SAFE diagonal ratio
	final public static float getDiagonalRatio(float magnitude){
		
		float ratio = magnitude/(screenSizeDiagonal* Percent_Max);
		
		if(ratio >1f) return 1f;
		
		return ratio;
	}
	
	
	public static boolean intersectsObjects(FloatLine obj)
	{
		for(int a=0; a < floatingStructures.size(); a++)
		{
			
			if(FloatLine.IntersectsLine(obj, floatingStructures.get(a).position))
				return true;
		}
		return false;
	}
	
	
}
