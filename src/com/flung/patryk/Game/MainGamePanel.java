package com.flung.patryk.Game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.flung.patryk.R;
import com.flung.patryk.GameActivity.GameActivity;
import com.flung.patryk.GameOver_Activity.*;
import com.flung.patryk.Game_Utility.*;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private GameThread thread;
	
	//MOVEMENT RELATED STUFF
	private int PAUSED; // 0 unpaused, 1 first frame paused, 2 really paused

	private float prevX;
	private float prevY;
	private float currentX;
	private float currentY;
	
	public Context thisContext;
	
	
	
	//end of movement related stuff
	public int Blink_Timer = 0;
	public FlungObject playerOne;
	
	private float MAGNITUDE;
	
	private int taskPID;

	public MainGamePanel(Context context, int taskPID) {
		super(context);
		this.thisContext = context;
		this.PAUSED = 0;
		this.taskPID = taskPID;
		GameManager.resetAll(this.thisContext);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		setupGame();
		//
				// create the game loop thread
		thread = new GameThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		GameManager.startTimer_Line(this.taskPID);
		GameManager.startPointTally(this.taskPID);
		GameManager.startPowerUpTimer(this.taskPID);
	}
	
	
	public void resetPosition()
	{
		ArrayList<Bitmap> list = new ArrayList<Bitmap>();
		list.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird1));
		list.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird2));
		list.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird3));
		list.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird4));
		playerOne = new FlungObject(list, (int)((double)GameConstants.screenSizeX*0.1),
							GameConstants.screenSizeY /2 - 50);
		
		playerOne.setConstantForceY(GameManager.usingGravity);
	}
	
	
	private void setupGame()
	{
		
		
		
		
		this.resetPosition();
		
		//GameConstants.floatingStructures.add(new GameObject(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 200, 200));
		
		
		//setting up borders
		//bottom
		GameConstants.borders.add(new ExitBorders(0, GameConstants.screenSizeY,
						(float) GameConstants.screenSizeX, 10f));
		//right
		GameConstants.borders.add(new ExitBorders(GameConstants.screenSizeX, 0,
				10f, (float) GameConstants.screenSizeY));
		//top
		GameConstants.borders.add(new ExitBorders(0, 0,
				(float) GameConstants.screenSizeX, (float) 10f));
		//left
		GameConstants.borders.add(new ExitBorders(0, 0,
				10f, (float) GameConstants.screenSizeY));

		
		
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		
	}
	
	//utility method (Draw line, changes color based on magnitude)
	
	
	
	/**
	 * Draws line from original click to current click, colors redness based on magnitude. 
	 * Calcultes magnitude as well for future use.
	 * @param canvas
	 */
	void drawLine(Canvas canvas) {
		float totalY = (float)Math.pow((double)this.currentY - (double)this.prevY, 2);
		float totalX = (float)Math.pow((double)this.currentX - (double)this.prevX, 2);
		this.MAGNITUDE =(float) Math.sqrt((double)totalX + (double)totalY); 
		
		
		Paint paint = new Paint();
		float ratio = GameConstants.getDiagonalRatio(this.MAGNITUDE);
		
		paint.setColor(android.graphics.Color.argb(255, (int)(ratio * 255f), 10, (int)(255f -(ratio * 255f))));
		paint.setStrokeWidth(70);
		canvas.drawLine(this.prevX, this.prevY, this.currentX, this.currentY, paint);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(GameManager.RemainingSwipes() == 0)
		{  
			return true;
		} 
		
		if(this.PAUSED==1) //go to true pause
			this.PAUSED++;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// tries to find what we're touching...
			
			if(this.PAUSED != 0 || GameManager.getAccurateGameClock() != 1f)
			{//something went horribly wrong, attempt to fix it
				this.PAUSED = 0;
				GameManager.gameClock = 1f;
				return true;
			}
			else
			{
				this.PAUSED = 1;
				GameManager.PAUSE_SPAWN_CLOCK = 1;
				GameManager.addToGameClock(-1f * GameManager.GameDifficulty);
				this.prevX = event.getX();
				this.prevY = event.getY();
			}
			
			
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// we are now moving, counts as a swipe.

			
			this.currentX = event.getX();
			this.currentY = event.getY();
			
			//move gesture
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			
			//release gesture
			if(GameManager.PAUSE_SPAWN_CLOCK != 0)
			{
				GameManager.createLine(); //punishing line for taking a move
				if(GameManager.GameDifficulty <= 0.7f)
					{
					for(int a=1; a < GameManager.PAUSE_SPAWN_CLOCK; a++)
						GameManager.createLine(); //add penalty lines for each spawn during a "pause"
					}
					GameManager.PAUSE_SPAWN_CLOCK =0;
								

			}
			this.PAUSED= 0;
			if(this.MAGNITUDE >= 2f)
			{
				GameManager.SWIPE_COUNT++; //count the swipe!
				GameManager.gameClock =1f;
				FloatPoint tempPoint =
				movementUtility.calculateSpeedVector(this.MAGNITUDE, this.currentX-this.prevX, this.currentY-this.prevY);
				playerOne.setVelocityX(tempPoint.X);
				playerOne.setVelocityY(tempPoint.Y);
			}
			
			
			
		}
		return true;
	}
	
	private void decrementor()
	{
		if(this.Blink_Timer > 0)
			this.Blink_Timer -=1;
		else
			this.Blink_Timer = 0;
	}

	public void render(Canvas canvas) {
		if(GameManager.ThreadRunning== false || GameManager.PID != this.taskPID)
			return;
		canvas.drawColor(Color.CYAN);

		//draws the vector line!
		if(this.PAUSED == 2)
			this.drawLine(canvas);
		
		if(this.Blink_Timer % 10 == 0)
			{playerOne.render(canvas);
			
			}

		this.decrementor();
		for(int a=0; a < GameConstants.floatingStructures.size(); a++)
		{
			GameConstants.floatingStructures.get(a).render(canvas);//renders each item to the canvas
		}
		
		Paint textPaint =  new Paint();
		textPaint.setColor(Color.RED);
		textPaint.setTextSize(100);
		textPaint.setStrokeWidth(30);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setStyle(Style.FILL);
		canvas.drawText("" + GameManager.PLAYER_POINTS, GameConstants.screenSizeX-((GameManager.PLAYER_POINTS/9999 >= 1)? 
				200 : 150), 100,textPaint);
		
		textPaint.setTextSize(60);
		textPaint.setStrokeWidth(20);
		if(GameManager.SWIPE_LIMIT == -1)
		canvas.drawText( ""+ GameManager.SWIPE_COUNT, 40, 100,textPaint);
		else
		canvas.drawText( GameManager.RemainingSwipes()  + " / " +  GameManager.SWIPE_LIMIT, 100, 70,textPaint);
		
		
		/// LIVES
		int iterationVal = GameManager.getPlayerLives() + GameManager.getPlayerArmor() ;
		for(int a =0; a < iterationVal; a++)
		{
			Rect temp = new Rect(GameConstants.screenSizeX- ((a+1) *GameConstants.LifeSize),
					GameConstants.screenSizeY- GameConstants.LifeSize,
					GameConstants.screenSizeX- ((a) *GameConstants.LifeSize), GameConstants.screenSizeY);
			
			if(a >= GameManager.getPlayerLives())
				canvas.drawBitmap(GameManager.armorPicture, null,  temp, new Paint());
			else
				canvas.drawBitmap(GameManager.lifePicture, null, temp, new Paint());
		}
		
		//drawing projectiles...
		for(int a=0; a < GameConstants.PowerUps.size(); a++)
			GameConstants.PowerUps.get(a).render(canvas);
	}

	public void stopThreads()
	{
		thread.setRunning(false); //ends game logic run
		GameManager.ThreadRunning = false; //ends the spawning of lines
	}
	
	public void finish()
	{
		stopThreads();
		//now that flags are set, we need to "clean" after a short delay
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
	
				((GameActivity)getContext()).finish();
			}
		}, 100);
		
		

	}
	
	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
	
		
			playerOne.update();
		// Update 
			
		for(int a=0; a < GameConstants.floatingStructures.size(); a++)
		{
			if(GameConstants.floatingStructures.size() == a)break;
			
			if(GameConstants.floatingStructures.get(a).outOfBounds())
			{
				
				int exitVal= -1;
				for(int iterator=0; iterator < GameConstants.floatingStructures.size(); iterator++)
				{
					exitVal = iterator;
					if(GameConstants.floatingStructures.get(iterator).equals(GameConstants.floatingStructures.get(a)))
						break;
					if(iterator +1 == GameConstants.floatingStructures.size())
						exitVal = -1;
				}
				if(exitVal != -1)
				{
				GameConstants.floatingStructures.remove(exitVal); //the current object is out of bounds... must be removed
				continue;
				}
			}			
			
				GameConstants.floatingStructures.get(a).update();//updates all objects
			if(GameConstants.floatingStructures.get(a).collidedWith(this.playerOne))
			{
				GameConstants.floatingStructures.get(a).executeFunctions(playerOne, this);
			}
			
		}//endof for loop
		for(int a=0; a < GameConstants.borders.size(); a++)
		{
			if(playerOne.collidedWith(GameConstants.borders.get(a)))
				GameConstants.borders.get(a).executeFunctions(playerOne, this);
		}
		
		int exitVal = -1;
		int removeVal = -1;
		for(int a=0; a < GameConstants.PowerUps.size(); a++)
		{
			if(playerOne.collidedWith(GameConstants.PowerUps.get(a)))
				{GameConstants.PowerUps.get(a).executeFunctions(playerOne, this);
				exitVal = a;
				}
			else
				GameConstants.PowerUps.get(a).update();
		
			if(GameConstants.PowerUps.get(a).outOfBounds())
				removeVal = a;
		}
		if(exitVal != -1) GameConstants.PowerUps.remove(exitVal);
		if(removeVal != -1 && exitVal == -1) GameConstants.PowerUps.remove(removeVal);
	}

}