package com.flung.patryk.Game_Utility;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;


public class FloatLine {

	private FloatPoint initial;
	private FloatPoint last;
	public FloatRect softBox;
	public float Width;
	public FloatLine(FloatPoint initial, FloatPoint last, float Width)
	{
		this.Width = Width;
		this.initial = initial.copy();
		this.last = last.copy();
		//INITIAL SHOULD ALWAYS START ABOVE

		
		
		
		FloatPoint dimensions = this.last.getDifference(this.initial);
		dimensions.X = (dimensions.X == 0) ? 1f : dimensions.X;
		dimensions.Y = (dimensions.Y == 0) ? 1f : dimensions.Y;
		FloatPoint leftCorner = (this.initial.Y < this.last.Y) ? this.initial : this.last;
		this.softBox = new FloatRect(leftCorner, dimensions);

	}
	
	public FloatPoint initial()
	{
		return this.initial;
	}
	public FloatPoint last()
	{
		return this.last;
	}
	public Paint overridePaint = null;
	public void render(Canvas canvas, Paint paint)
	{
		paint.setStrokeWidth(this.Width);
		canvas.drawLine(this.initial.X, this.initial.Y,
				this.last.X, this.last.Y, (this.overridePaint != null) ? this.overridePaint :paint);
		
		//this.softBox.render(canvas, paint);
		
	}
	
	public boolean Intersects(FloatRect rect)
	{
		if(!FloatRect.Intersects(this.softBox, rect)) return false; //doesn't even intersect soft Box
		ArrayList<FloatLine> RectLines = rect.RectLines();
		for(int a=0; a < RectLines.size(); a++)
		{
			//checks if any of the rect lines intersect with the current line.
			if(IntersectsLine(RectLines.get(a), this))
				return true;
		}
		return false;
	}
	public float holderValue = -12345;
	
	public void MOVE(FloatPoint movement)
	{
		this.softBox.XY.X += movement.X;
		this.softBox.XY.Y += movement.Y;

		this.initial.X += movement.X;

		this.initial.Y += movement.Y;

		this.last.X += movement.X;

		this.last.Y += movement.Y;

	}
	
	
	public static boolean IntersectsLine(FloatLine A, FloatLine B)
	{
		return IntersectsLine(A.initial, A.last, B.initial, B.last);
	}
	
	public static boolean IntersectsLine(FloatPoint A, FloatPoint B, FloatPoint C, FloatPoint D)
	{
		
		FloatPoint CmP = new FloatPoint(C.X - A.X, C.Y - A.Y);
		FloatPoint r = new FloatPoint(B.X - A.X, B.Y - A.Y);
		FloatPoint s = new FloatPoint(D.X - C.X, D.Y - C.Y);
 
		float CmPxr = CmP.X * r.Y - CmP.Y * r.X;
		float CmPxs = CmP.X * s.Y - CmP.Y * s.X;
		float rxs = r.X * s.Y - r.Y * s.X;
 
		if (CmPxr == 0f)
		{
			// Lines are collinear, and so intersect if they have any overlap
 
			return ((C.X - A.X < 0f) != (C.X - B.X < 0f))
				|| ((C.Y - A.Y < 0f) != (C.Y - B.Y < 0f));
		}
 
		if (rxs == 0f)
			return false; // Lines are parallel.
 
		float rxsr = 1f / rxs;
		float t = CmPxs * rxsr;
		float u = CmPxr * rxsr;
 
		return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
	}

	
	public boolean equals(FloatLine temp)
	{
		return (this.initial.equals(temp.initial) && (this.last.equals(temp.last)));
	}
	
	public FloatLine copy()
	{
		FloatPoint start = this.initial.copy();
		FloatPoint last = this.last.copy();
		FloatLine r_val = new FloatLine(start, last, this.Width);
		r_val.softBox = this.softBox.copy();
		return r_val;
		
	}
}