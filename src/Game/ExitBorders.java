package Game;

import meta.states.MetaEndGame;

public class ExitBorders extends TriggerGameObject {

	public ExitBorders(int x, int y, float width, float height)
	{
		super(x, y, width, height);
		this.addFunction(new MetaEndGame()); //ends game on trigger
	}
	
}
