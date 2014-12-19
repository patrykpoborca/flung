package meta.states;

import Game.GameObject;
import Game.MainGamePanel;


public class MetaEndGame extends MetaState {

	public MetaEndGame()
	{
		
	}
	
	@Override
	public void execute(GameObject playerOne, MainGamePanel panel)
	{
		
		panel.finish();
	}
}
