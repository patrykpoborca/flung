package utility;

public class MetaGameData {

	public static float GameQuality; //scalar of game quality 0 to 1f
	
	//true statics
	public static float PixelDensity = 10f;
	
	public static float FetchCollisionPrecision()
	{		
		return MetaGameData.PixelDensity * GameQuality;//Indicates the density per 10 DPI for collision
	}
}
