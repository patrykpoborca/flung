package Game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.flung.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import utility.*;


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
	//private Droid droid;
	private float prevX;
	private float prevY;
	private float currentX;
	private float currentY;
	
	private Bitmap lifePicture;
	
	//end of movement related stuff
	public int Blink_Timer = 0;
	public FlungObject playerOne;
	
	private float MAGNITUDE;

	public MainGamePanel(Context context) {
		super(context);
		
		this.PAUSED = 0;
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		setupGame();
		//
				// create the game loop thread
		thread = new GameThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		GameManager.startTimer_Line();
		GameManager.startPointTally();
	}
	
	
	private void setupGame()
	{
		playerOne = new FlungObject(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 50, 50);
		//playerOne.setConstantForceY(GameConstants.gravity);
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

		this.lifePicture = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
		
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
		Log.d(TAG, "Surface is being destroyed");
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
		Log.d(TAG, "Thread was shut down cleanly");
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
		if(this.PAUSED==1) //go to true pause
			this.PAUSED++;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// tries to find what we're touching...
			
			if(this.PAUSED != 0 || GameManager.gameClock != 1f)
			{//something went horribly wrong, attempt to fix it
				this.PAUSED = 0;
				GameManager.gameClock = 1f;
				return true;
			}
			else
			{
				this.PAUSED = 1;
				GameManager.PAUSE_SPAWN_CLOCK = 1;
				GameManager.gameClock -= GameManager.GameDifficulty;
				this.prevX = event.getX();
				this.prevY = event.getY();
			}
			
			
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			this.currentX = event.getX();
			this.currentY = event.getY();
			
			//move gesture
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			//release gesture
			if(GameManager.PAUSE_SPAWN_CLOCK != 0)
			{
				GameManager.createLine(); //punishing line for taking a move
				if(GameManager.PAUSE_SPAWN_CLOCK == 2)
					{
					GameManager.PAUSE_SPAWN_CLOCK =0;
					}				

			}
			this.PAUSED= 0;
			if(this.MAGNITUDE >= 2f)
			{
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
		if(GameManager.ThreadRunning== false)
			return;
		canvas.drawColor(Color.BLACK);

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
		
		
		/// LIVES
		for(int a =0; a < GameManager.getPlayerLives(); a++)
		{
			Rect temp = new Rect(GameConstants.screenSizeX- ((a+1) *GameConstants.LifeSize),
					GameConstants.screenSizeY- GameConstants.LifeSize,
					GameConstants.screenSizeX- ((a) *GameConstants.LifeSize), GameConstants.screenSizeY);
			
			canvas.drawBitmap(this.lifePicture, null, temp, new Paint());
		}
	}

	public void finish()
	{
		
		thread.setRunning(false); //ends game logic run
		GameManager.ThreadRunning = false; //ends the spawning of lines
		
		//now that flags are set, we need to "clean" after a short delay
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				GameConstants.floatingStructures.clear();
				GameConstants.borders.clear();
				((Activity)getContext()).finish();
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
			//Log.d("COUNT:", ""+ GameConstants.floatingStructures.size());
		for(int a=0; a < GameConstants.floatingStructures.size(); a++)
		{
			
			if(GameConstants.floatingStructures.get(a).outOfBounds())
			{
				//Log.d("Finding SELF", "Working...  " + GameConstants.floatingStructures.size());
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
		
	}

}