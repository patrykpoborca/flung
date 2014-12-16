package utility;

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
		this.XY = XY;
		this.WidthHeight = WidthHeight;
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
}
