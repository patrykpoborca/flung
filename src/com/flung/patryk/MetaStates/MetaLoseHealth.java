package com.flung.patryk.MetaStates;

import com.flung.patryk.Game.GameManager;
import com.flung.patryk.Game.GameObject;
import com.flung.patryk.Game.MainGamePanel;

import android.util.Log;


public class MetaLoseHealth extends MetaState {

	public MetaLoseHealth()
	{
		
	}
	
	private boolean hasExecuted= false;
	@Override
	public void execute(GameObject playerOne, MainGamePanel panel)
	{
		
		if(this.hasExecuted) return;
		this.hasExecuted = true;
		panel.Blink_Timer += 51;
		GameManager.adjustPlayerLives(1);
		if( GameManager.getPlayerLives() <= 0)
			panel.finish();
	}
}
