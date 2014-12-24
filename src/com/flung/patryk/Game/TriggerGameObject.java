package com.flung.patryk.Game;

import com.flung.patryk.Game_Utility.FloatPoint;
import com.flung.patryk.Game_Utility.FloatRect;

import android.graphics.Canvas;

public class TriggerGameObject extends GameObject {

	public TriggerGameObject(int x, int y, float width, float height)
	{
		this.x = x;
		this.y = y;
		collisionBox = new FloatRect(new FloatPoint((float)this.x, (float)this.y),
				new FloatPoint(width, height));
	}
	
	@Override
	public void render(Canvas canvas)
	{
		//no render...
	}
	
	
}
