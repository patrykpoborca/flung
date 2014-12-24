package com.flung.patryk.GameActivity;

import com.flung.patryk.R;
import com.flung.patryk.Game.*;
import com.flung.patryk.GameOver_Activity.GameOverActivity;
import com.flung.patryk.Game_Utility.GameConstants;
import com.flung.patryk.MetaStates.FlungValues;
import com.flung.patryk.SettingsActivity.SettingsActivity;
import com.example.flung.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	
	private MainGamePanel PANEL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		GameConstants.screenSizeX = size.x;
		GameConstants.screenSizeY = size.y;
		double tempx = Math.pow((double)size.x, 2);
		double tempy = Math.pow((double)size.y, 2);
		GameConstants.screenSizeDiagonal = (float)Math.sqrt(tempx+tempy);
	    // Set window fullscreen and remove title bar, and force landscape orientation
	   
	}
	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub

		super.onRestart();
	}


	@Override
	public void onBackPressed() {
		this.PANEL.stopThreads();
		super.onBackPressed();
	}


	@Override
	protected void onResume() {
				
		GameManager.PID++;
		this.PANEL = new MainGamePanel(this, GameManager.PID);
		setContentView(this.PANEL);
		 
		 
		 
		super.onResume();
	}
	


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
		super.onStop();
	}
	@Override
	protected void onPause() {
		Log.d("WASI", "CALLED");
		super.onPause();
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}


	/**
	 * Used to send you to game over, which then will cause this activity to finish from the activity result
	 */
	

}
