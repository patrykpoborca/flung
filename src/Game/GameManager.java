package Game;
import utility.*;

import java.util.*;

public class GameManager {

public static float GameDifficulty = 1f; //lower to 0 the harder game becomes
public static float SpawnTimer = 10f;	
public static float maxFloatingSpeed = 10f;
public static float outOfBoundsRatio = 0.9f;//how much of object can go out of bounds
public static float LengthRatio = 0.7f; //max magnitude of a line in regards to screen Height
public static float obstacleLineWidth = 20f;
public static float minGapSize = 70f;

public static float getMaxSize()
{
	return (float)GameConstants.screenSizeY * LengthRatio;
}

/**
 * Used to find a line that has a good gap between itself and all other gaps that will be created!
 * @return
 */
public static FloatLine generateNonCollisionLine()
{
	float Magnitude = getMaxSize() * (float)Math.random();
	float splitRatio = (float)Math.random(); //ratio for X to Y
	float width, height, y_range, y_Position;
	FloatPoint XY, XY_second;
	FloatLine temporary;
	while(true)
	{
		width= (splitRatio * Magnitude) + minGapSize; 
		height = (Magnitude - width) + minGapSize;
		y_range = (float)GameConstants.screenSizeY * outOfBoundsRatio;
		y_Position = (float)Math.random() * y_range; //position of y
		XY = new FloatPoint(GameConstants.screenSizeX +10f, y_Position);
		XY_second = new FloatPoint(XY.X + width, XY.Y + height);
		
		temporary = new FloatLine(XY, XY_second, GameManager.obstacleLineWidth);

		
		if(!GameConstants.intersectsObjects(temporary)) break;
	}
	width= splitRatio * Magnitude; 
	height = Magnitude - width;
	y_range = (float)GameConstants.screenSizeY * outOfBoundsRatio;
	y_Position = (float)Math.random() * y_range; //position of y
	XY = new FloatPoint(GameConstants.screenSizeX +10f, y_Position);
	XY_second = new FloatPoint(XY.X + width, XY.Y + height);
	return new FloatLine(XY, XY_second, GameManager.obstacleLineWidth);
	
}



public static void startTimer_Line()
{
	Timer timer = new Timer();
	timer.schedule(new TimerTask()
	{
		@Override
		public void run()
		{
			FloatingObject parameter = new FloatingObject(GameManager.generateNonCollisionLine());
			float ratio = (float)Math.random();
			parameter.setVelocityX(-1 * ratio * GameManager.maxFloatingSpeed);//has to be negative to go left
			//goes remaining max velocity either down or up
			parameter.setVelocityY( ((Math.random() <= 0.5) ? -1 : 1) * 
					(GameManager.maxFloatingSpeed - (Math.abs(parameter.getVelocityX()))));
			GameConstants.floatingStructures.add(parameter);
			startTimer_Line();
		}
	}, (int)(1000 * (SpawnTimer * GameDifficulty)));
}
	
}
