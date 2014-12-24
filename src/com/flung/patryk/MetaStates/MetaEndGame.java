package com.flung.patryk.MetaStates;

import com.flung.patryk.Game.GameManager;
import com.flung.patryk.Game.GameObject;
import com.flung.patryk.Game.MainGamePanel;


public class MetaEndGame extends MetaState {

	public MetaEndGame()
	{
		
	}
	
	//will reset position on going out of bounds and kill if out of lives.
	@Override
	public void execute(GameObject playerOne, MainGamePanel panel)
	{
		GameManager.adjustPlayerLives(1);
		panel.Blink_Timer += 51;
		if(GameManager.getPlayerLives() <= 0)
			panel.finish();
		else
			panel.resetPosition();
	}
}
