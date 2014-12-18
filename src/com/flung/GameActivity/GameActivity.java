package com.flung.GameActivity;

import utility.GameConstants;
import utility.MetaGameData;

import com.example.flung.R;
import com.example.flung.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
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
import Game.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        MetaGameData.GameQuality = 0.2f; //temp							
        
        Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		GameConstants.screenSizeX = size.x;
		GameConstants.screenSizeY = size.y;
		double tempx = Math.pow((double)size.x, 2);
		double tempy = Math.pow((double)size.y, 2);
		GameConstants.screenSizeDiagonal = (float)Math.sqrt(tempx+tempy);
		
        setContentView(new MainGamePanel(this));

	    // Set window fullscreen and remove title bar, and force landscape orientation
	   
	}

}
