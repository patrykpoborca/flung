package com.flung.patryk.GameOver_Activity;

import com.flung.patryk.R;
import com.flung.patryk.Game.GameManager;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameOverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    if(GameManager.newHighScore != 0)
	    	setContentView(R.layout.activity_game_over_high_score);
	    else
	    	setContentView(R.layout.activity_game_over);

	    this.printStats();
		this.setUpLayout();
		
		
		Intent sender = new Intent(this, GameOverActivity.class);
		sender.putExtra("GameOver", true);
		this.setIntent(sender);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void setUpLayout()
	{
		final GameOverActivity this_Activity = this;
		ImageButton OK = (ImageButton)this.findViewById(R.id.OK_Button);
		OK.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//* Leaderboard stuff here
				
				//////
				this_Activity.finish();				
			}
		});
	}
	
	private void printStats()
	{
		TextView hsBox = (TextView)this.findViewById(R.id.High_Score_text);
		hsBox.setText(""+ GameManager.PLAYER_POINTS);
		
		TextView summary = (TextView)findViewById( R.id.PostGame_Text);
		Log.d("BUTNOT", "dfd");
		String text = "Lives remaining= " + GameManager.getPlayerLives();
		text += "\nSwipes Used = " + GameManager.SWIPE_COUNT + " / " +((GameManager.SWIPE_LIMIT == -1) ? "unlimited" : GameManager.SWIPE_LIMIT); 
		summary.setText(text);
		
		
	}

}