package utility;

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
	 * Will get the increment for the X and Y based upon the scalar and distance ratio
	 * @param position
	 * @param scalar
	 * @return
	 */
	public FloatPoint getIncrement(FloatPoint position, float scalar)
	{
		float distance = this.distance(position);
		float ratio = scalar/distance;
		float diffX = ratio*(position.X - this.X);
		float diffY = ratio*(position.Y - this.Y);
		return new FloatPoint(diffX, diffY);
	}
}
