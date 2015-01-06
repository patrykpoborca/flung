package com.flung.patryk.Game;

import java.util.ArrayList;

import com.flung.patryk.R;
import com.flung.patryk.Game_Utility.GameConstants;
import com.flung.patryk.MetaStates.AddShield;
import com.flung.patryk.MetaStates.MetaState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ShieldPowerUp extends GameObject{
	
	public ShieldPowerUp(int x, int y)
	{
		
		super(GameManager.armorPicture, x, y);
		this.listOfCommands = new ArrayList<MetaState>();
		this.listOfCommands.add(new AddShield());
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
