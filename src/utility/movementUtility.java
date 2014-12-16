package utility;
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
	
		//Use get's the angle between the X and Y values (THey are passed into the method as
		// End.x-start.x, End.y - start.y)
		double angle = Math.toDegrees( Math.atan2(y, x)); //angle of right triangle at origin.
		
		//then we just pultiple the hypotenuse (max speed) by the cos/sin(theta) to get proper sizes
		double adjustedHeight = Math.abs(Math.sin(angle) * (double)speed);
		double adjustedWidth = Math.abs((Math.cos(angle) *(double)speed));
		
		FloatPoint r_val = new FloatPoint();
		//Basically to see if our newly found forces will be applied in the negative or positive.
		r_val.X = (float)adjustedHeight * ((x < 0) ? -1 : 1); //we want the proper direction
		r_val.Y = (float)adjustedWidth  * ((y < 0) ? -1 : 1);
		
		
		return r_val;
	}
	
}
