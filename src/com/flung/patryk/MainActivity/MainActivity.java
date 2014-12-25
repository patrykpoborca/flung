/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flung.patryk.MainActivity;


import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.flung.patryk.R;
import com.flung.patryk.Game.GameManager;
import com.flung.patryk.GameActivity.GameActivity;
import com.flung.patryk.GameOver_Activity.*;
import com.flung.patryk.Game_Utility.GameConstants;
import com.flung.patryk.Game_Utility.MetaPlayerData;
import com.flung.patryk.MetaStates.FlungValues;
import com.flung.patryk.R.drawable;
import com.flung.patryk.SettingsActivity.SettingsActivity;
import com.flung.patryk.libraries.BaseGameActivity;
import com.flung.patryk.libraries.BaseGameUtils;
import com.flung.patryk.libraries.GameHelper;
import com.flung.patryk.libraries.GameHelper.GameHelperListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver.PendingResult;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Android Google+ Quickstart activity.
 *
 * Demonstrates Google+ Sign-In and usage of the Google+ APIs to retrieve a
 * users profile information.
 */
public class MainActivity extends BaseGameActivity implements
  GameHelperListener, View.OnClickListener {



  private SignInButton mSignInButton;
  private Button mSignOutButton;

  private TextView mStatus;
  
  private boolean DESTROYED = false;
  
  private MainFragment mainFragment; //facebook's fragment
  private Button TEST;
  
  
  private  UiLifecycleHelper uiHelper;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    
    this.setStartScreen();
    
    
    
    this.mHelper = new GameHelper(this, GameHelper.CLIENT_GAMES | GameHelper.CLIENT_PLUS);
    mHelper.setMaxAutoSignInAttempts(3);
    mHelper.setup(this);
    
		//Google API STUFF
    	//Google API STUFF
    	//Google API STUFF
    mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
    mSignOutButton = (Button) findViewById(R.id.sign_out_button);
   // mRevokeButton = (Button) findViewById(R.id.revoke_access_button);
    mStatus = (TextView) findViewById(R.id.sign_in_status);
    this.TEST = (Button)findViewById(R.id.TEST);
    this.TEST.setOnClickListener(this);
    mSignInButton.setOnClickListener(this);
    mSignOutButton.setOnClickListener(this);
  
    MetaPlayerData.LoadPlayerData(this);
    
    
    //facebook loading
    
    if (savedInstanceState == null) {
        // Add the fragment on initial activity setup
        mainFragment = new MainFragment();
        getSupportFragmentManager()
        .beginTransaction()
        .add(android.R.id.content, mainFragment)
        .commit();
    } else { 
        // Or set the fragment from restored state info
        mainFragment = (MainFragment) getSupportFragmentManager()
        .findFragmentById(android.R.id.content);
    }
     
    
		this.uiHelper = new UiLifecycleHelper(this, null);
		this.uiHelper.onCreate(savedInstanceState);
    
  }
  
  


  @Override
  protected void onStart() {
	  super.onStart();
	  
		 
	  
	 
  }

  @Override
  protected void onStop() {
    super.onStop();
  } 
  


@Override
protected void onPostResume() {
	 
	super.onPostResume();
}


@Override
protected void onResumeFragments() {
	 
	super.onResumeFragments();
}


@Override
  public void onResume() {
      super.onResume();  
      uiHelper.onResume();
  }
  
@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    uiHelper.onSaveInstanceState(outState);
}
  

  @Override
protected void onRestart() {
	
	super.onRestart();
}

  @Override
  public void onPause() {
      super.onPause();
      uiHelper.onPause();
  }
  
  @Override
  public void onDestroy() {
      super.onDestroy();
      uiHelper.onDestroy();
  }

@Override
  public void onClick(View v) {
	  
	  Intent i;
    if (!this.mHelper.isConnecting()) {
      // We only process button clicks when GoogleApiClient is not transitioning
      // between connected and not connected.
      switch (v.getId()) {
          case R.id.sign_in_button:
        	  if(mHelper == null)
        		  
            mHelper.beginUserInitiatedSignIn();
            break;

          case R.id.LeaderBoard_Button:

				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(this.mHelper.getApiClient(),
						this.getResources().getString(R.string.leaderboard_flung_top_scores)), 10);        	  
        	  break;
        	  
          case R.id.Settings_Button:
        	  	i = new Intent(getApplicationContext(), SettingsActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				this.startActivity(i);
				
				break;
				
          case R.id.New_Game_Button:
				i = new Intent(getApplicationContext(), GameActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivityForResult(i, FlungValues.CALL_GAME.ordinal()); // 9000 is game signiture (OVER 9000!!!!)
				break;
          case R.id.TEST:
        	  Log.d("TOUCH", "MABODY");
        	  FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
              .setLink("https://developers.facebook.com/android")
              .build();
        	  uiHelper.trackPendingDialogCall(shareDialog.present());
        	  break;
      }
    }
  }

  

  
  //HELPERS
  ///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  
  private void setStartScreen()
	{
		GameManager.CurrentView = FlungValues.CALL_MAIN; // tell everyone else that we're currently on main
	  	setContentView(R.layout.activity_main);
	  	
	  	this.findViewById(R.id.New_Game_Button).setOnClickListener(this);
	  	this.findViewById(R.id.Settings_Button).setOnClickListener(this);
	  	this.findViewById(R.id.LeaderBoard_Button).setOnClickListener(this);
		
	}

  


	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		this.mStatus.setText("Failed Login");
		CleanUp_LoggedOut();
	}
	
	private void CleanUp_LoggedOut()
	{
		
		mSignInButton.setVisibility(View.VISIBLE);
	    mSignInButton.setEnabled(true);
	    mSignOutButton.setVisibility(View.GONE);
	    mSignOutButton.setEnabled(false);
	}

	
	private void CleanUp_LoggedIn(){
		
		   mSignInButton.setVisibility(View.GONE);
		    mSignInButton.setEnabled(false);
		    mSignOutButton.setVisibility(View.VISIBLE);
		    mSignOutButton.setEnabled(true);
	}

	@Override
	public void onSignInSucceeded() {

		this.mStatus.setText("SIGNED IN");
		  CleanUp_LoggedIn();
		  
		  com.google.android.gms.common.api.PendingResult<LoadPlayerScoreResult> holdIt = 
				  Games.Leaderboards.loadCurrentPlayerLeaderboardScore(this.mHelper.getApiClient(), this.getString(
					R.string.leaderboard_flung_top_scores), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC);
			
		  holdIt.setResultCallback(new ResultCallback<LoadPlayerScoreResult>()
					{
						public void onResult(LoadPlayerScoreResult returned)
						{
							if(returned != null && returned.getStatus().isSuccess())
							{
								Log.d("WOrking?!@", "meh " + returned.getScore());
								int temporaryValue = (returned.getScore() != null) ? (int)returned.getScore().getRawScore() : 0;
								if(temporaryValue > GameManager.SELF.highScore) GameManager.SELF.highScore = temporaryValue;
							} 
						}
					});
		  
			
		if(GameManager.newHighScore !=0) //then we update highscore.
		{
			Games.Leaderboards.submitScore(this.mHelper.getApiClient(), this.getString(R.string.leaderboard_flung_top_scores), GameManager.newHighScore);
			Log.d("UPdatingScore", "TO THE WEB");
			GameManager.newHighScore = 0;
		}
		
	}
	
	private void showScore()
	{
		//if player beat old score?!
		if(GameManager.PLAYER_POINTS > GameManager.SELF.highScore)
		{
			GameManager.newHighScore = GameManager.PLAYER_POINTS;
		}
		Intent i = new Intent(getApplicationContext(), GameOverActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		this.startActivityForResult(i, FlungValues.CALL_PostGame.ordinal());
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == FlungValues.CALL_GAME.ordinal())
		{
			//clearing!
			GameConstants.floatingStructures.clear();
			GameConstants.borders.clear();
			showScore();
			
		}
		
		uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
		
	 super.onActivityResult(requestCode, resultCode, data);
	}
	


	
	
  
}