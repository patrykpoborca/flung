package com.flung.patryk.SettingsActivity;

import com.flung.patryk.R;
import com.flung.patryk.Game.GameManager;
import com.flung.patryk.Game_Utility.GameConstants;
import com.flung.patryk.Game_Utility.MetaPlayerData;
import com.flung.patryk.MainActivity.MainActivity;
import com.flung.patryk.MetaStates.FlungValues;
import com.flung.patryk.R.drawable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setSettings();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	
	
	
	  
	  private void ChangeTextNumber()
		{
		  //makes sure that we don't have float precision nonsense. we keep them close to their int values
		  GameManager.SELF.DifficultyPreference = ((float) Math.round(GameManager.SELF.DifficultyPreference * 10))/10f;
			final TextView difficultyText = (TextView)findViewById(R.id.Difficulty_Text);
			difficultyText.setText((Math.round((10- (GameManager.SELF.DifficultyPreference * 10)))) + "");
		}
	
	private void setSettings()
	{
		
		GameManager.CurrentView = FlungValues.CALL_SETTINGS; //currently on settings...
		
		setContentView(R.layout.activity_settings);
		final SettingsActivity context = this;

		
		// end of medium button
		GameManager.SELF.DifficultyPreference = GameManager.GameDifficulty;
		ChangeTextNumber();
		final Button incrementButton = (Button)findViewById(R.id.Increment_Button);
		incrementButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GameManager.SELF.DifficultyPreference <= 0) return; //has to be flipped, so that decrement increases the number which subtracts from 1
				GameManager.SELF.DifficultyPreference =   GameManager.SELF.DifficultyPreference - 0.1f;
				
				ChangeTextNumber();
			}
		});
		final Button decrementButton = (Button)findViewById(R.id.Decrement_Button);
		decrementButton.setOnClickListener(new View.OnClickListener() { 
			
			@Override
			public void onClick(View v) {
				if(GameManager.SELF.DifficultyPreference >= 1f) return;
				GameManager.SELF.DifficultyPreference += 0.1f;
				
				ChangeTextNumber();
			}
		});
		
		//GRAVITY USE
		final ImageButton gravityButton = (ImageButton)findViewById(R.id.onoffgravity);
		GameManager.SELF.UseGravity = 0 != GameManager.usingGravity;
		Log.d("GRAVITY: ", "GameMan: " + GameManager.SELF.UseGravity + "  GameValue: " + GameManager.usingGravity);
		if(!GameManager.SELF.UseGravity)
		{
		gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonoff)); 
		}
	else
		{
		gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonon)); 
		}
		
		gravityButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GameManager.SELF.UseGravity = !GameManager.SELF.UseGravity;
				if(!GameManager.SELF.UseGravity)
					{
					gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonoff)); 
					}
				else
					{
					gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonon)); 

					}
			}
		});
		//CLICK CHALLENGE
		
		GameManager.SELF.ClickChallenge = -1 != GameManager.SWIPE_LIMIT;
		final ImageButton clickButton = (ImageButton)findViewById(R.id.onoffclick);
		if(!GameManager.SELF.ClickChallenge)
		{
			
			clickButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonoff)); 
		}
	else
		{

		clickButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonon)); 
		}
		
		clickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GameManager.SELF.ClickChallenge = !GameManager.SELF.ClickChallenge;
				if(!GameManager.SELF.ClickChallenge)
				{
					clickButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonoff)); 
					
				}
			else
				{
				clickButton.setImageDrawable(context.getResources().getDrawable(drawable.buttonon)); 
				
				}
			}
		});
		
		
		
		//save settings
		final ImageButton saveButton = (ImageButton)findViewById(R.id.Save_settings_button);
		final Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
			
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MetaPlayerData.SavePlayerData(context);	
				MetaPlayerData.playerToSettings();
				i.putExtra("Saved", true);
				context.setResult(Activity.RESULT_OK, i);
				context.finish();
			}
		});
		
		//exit back to main
//		final Button exitButton = (Button)findViewById(R.id.Exit_settings_button);
//		exitButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				i.putExtra("Saved", false);
//				context.finish();
//			}
//		});
	}

	@Override
	public void onBackPressed() {
		final Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		MetaPlayerData.SavePlayerData(this);	
		MetaPlayerData.playerToSettings();
		i.putExtra("Saved", true);
		this.setResult(Activity.RESULT_OK, i);
		this.finish();
		super.onBackPressed();
	}
	
	
	

}
