package utility;

import android.util.Log;

public class FloatPoint {
	public float X;
	public float Y;
	public FloatPoint(){}
	public FloatPoint(float X, float Y){this.X = X; this.Y = Y;}
	
	public float distance(FloatPoint position)
	{
		double width = (double)(position.X - this.X);
		double height = (double)(position.Y - this.Y);
		return (float)Math.sqrt(Math.pow(width, 2)+ Math.pow(height, 2));
	}
	

	public boolean equals(FloatPoint other)
	{
		
		return (other.X == this.X && other.Y == this.Y); 
	}
	
	public void add(FloatPoint increment)
	{
		this.X += increment.X;
		this.Y += increment.Y;
	}
	
	/**
	 * Get's absolute values of X and Y differences between two points (height/widht difference) 
	 * @param other
	 * @return
	 */
	public FloatPoint getDifference(FloatPoint other)
	{
		Float x = Math.abs(this.X - other.X);
		Float y = Math.abs(this.Y - other.Y);
		return new FloatPoint(x, y);
	}
	
	/**
	 * Will get the increment for the X and Y based upon the scalar/distance
	 * @param position
	 * @param scalar
	 * @return
	 */
	public FloatPoint getIncrement(FloatPoint position, float scalar)
	{
		float distance = this.distance(position);
		float ratio = scalar/distance;
		float diffX = ratio*this.getDifference(position).X;
		float diffY = ratio*this.getDifference(position).Y;
		return new FloatPoint(diffX, diffY);
	}
	public FloatPoint copy() {
		return new FloatPoint(this.X, this.Y);
	}
	
	
	
}
