package com.flung.patryk.Game;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class FlungObject extends GameObject {

	
	public FlungObject(ArrayList<Bitmap> stages, int x, int y)
	{
		super(stages.get(0), x, y);
		this.stages = stages;
		this.collisionBox.XY.X+= 4f;
		this.collisionBox.WidthHeight.X -= 4f;
		this.collisionBox.WidthHeight.Y -= 8f;
	}
	private ArrayList<Bitmap> stages;
	private int iterator =0;
	private int drawDelay =0;
	@Override
	public void render(Canvas canvas)
	{
		//cycling through animations
		drawDelay ++;
		if(drawDelay /8 == 1)
		{drawDelay = 0;
			iterator ++;
			if(iterator == stages.size()) iterator =0;
		}
		
		this.bitmap = stages.get(iterator);
		super.render(canvas);
		
	}
}
