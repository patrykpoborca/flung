package Game;

import java.util.ArrayList;

import com.example.flung.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	
	
	//end of movement related stuff
	
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
			this.PAUSED = 1;
			this.prevX = event.getX();
			this.prevY = event.getY();
			
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			this.currentX = event.getX();
			this.currentY = event.getY();
			//move gesture
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			//release gesture
			this.PAUSED= 0;
			if(this.MAGNITUDE >= 2f)
			{
				FloatPoint tempPoint =
				movementUtility.calculateSpeedVector(this.MAGNITUDE, this.currentX-this.prevX, this.currentY-this.prevY);
				playerOne.setVelocityX(tempPoint.X);
				playerOne.setVelocityY(tempPoint.Y);
			}
			
			
			
		}
		return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		if(this.PAUSED == 2)
			this.drawLine(canvas);
		playerOne.render(canvas);
		for(int a=0; a < GameConstants.floatingStructures.size(); a++)
		{
			GameConstants.floatingStructures.get(a).render(canvas);//renders each item to the canvas
		}
	}

	public void finish()
	{
		thread.setRunning(false);
		((Activity)getContext()).finish();
	}
	
	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
	
		if(this.PAUSED==0)
			playerOne.update();
		// Update 
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
				GameConstants.floatingStructures.remove(exitVal);
				Log.d("REMOVING SELF", "IT WORKED? " + GameConstants.floatingStructures.size());
				continue;
				}
			}
			
			
			if(this.PAUSED==0)
				GameConstants.floatingStructures.get(a).update();//updates all objects
			if(GameConstants.floatingStructures.get(a).collidedWith(this.playerOne))
			{
				Log.d("COLLISION", "YELLOW COLLISION!");
			}
			
		}
		for(int a=0; a < GameConstants.borders.size(); a++)
		{
			if(playerOne.collidedWith(GameConstants.borders.get(a)))
				GameConstants.borders.get(a).executeFunctions(playerOne, this);
		}
		
	}

}