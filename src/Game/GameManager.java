package Game;
import utility.*;

import java.util.*;

import android.util.Log;

public class GameManager {

public static float GameDifficulty = 1f; //lower to 0 the harder game becomes harder
//THe speed of the objects in the game!
public static float gameClock = 1f;
public static float SpawnTimer = 2.7f;	
public static float maxFloatingSpeed = 10f;
public static float outOfBoundsRatio = 0.9f;//how much of object can go out of bounds
public static float LengthRatio = 0.7f; //max magnitude of a line in regards to screen Height
public static float MinLengthRatio = 0.1f; //min magnitude
public static float obstacleLineWidth = 20f;
public static float minGapSize = 70f;

public static int SWIPE_COUNT = 0;
public static int SWIPE_LIMIT = -1;

private static int BASE_PLAYER_LIVES = 10;
private static int LIVES_LOST = 0;


public static MetaPlayerData SELF = new MetaPlayerData();

public static void resetGameManager()
{
	SWIPE_COUNT = 0;
	LIVES_LOST =0;
	ThreadRunning = true;
	PAUSE_SPAWN_CLOCK = 0;
	PLAYER_POINTS = 0;
}

public static int RemainingSwipes()
{
	if(SWIPE_LIMIT == -1) return -1;
	return SWIPE_LIMIT- SWIPE_COUNT;
}
public static int adjustPlayerLives(int amount)
{
	LIVES_LOST-=amount;
	
	return getPlayerLives() - LIVES_LOST;
}
public static int getPlayerLives()
{	
	return ((GameDifficulty == 0) ? 1 : (int)(BASE_PLAYER_LIVES * GameDifficulty)) + LIVES_LOST;}

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
	//Log.d("ADDING!", "HELLo...");
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
	float ratio = ((float)Math.random() * 0.3f) + 0.7f; //mostly a horizontal movement
	parameter.setVelocityX(-1 * ratio * GameManager.maxFloatingSpeed);//has to be negative to go left
	//goes remaining max velocity either down or up
	parameter.setVelocityY( ((Math.random() <= 0.5) ? -1 : 1) * 
			(GameManager.maxFloatingSpeed - (Math.abs(parameter.getVelocityX()))));
	GameConstants.floatingStructures.add(parameter);

}

public static int getStandardTime()
{
	return (int)(1000 * (SpawnTimer * ((GameDifficulty <= 0.5) ? 0.5 : GameDifficulty)));
}

//used to correct for time spent paused
public static int PAUSE_SPAWN_CLOCK = 0;
private static float TIMER_AMOUNT = 5;
public static int PLAYER_POINTS =0;
private static int POINT_INC = 2;
public static void startPointTally()
{
	if(!ThreadRunning) return;
	Timer timer = new Timer();
	timer.schedule(new TimerTask()
	{
		@Override
		public void run()
		{
			PLAYER_POINTS+= 1;
			startPointTally();
		}
	}, ((int) (((GameDifficulty <= 0.1) ? 0.1 : GameDifficulty) * (float)TIMER_AMOUNT * 100f))/POINT_INC);// base: 0.5s, can be every 0.02 seconds

}

public static void startTimer_Line()
{
	if(!ThreadRunning) return;
	Timer timer = new Timer();
	timer.schedule(new TimerTask()
	{
		@Override
		public void run()
		{
			if(GameManager.PAUSE_SPAWN_CLOCK != 0)
			{
				if(PAUSE_SPAWN_CLOCK == 1) PAUSE_SPAWN_CLOCK++;
				startTimer_Line();
				return;
			}
			//Log.d("TimerCall", " bleh");
			createLine();
			startTimer_Line();
			
		}
	}, getStandardTime());

}
	
}
