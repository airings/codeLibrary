/*
IO??????????????????????????????
*/
package com.zxyairings.codelib.io.stream;

import java.io.FileWriter;
import java.io.IOException;

public class IOExceptionHandle {
	public static void main(String[] args) 
	{
		FileWriter fw = null;
		try
		{
			fw = new FileWriter("demo.txt");
			fw.write("abcdefg");

		}
		catch (IOException e)
		{
			System.out.println("catch:"+e.toString());
		}
		finally
		{
			try
			{
				if(fw!=null)
					fw.close();				
			}
			catch (IOException e)
			{
				System.out.println(e.toString());
			}
			
		}		

	}
}
