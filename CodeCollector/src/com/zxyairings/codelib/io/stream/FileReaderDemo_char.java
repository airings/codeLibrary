package com.zxyairings.codelib.io.stream;
/*
?????????????????????????????????????????????
*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderDemo_char {

	public static void main(String[] args)
	{
		//????????????????????????????????????????????????????????????????????????
		//????????????????????????????????????????????????????????????????????????FileNotFoundException
		FileReader fr=null;
		try {
			fr = new FileReader("demo.txt");
			
			//????????????????????????read?????????
			//read():???????????????????????????????????????????????????
			// ???????????????????????????????????????-1
			// ???????????????????????????????????????????????????????????????????????????java???????????????-1?????????????????????
			int ch1 = fr.read();
			int ch2 = fr.read();
			System.out.println("ch1="+(char)ch1);
			System.out.println("ch2="+(char)ch2);
			
			int ch = 0;

			while((ch=fr.read())!=-1)
			{
				System.out.println((char)ch);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/*
		while(true)
		{
			int ch = fr.read();
			if(ch==-1)
				break;
			System.out.println("ch="+(char)ch);
		}
		*/
	}

}
