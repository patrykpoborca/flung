package com.flung.patryk.Game;

import java.util.ArrayList;
import java.util.Vector;

import com.flung.patryk.Game_Utility.FloatPoint;
import com.flung.patryk.Game_Utility.FloatRect;
import com.flung.patryk.Game_Utility.GameConstants;
import com.flung.patryk.MetaStates.*;

import android.graphics.*;
import android.util.Log;
import android.view.View;

public class GameObject {

	protected Bitmap bitmap;	// the actual bitmap
	protected float x;			// the X coordinate
	protected float y;			// the Y coordinate
	protected boolean touched;	//  is touched
	protected float velocityX; //current velocity...
	protected float velocityY;
	protected float constantForceX; //constant force applied each update... (this gravity)
	protected float constantForceY;
	public FloatRect collisionBox; 
	public ArrayList<MetaState> listOfCommands = new ArrayList<MetaState>();
	public boolean flagForDeletion = false;
	
	
	//accessors
	public float getX(){return this.x;}
	public float getY(){return this.y;}
	public void setX(float x){this.x = x;}
	public void sety(float y){this.y = y;}
	public float getHeight(){return ((float)this.bitmap.getHeight());}
	public float getWidth(){return ((float)this.bitmap.getWidth());}
	
	//end of accessors
	public GameObject(){}
	
	public GameObject(Bitmap bitmap, int x, int y)
	{
		
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.velocityX =0;
		this.velocityY =0;
		this.constantForceX =0;
		this.constantForceY =0;
		collisionBox = new FloatRect(new FloatPoint((float)this.x, (float)this.y),
				new FloatPoint((float)this.bitmap.getWidth(), (float)this.bitmap.getHeight()));
	}
	
	public void getCollision_Update()
	{
		collisionBox.XY.X =this.x;
		collisionBox.XY.Y = this.y;
		
	}
	public boolean collidedWith(GameObject obj)
	{
		obj.getCollision_Update();
		this.getCollision_Update();
		
		
		return FloatRect.Intersects(obj.collisionBox, this.collisionBox);
	}
	
	public float getVelocityX(){return this.velocityX;}
	public float getVelocityY(){return this.velocityY;}
	
	public void setVelocityX(float x)
	{
		this.velocityX = x;
	}
	
	public void setVelocityY(float y)
	{
		this.velocityY = y;
	}
	
	public void setConstantForceX(float x)
	{
		this.constantForceX =x;
	}
	public void setConstantForceY(float y)
	{
		this.constantForceY =y;
	}
	
	public void render(Canvas canvas)
	{
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	public boolean outOfBounds()
	{
		return (this.getX() + this.getWidth()<0) || 
				(this.getY() + this.getHeight() <0) ||
				(this.getY() > GameConstants.screenSizeY);
	}
	
	public void update()
	{
		this.velocityX += this.constantForceX;
		this.velocityY += this.constantForceY;
		
		this.x += this.velocityX * GameManager.gameClock;
		this.y += this.velocityY * GameManager.gameClock;
	}
	
	/**
	 * Called when you hit this object. Methods must add the objects they wish to enact
	 * @param playerOne
	 */
	public void executeFunctions(GameObject playerOne, MainGamePanel panel)
	{
		for(int a=0; a < this.listOfCommands.size(); a++)
		{
			this.listOfCommands.get(a).execute(playerOne, panel);
		}
	}
	public void addFunction(MetaState state)
	{
		this.listOfCommands.add(state);
	}
	
}
