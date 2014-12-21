package com.flung.MainActivity;

import utility.GameConstants;
import utility.MetaPlayerData;

import com.example.flung.R;
import com.example.flung.R.drawable;
import com.flung.GameActivity.GameActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import Game.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;




public class MainActivity extends Activity implements
ConnectionCallbacks, OnConnectionFailedListener, OnClickListener  {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MetaPlayerData.LoadPlayerData(this); //load preconfig settings
        setStartScreen();
        final MainActivity cc = this;
		findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("WTF", "bleh");
				cc.onClick(v);
			}
		});
	
	}
	
	
	
	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;
	
	private GoogleApiClient mGoogleApiClient;
	
	private boolean mIntentInProgress= false;
	
	boolean mExplicitSignOut = false;
	boolean mInSignInFlow = false; // set to true when you're in the middle of the
	                               // sign in flow, to know you should not attempt
	                               // to connect in onStart()

	
	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
	  if (mConnectionResult.hasResolution()) {
	    try {
	    	Log.d("Try", "ResolveSignin");
	      mIntentInProgress = true;
	      startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
	           101, null, 0, 0, 0);
	      
	    } catch (SendIntentException e) {
	      // The intent was canceled before it was sent.  Return to the default
	      // state and attempt to connect to get an updated ConnectionResult.
	    	Log.d("Catch", "ResolveSignin");
	      mIntentInProgress = false;
	      mGoogleApiClient.connect();
	    }
	  }
	}


	
	private void setStartScreen()
	{
		
		
		final MainActivity CONTEXT = this;
		
		findViewById(R.id.sign_out_button).setVisibility(View.GONE);
		
		setContentView(R.layout.activity_main);
		final Button newGameButton = (Button)findViewById(R.id.New_Game_Button);
		newGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), GameActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivityForResult(i, 9000); // 9000 is game signiture (OVER 9000!!!!)
				 
			}
		});
		
		final Button settingsButton = (Button)findViewById(R.id.Settings_Button);
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setSettings();				 
			}
		});
		
		
		final Button leaderBoardsButton = (Button)findViewById(R.id.LeaderBoard_Button);

		if( GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS)
		{
		
		      mGoogleApiClient = new GoogleApiClient.Builder(this)
	            .addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .addApi(Plus.API)
	            .addScope(Plus.SCOPE_PLUS_LOGIN)
	            .build();
			
			leaderBoardsButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
							CONTEXT.getResources().getString(R.string.leaderboard_flung_top_scores)), 10);
				}
			});
			
		}
		else
		{
			leaderBoardsButton.setText("No Google Play Found");
		}
		
	}
	
	
	
	
	private void ChangeTextNumber()
	{
		final TextView difficultyText = (TextView)findViewById(R.id.Difficulty_Text);
		difficultyText.setText(((int)(10- (GameManager.SELF.DifficultyPreference * 10))) + "");
	}
	
	private void setSettings()
	{
		
		setContentView(R.layout.activity_settings);
		final MainActivity context = this;

		
		// end of medium button
		GameManager.SELF.DifficultyPreference = GameManager.GameDifficulty;
		ChangeTextNumber();
		final Button incrementButton = (Button)findViewById(R.id.Increment_Button);
		incrementButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GameManager.SELF.DifficultyPreference <= 0) return; //has to be flipped, so that decrement increases the number which subtracts from 1
				GameManager.SELF.DifficultyPreference =   GameManager.SELF.DifficultyPreference - 0.1f;
				Log.d("Df++++", GameManager.SELF.DifficultyPreference + "");
				ChangeTextNumber();
			}
		});
		final Button decrementButton = (Button)findViewById(R.id.Decrement_Button);
		decrementButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GameManager.SELF.DifficultyPreference >= 1f) return;
				GameManager.SELF.DifficultyPreference += 0.1f;
				Log.d("Df", GameManager.SELF.DifficultyPreference + "");
				ChangeTextNumber();
			}
		});
		
		//GRAVITY USE
		final ImageButton gravityButton = (ImageButton)findViewById(R.id.Gravity_Button);
		GameManager.SELF.UseGravity = 0 == GameManager.usingGravity;
		if(!GameManager.SELF.UseGravity)
		{
		GameManager.SELF.UseGravity = false;
		gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.gravity)); 
		}
	else
		{
		GameManager.SELF.UseGravity = true;
		gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.selected_gravity)); 
		}
		
		gravityButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GameManager.SELF.UseGravity = !GameManager.SELF.UseGravity;
				if(!GameManager.SELF.UseGravity)
					{
					gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.gravity)); 
					}
				else
					{
					gravityButton.setImageDrawable(context.getResources().getDrawable(drawable.selected_gravity)); 

					}
			}
		});
		//CLICK CHALLENGE
		
		GameManager.SELF.ClickChallenge = -1 == GameManager.SWIPE_LIMIT;
		final ImageButton clickButton = (ImageButton)findViewById(R.id.Clicks_Button);
		if(!GameManager.SELF.ClickChallenge)
		{
			
			clickButton.setImageDrawable(context.getResources().getDrawable(drawable.clicks)); 
		}
	else
		{

		clickButton.setImageDrawable(context.getResources().getDrawable(drawable.selected_clicks)); 
		}
		
		clickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GameManager.SELF.ClickChallenge = !GameManager.SELF.ClickChallenge;
				if(!GameManager.SELF.ClickChallenge)
				{
					clickButton.setImageDrawable(context.getResources().getDrawable(drawable.clicks)); 
					
				}
			else
				{
				clickButton.setImageDrawable(context.getResources().getDrawable(drawable.selected_clicks)); 
				
				}
			}
		});
		
		
		
		//save settings
		final Button saveButton = (Button)findViewById(R.id.Save_settings_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playerToSettings();
				context.setStartScreen();
			}
		});
		
		//exit back to main
		final Button exitButton = (Button)findViewById(R.id.Exit_settings_button);
		exitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.setStartScreen();
			}
		});
	}
	
	/**
	 * Used to update all the settings of the Game based upon player's preferences. Called on save. WIll also save to local file
	 */
	public void playerToSettings()
	{
		GameManager.GameDifficulty = GameManager.SELF.DifficultyPreference;
		GameManager.SWIPE_LIMIT = (GameManager.SELF.ClickChallenge) ? GameManager.SWIPE_CONSTANT : -1;
		GameManager.usingGravity = (GameManager.SELF.UseGravity) ? GameConstants.gravity : 0;
		MetaPlayerData.SavePlayerData(this);		
	}
	
	

	 
		@Override
	protected void onStart() {

		super.onStart();
	    if (!mInSignInFlow && !mExplicitSignOut) {
	        // auto sign in
	        mGoogleApiClient.connect();
	    }
	}


		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			
			if(resultCode == 9000)
			{
				setContentView(R.layout.activity_game_over); //game was ended.
				printResults();
			}
			else
			{
				//this.setStartScreen();
			}
			
			
			
			super.onActivityResult(requestCode, resultCode, data);
		}
		
		private void printResults()
		{
			TextView summary = (TextView)findViewById( R.id.PostGame_Text);
			
			String text = "Lives remaining= " + GameManager.getPlayerLives();
			text += "\nSwipes Used = " + GameManager.SWIPE_COUNT + " / " +((GameManager.SWIPE_LIMIT == -1) ? "unlimited" : GameManager.SWIPE_LIMIT); 
			text+= "\n Points = " + GameManager.PLAYER_POINTS;
			summary.setText(text);
			
			ImageButton OK = (ImageButton)this.findViewById(R.id.OK_Button);
			OK.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					
					setStartScreen();
				}
			});
		}

		@Override
		public void onConnectionFailed(ConnectionResult result) {
			  if (!mIntentInProgress) {
				    // Store the ConnectionResult so that we can use it later when the user clicks
				    // 'sign-in'.
				    mConnectionResult = result;

				    if (mSignInClicked) {
				      // The user has already clicked 'sign-in' so we attempt to resolve all
				      // errors until the user is signed in, or they cancel.
				      resolveSignInError();
				    }
				  }
			
		}

		@Override
		public void onConnected(Bundle connectionHint) {
		    // show sign-out button, hide the sign-in button
		    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
		    findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);

		    // (your code here: update UI, enable functionality that depends on sign in, etc)
		}

		@Override
		public void onConnectionSuspended(int arg0) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Log.d("Called", "hihi ="+ R.id.sign_in_button + " == " + view.getId() + " == " + R.id.sign_out_button);
			 if (view.getId() == R.id.sign_in_button) {
			        // start the asynchronous sign in flow
			        mSignInClicked = true;
			        mGoogleApiClient.connect();
			        
			        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			        	   // signed in. Show the "sign out" button and explanation.
			        	   // ...
			        	Log.d("SIGNED IN!", "DFDFD");
			        	}
			        else
			        	Log.d("NOTSIGNE", "fffff");
			        
			    }
			    else if (view.getId() == R.id.sign_out_button) {
			        // sign out.
			        mSignInClicked = false;
			        Games.signOut(mGoogleApiClient);

			        // show sign-in button, hide the sign-out button
			        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
			        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
			        
			        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			            Games.signOut(mGoogleApiClient);
			            mGoogleApiClient.disconnect();
			        }
			    }


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
