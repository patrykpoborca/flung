package utility;

import java.util.ArrayList;
import android.util.Log;
import Game.GameObject;


final public class GameConstants {
	
	//This is for all objects being managed at any point of the game
	final public static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	final public static float gravity = 0.981f/2f;
	public static float ScrollSpeed = 10f;
	public static int screenSizeX;
	public static int screenSizeY;
	public static float screenSizeDiagonal;
	public static float maxForceX; //used for thresholds on flings
	public static float maxForceY; //used for thresholds on flings
	public static float maxSpeed = 20f;//used to indicate maximum amount of speed that may be applied
	
	
	
	public static float Percent_Max = 0.7f;
	public static float Percent_Med = 0.4f;
	
	//gets you the SAFE diagonal ratio
	final public static float getDiagonalRatio(float magnitude){
		
		float ratio = magnitude/(screenSizeDiagonal* Percent_Max);
		
		if(ratio >1f) return 1f;
		
		return ratio;
	}
	
	
	
}
