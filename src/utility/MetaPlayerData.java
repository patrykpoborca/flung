package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

import com.example.flung.*;

import Game.GameManager;
import android.app.Activity;
import android.content.Context;
import android.util.Log;


/**
 * Class which holds data about the player... persists from game to game.
 * @author Patryk
 *
 */
final public class MetaPlayerData {

	public  String UserName;
	public  int highScore;
	public  float DifficultyPreference;
	
	public MetaPlayerData(){}
	
	
	
	public static void LoadPlayerData(Context context)
	{
		String path = context.getFilesDir().getAbsolutePath();
		File file = new File(path + GameConstants.fileName);
		
		if(file.exists())
		{
			int length = (int) file.length();

			byte[] bytes = new byte[length];

			
			try {
				FileInputStream in = new FileInputStream(file);
			    in.read(bytes);
			    in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			String contents = new String(bytes);   
			//Log.d("DISPLAY PRE-eRROR", contents);
			int index = 0;
			
			Field field;
			String returnedString[];
			
			while(true)
			{
				index = ReflectionHelper.getNext(index, contents);
				
				returnedString = ReflectionHelper.cleanParse(contents.substring(0 , index));
				field = ReflectionHelper.getField(GameManager.SELF, returnedString[0]);
				
				
				ReflectionHelper.SetField(field, GameManager.SELF, returnedString[1]);				
				contents = contents.substring(index+3);
				
				index =0;
				if(contents.length() <=3)
				{
					break;
				}
			}
//			Log.d("Printing!", contents);
		}
	}
	
	public static void SavePlayerData(Context context)
	{
		String path = context.getFilesDir().getAbsolutePath();
		File file = new File(path + GameConstants.fileName);
		
		
		try {
			FileOutputStream stream = new FileOutputStream(file);
//		    stream.write(GameManager.SELF.UserName.getBytes());
//		    stream.write(("!=!"+ GameManager.SELF.highScore).getBytes());
//		    stream.write(("!=!"+ GameManager.SELF.DifficultyPreference + "!=!").getBytes());
			byte bytearray[]= ReflectionHelper.createParsedString_as_bytes(GameManager.SELF);
			//Log.d("PreWRITE", new String(bytearray));
			stream.write(bytearray);
		    stream.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
