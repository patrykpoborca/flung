package com.flung.patryk.Game;

import com.flung.patryk.Game_Utility.*;
import com.flung.patryk.MetaStates.MetaLoseHealth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
public class FloatingObject extends GameObject {
	
	public FloatLine position;
	
	
	//ACCESSORS
	@Override
	public float getX(){return position.initial().X;}
	@Override
	public float getY(){return position.initial().Y;}
	@Override
	public void setX(float x){this.position.MOVE(new FloatPoint(x, 0f));}
	@Override
	public void sety(float y){this.position.MOVE(new FloatPoint(0f, y));}
	@Override
	public float getHeight(){return this.position.initial().getDifference(this.position.last()).Y;}
	@Override
	public float getWidth(){return this.position.initial().getDifference(this.position.last()).X;}
	//end of accessors
	
	public FloatingObject(FloatPoint start, FloatPoint end, float width)
	{
		this(new FloatLine(start, end, width));
	}
	
	public FloatingObject(FloatLine line)
	{
		this.position = line.copy();
		this.listOfCommands.add(new MetaLoseHealth());
	}

	public FloatingObject() {
		//DO NOT USE UNLESS NEED MEMORARY ALLOCATED!
	}
	@Override
	public void getCollision_Update() {
		//not needed with fleshline
	}

	@Override
	public boolean collidedWith(GameObject obj) {
		return this.position.Intersects(obj.collisionBox);
	}

	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		this.position.render(canvas, paint);
	}

	@Override
	public void update() {
		
		
		// TODO Auto-generated method stub
		this.velocityX += this.constantForceX;
		this.velocityY += this.constantForceY;
		
		this.position.MOVE(new FloatPoint(this.velocityX*GameManager.gameClock, this.velocityY * GameManager.gameClock));
	}
	

	
	
	@Override
	public void executeFunctions(GameObject playerOne, MainGamePanel panel) {
		for(int a=0; a < this.listOfCommands.size(); a++)
		{
			this.listOfCommands.get(a).execute(playerOne, panel);
		}
	}
	public boolean equals(FloatingObject temp)
	{
		return this.position.equals(temp.position);
	}
	
}
