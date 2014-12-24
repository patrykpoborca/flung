package com.flung.patryk.Game;

import com.flung.patryk.MetaStates.MetaEndGame;

public class ExitBorders extends TriggerGameObject {

	public ExitBorders(int x, int y, float width, float height)
	{
		super(x, y, width, height);
		this.addFunction(new MetaEndGame()); //ends game on trigger
	}
	
}
