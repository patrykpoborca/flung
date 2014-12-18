package Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import utility.*;
public class FloatingObject extends GameObject {
	
	public FloatLine position;
	
	
	//ACCESSORS
	@Override
	public float getX(){return position.initial.X;}
	@Override
	public float getY(){return position.initial.Y;}
	@Override
	public void setX(float x){this.position.MOVE(new FloatPoint(x, 0f));}
	@Override
	public void sety(float y){this.position.MOVE(new FloatPoint(0f, y));}
	@Override
	public float getHeight(){return this.position.initial.getDifference(this.position.last).Y;}
	@Override
	public float getWidth(){return this.position.initial.getDifference(this.position.last).X;}
	//end of accessors
	
	public FloatingObject(FloatPoint start, FloatPoint end, float width)
	{
		this(new FloatLine(start, end, width));
	}
	
	public FloatingObject(FloatLine line)
	{
		this.position = line;
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
		
		this.position.MOVE(new FloatPoint(this.velocityX*MetaGameData.gameClock, this.velocityY * MetaGameData.gameClock));
	}
	
	@Override
	public int hashCode()
	{
		final int hashInt = 3;
		int result = hashInt * (int)this.position.initial.X;
		result =result * hashInt * (int)this.position.initial.Y;
		result =result * hashInt * (int)this.position.last.X;
		result =result * hashInt * (int)this.position.last.Y;
		return result;
	}
	
	
	public boolean equals(FloatingObject temp)
	{
		return this.position.equals(temp.position);
	}
	
}
