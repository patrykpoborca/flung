package com.flung.MainActivity;

import com.example.flung.R;
import com.flung.GameActivity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import Game.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
	final RelativeLayout layout = (RelativeLayout)findViewById(R.id.superView);
	layout.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent i = new Intent(getApplicationContext(), GameActivity.class);
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
	/*
	 @Override
	 protected void onStart()
	 {
		 
	 }

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

     @Override
     protected void onStop()
     {}
     @Override
     protected void onDestroy(){}*/
}
