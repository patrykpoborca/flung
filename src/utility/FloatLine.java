package utility;

import java.util.ArrayList;


public class FloatLine {

	float Width;
	public ArrayList<FloatPoint> positions;
	public FloatRect softCollision; //used to detect whether better collision needs to be computed
	
	public FloatLine(FloatPoint initial,FloatPoint last)
	{
		positions = new ArrayList<FloatPoint>();
		FloatPoint current;
		current = initial;
		float distance = initial.distance(last);
		FloatPoint incrementVector = initial.getIncrement(last, distance);
		
		for(float a=0; a < distance; a+=(10f/MetaGameData.FetchCollisionPrecision()))
		{
			positions.add(current);
			current.add(incrementVector);			
		}
		if(!positions.get(positions.size()-1).equals(last))
		{
			positions.add(last);
		}
		
		this.softCollision = new FloatRect(initial, initial.getIncrement(last, distance));
	}
	
	/**
	 * Determines if they intsersect with a box. 
	 * @param box
	 * @return
	 */
	public boolean Intersects(FloatRect box)
	{
		float distanceCheck = this.Width + box.getDistanceFromCenter();
		if(FloatRect.Intersects(this.softCollision, box))
		{
			for(int a=0; a < this.positions.size(); a++)
			{
				if(distanceCheck >= this.positions.get(a).distance(box.getCenter()))
					return true;
			}
		}
		return false;
	}
	
	
}
