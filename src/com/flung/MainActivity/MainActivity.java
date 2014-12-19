package com.flung.MainActivity;

import utility.MetaPlayerData;

import com.example.flung.R;
import com.flung.GameActivity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import Game.*;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
	final Button newGameButton = (Button)findViewById(R.id.NewGame_Button);
	newGameButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), GameActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivityForResult(i, 9000); // 9000 is game signiture (OVER 9000!!!!)
			 
		}
	});
	
		
//		final Button button = (Button) findViewById(R.id.button1);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//            	
//            	TextView viewer = (TextView) findViewById(R.id.helloText);
//            	viewer.setText("I love you! :) Hope your nap was good");
//            }
//        });
//		
	}

	 
		@Override
	protected void onStart() {
			GameManager.SELF.UserName = "igotthis321";
			GameManager.SELF.DifficultyPreference = 1f;
			GameManager.SELF.highScore = 33333;
			 MetaPlayerData.SavePlayerData(getApplicationContext());
			 GameManager.SELF.UserName = "zzzz";
				GameManager.SELF.DifficultyPreference = 1f;
				GameManager.SELF.highScore = 0000;
			 MetaPlayerData.LoadPlayerData(getApplicationContext());
			 
			
		super.onStart();
	}


	/*
	 @Override
     protected void onRestart()
     {
    	 
     }

     @Override
     protected void onResume()
     {
    	 
     }

     @Override
     protected void onPause()
     {
    	 
     }
*/


     //@Override
     //protected void onDestroy(){}
}
