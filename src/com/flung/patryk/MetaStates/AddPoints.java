package com.flung.patryk.MetaStates;

import com.flung.patryk.Game.GameManager;
import com.flung.patryk.Game.GameObject;
import com.flung.patryk.Game.MainGamePanel;

public class AddPoints extends MetaState {

	public AddPoints()
	{
		
	}
	
	//will reset position on going out of bounds and kill if out of lives.
	@Override
	public void execute(GameObject playerOne, MainGamePanel panel)
	{
		GameManager.PLAYER_POINTS += 100;
	}
}
