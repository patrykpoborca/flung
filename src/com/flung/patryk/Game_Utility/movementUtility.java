package com.flung.patryk.Game_Utility;
import android.util.Log;

final public class movementUtility {

	public static float calculateSpeed(float magnitude)
	{
		//basically from 0 - 1f we have a ratio which is min to max swipe distance (0.7 of the screen)
		float ratio = GameConstants.getDiagonalRatio(magnitude);
		//mult that ration with the speed to get a certain speed.
		float speed = GameConstants.maxSpeed * ratio;
		
		return speed;
	}
	
	/**
	 * Calculates the speed vector, based off of GameConstant's maxspeed. Directions are based of x, y
	 * @param magnitude
	 * @param x
	 * @param y
	 * @return
	 */
	public static FloatPoint calculateSpeedVector(float magnitude, float x, float y)
	{
		
		//Get's the speed
		float speed = calculateSpeed(magnitude);
	
		float hypRatio = speed/magnitude; 
		
		FloatPoint r_val = new FloatPoint(x*hypRatio, y*hypRatio);
		

		return r_val;
	}
	
}
