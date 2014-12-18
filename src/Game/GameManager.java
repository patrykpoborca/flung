package Game;
import utility.*;

import java.util.*;

import android.util.Log;

public class GameManager {

public static float GameDifficulty = 1f; //lower to 0 the harder game becomes harder
//THe speed of the objects in the game!
public static float gameClock = 1f;
public static float SpawnTimer = 1.7f;	
public static float maxFloatingSpeed = 10f;
public static float outOfBoundsRatio = 0.9f;//how much of object can go out of bounds
public static float LengthRatio = 0.7f; //max magnitude of a line in regards to screen Height
public static float MinLengthRatio = 0.1f; //min magnitude
public static float obstacleLineWidth = 20f;
public static float minGapSize = 70f;


//used to correct for time spent paused
public static int CLOCK_CORRECTION = 0;

public static boolean ThreadRunning = true;

public static float getMaxSize()
{
	return (float)GameConstants.screenSizeY * LengthRatio;
}
public static float getMinSize()
{
	return (float)GameConstants.screenSizeY * MinLengthRatio;
}

/**
 * Used to find a line that has a good gap between itself and all other gaps that will be created!
 * @return
 */
public static FloatLine generateNonCollisionLine()
{	
	float Magnitude = getMaxSize() * (float)Math.random();
	if(Magnitude < getMinSize()) Magnitude = getMinSize();
	
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


public static void createLine()
{
	FloatingObject parameter = new FloatingObject(GameManager.generateNonCollisionLine());
	float ratio = ((float)Math.random() * 0.5f) + 0.5f; //mostly a horizontal movement
	parameter.setVelocityX(-1 * ratio * GameManager.maxFloatingSpeed);//has to be negative to go left
	//goes remaining max velocity either down or up
	parameter.setVelocityY( ((Math.random() <= 0.5) ? -1 : 1) * 
			(GameManager.maxFloatingSpeed - (Math.abs(parameter.getVelocityX()))));
	GameConstants.floatingStructures.add(parameter);

}

public static int getStandardTime()
{
	return (int)(1000 * (SpawnTimer * GameDifficulty));
}

public static void startTimer_Line(final int time)
{
	if(!ThreadRunning) return;
	Timer timer = new Timer();
	timer.schedule(new TimerTask()
	{
		@Override
		public void run()
		{
			if(CLOCK_CORRECTION != 0)
			{
				Log.d("NANO:", "" + CLOCK_CORRECTION);
				startTimer_Line(CLOCK_CORRECTION);
				CLOCK_CORRECTION = 0;
			}
			else
				{createLine();
				startTimer_Line(getStandardTime());
				}
			
			
		}
	}, time);

}
	
}
