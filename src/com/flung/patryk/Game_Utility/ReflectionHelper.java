package com.flung.patryk.Game_Utility;

import java.lang.reflect.*;

import android.util.Log;

public class ReflectionHelper {

	/**
	 * Iterates over all SET fields and converts them into a parsable string.
	 * YOU MUST HAVE A TO_STRING/FROM_STRING FOR YOUR DECLARED FIELDS FOR THIS TO WORK!
	 * @param c
	 * @return
	 */
	public static byte[] createParsedString_as_bytes(Object obj)
	{
		Class<?> c = obj.getClass();
		String r_val = "";
		
		try
		{
			Field fields[] = c.getDeclaredFields();
			for(int a=0; a < fields.length; a++)
			{
				Field temp = fields[a];
				r_val += temp.getName();
				r_val += "?=?";
				r_val += (""+ temp.get(obj));
				r_val += "?=?";
			}
		}
		catch(Exception e)
		{
			Log.d("CreateParsed String", "ERROR" + e);
			return null;
		}
		
		return r_val.getBytes();
	}
	
	/**
	 * returns the FieldName + Field range 0 - returnVal
	 * @param i
	 * @param param
	 * @return
	 */
	public static int getNext(int i, String param)
	{

		
		int until = param.substring(i).indexOf("?=?");
		
		until +=3+ param.substring(until + 3).indexOf("?=?");;
		
		
		return until;
	}
	
	/**
	 * Assumes formate "THIS IS STRING ?=? end of string" SEE getNext(int, String)
	 * returns array of [0]= name, [1]= value
	 * @param param
	 * @return
	 */
	public static String[] cleanParse(String param)
	{
		
		String[] r_val= new String[2];
		int temp =  param.indexOf("?=?");
		r_val[0] = param.substring(0,temp);
		r_val[1] = param.substring(temp + 3);
		
		return r_val;
	}
	
	/**
	 * Gets the field based on a name from a class
	 * @param c
	 * @param Name
	 * @return
	 */
	public static Field getField(Object obj, String Name)
	{
		Class<?> c = obj.getClass();
		try{
			Field fields[] = c.getFields();
			for(int a=0; a < fields.length; a++)
			{
				
				if(Name.contains(fields[a].getName()))
					return fields[a];
			}
			
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	public static void SetField(Field field, Object object, String value)
	{
		
		try
		{
			if(field.getType() == Boolean.TYPE)
			{
					
					field.setBoolean(object, Boolean.parseBoolean(value));
				
			}
			else
			if(field.getType() == Float.TYPE)
			{
					
					field.setFloat(object, Float.parseFloat(value));
				
			}
			else
			if(field.getType() == Integer.TYPE)
			{
				
					field.setInt(object, Integer.parseInt(value));
				
			}
			else
			{
				
					field.set(object, value);
				
				
			}
		}catch(Exception e){
			Log.d("Error: ", e.toString());
		}
	}
	
	
}
