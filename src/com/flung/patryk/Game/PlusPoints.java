package com.flung.patryk.Game;

import java.util.ArrayList;
import com.flung.patryk.MetaStates.*;



public class PlusPoints extends GameObject {
	
	
	public PlusPoints(int x, int y)
	{
		
		super(GameManager.bonusPicture, x, y);
		this.listOfCommands = new ArrayList<MetaState>();
		this.listOfCommands.add(new AddPoints());
	}
		
	@Override
	public void executeFunctions(GameObject playerOne, MainGamePanel panel)
	{
		for(int a=0; a < this.listOfCommands.size(); a++)
		{
			this.listOfCommands.get(a).execute(playerOne, panel);
		}
		this.flagForDeletion = true;
	}
	
}
