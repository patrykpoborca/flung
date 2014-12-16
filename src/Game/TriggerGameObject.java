package Game;

import utility.FloatPoint;
import utility.FloatRect;
import android.graphics.Canvas;

public class TriggerGameObject extends GameObject {

	public TriggerGameObject(int x, int y, float width, float height)
	{
		this.x = x;
		this.y = y;
		collisionBox = new FloatRect(new FloatPoint((float)this.x, (float)this.y),
				new FloatPoint(width, height));
	}
	
	@Override
	public void render(Canvas canvas)
	{
		//no render...
	}
	
	
}
