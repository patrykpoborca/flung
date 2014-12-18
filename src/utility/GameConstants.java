package utility;

import java.util.ArrayList;
import android.util.Log;
import Game.*;


final public class GameConstants {
	
	//This is for all objects being managed at any point of the game
	final public static ArrayList<FloatingObject> floatingStructures = new ArrayList<FloatingObject>();
	final public static ArrayList<TriggerGameObject> borders = new ArrayList<TriggerGameObject>();
	final public static float gravity = 0.981f/2f;
	public static float ScrollSpeed = 10f;
	public static int screenSizeX;
	public static int screenSizeY;
	public static float screenSizeDiagonal;
	public static float maxForceX; //used for thresholds on flings
	public static float maxForceY; //used for thresholds on flings
	public static float maxSpeed = 20f;//used to indicate maximum amount of speed that may be applied
	
	
	//private static ArrayList<FloatingObject> removeStructures = new ArrayList<FloatingObject>();
	
	public static float Percent_Max = 0.7f;
	public static float Percent_Med = 0.4f;
	
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
