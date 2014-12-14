package com.example.flung;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
		
		
		final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	
            	TextView viewer = (TextView) findViewById(R.id.helloText);
            	viewer.setText("I love you! :) Hope your nap was good");
            }
        });
		
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
