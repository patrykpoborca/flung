package utility;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class FloatRect {

	public FloatPoint XY;
	public FloatPoint WidthHeight;
	
	/**
	 * 
	 * @param XY
	 * @param WidthHeight
	 */
	public FloatRect(FloatPoint XY, FloatPoint WidthHeight)
	{
		this.XY = XY.copy();
		this.WidthHeight = WidthHeight.copy();
	}
	
	public FloatPoint getCenter()
	{
		float x = this.XY.X + (this.WidthHeight.X/2);
		float y = this.XY.Y + (this.WidthHeight.Y/2);
		return new FloatPoint(x, y);
	}
	
	public float getDistanceFromCenter()
	{
		return this.getCenter().distance(XY);
	}
	
	public float left()
	{
		return this.XY.X;
	}
	
	public float right()
	{
		return this.XY.X +this.WidthHeight.X;
	}
	public float top()
	{
		return this.XY.Y;
	}
	public float bottom()
	{
		return this.XY.Y + this.WidthHeight.Y;
	}
	public static boolean Intersects(FloatRect RectA, FloatRect RectB)
	{		
		if (RectA.left() < RectB.right() && RectA.right() > RectB.left() &&
			    RectA.top() < RectB.bottom() && RectA.bottom() > RectB.top()) 
			return true;
		return false;
	}
	
	public void render(Canvas canvas, Paint paint)
	{
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10);
		//top
		canvas.drawLine(this.XY.X, this.XY.Y,
				this.XY.X + this.WidthHeight.X, this.XY.Y, paint);
		//left
		canvas.drawLine(this.XY.X, this.XY.Y,
				this.XY.X, this.WidthHeight.Y+ this.XY.Y, paint);
		//right
		canvas.drawLine(this.XY.X + this.WidthHeight.X, this.XY.Y,
				this.XY.X + this.WidthHeight.X, this.XY.Y  +this.WidthHeight.Y, paint);
		//bottom
		canvas.drawLine(this.XY.X, this.XY.Y + this.WidthHeight.Y,
				this.XY.X + this.WidthHeight.X, this.XY.Y + this.WidthHeight.Y, paint);
		
	}
	
	/**
	 * Gets a list of all FloatLines that a Rect is made up of.
	 * @return
	 */
	public ArrayList<FloatLine> RectLines()
	{
		ArrayList<FloatLine> r_val = new ArrayList<FloatLine>();
		FloatPoint topLeft = this.XY;
		FloatPoint topRight = new FloatPoint(this.XY.X + this.WidthHeight.X, this.XY.Y);
		FloatPoint bottomRight = new FloatPoint(this.XY.X + this.WidthHeight.X, this.XY.Y + this.WidthHeight.Y);
		FloatPoint bottomLeft = new FloatPoint(this.XY.X, this.XY.Y + this.WidthHeight.Y);
		
		r_val.add(new FloatLine(topLeft, topRight, 1));
		r_val.add(new FloatLine(topRight, bottomRight, 1));
		r_val.add(new FloatLine(topLeft, bottomLeft, 1));
		r_val.add(new FloatLine(bottomLeft, bottomRight, 1));
		return r_val;
	}
	
	public static boolean Intersects(FloatRect RectA, FloatPoint PointB)
	{
		if (RectA.left() < PointB.X && RectA.right() > PointB.X &&
			    RectA.top() < PointB.Y && RectA.bottom() > PointB.Y) 
			return true;
		return false;
	}

	public FloatRect copy() {
		return new FloatRect(this.XY.copy(), this.WidthHeight.copy());
	}
}
