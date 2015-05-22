package com.zxyairings.codelib.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/*
 * 
 * 自定义的类加载器的必须继承ClassLoader
 * loadClass方法: 不需要重写这个方法，这个方法的内部已经实现委托机制，如果找不到，会调用当前类加载器的findClass方法来加载类，这是模板方法设计模式。
 * findClass方法: 是当前自定义类加载器 加载类的实现方式，我们只需要覆盖该方法，那么我们就保留了loadClass方法中的流程，而具体细节则在findClass方法中
 * defineClass方法: converts an array of bytes into an instance of class Class. 将class文件转化成Class对象
 * 
 *      class NetworkClassLoader extends ClassLoader {
         String host;
         int port;

         public Class findClass(String name) {
             byte[] b = loadClassData(name);
             return defineClass(name, b, 0, b.length);
         }

         private byte[] loadClassData(String name) {
             // load the class data from the connection
              . . .
         }
     }
 * 
 * 
 * */

public class MyClassLoader extends ClassLoader{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String srcPath = args[0];
		String destDir = args[1];
		FileInputStream fis = new FileInputStream(srcPath);
		String destFileName = srcPath.substring(srcPath.lastIndexOf('\\')+1);
		String destPath = destDir + "\\" + destFileName;
		FileOutputStream fos = new FileOutputStream(destPath);
		cypher(fis,fos);
		fis.close();
		fos.close();
	}
	
	//加密程序
	private static void cypher(InputStream ips ,OutputStream ops) throws Exception{
		int b = -1;
		while((b=ips.read())!=-1){
			ops.write(b ^ 0xff);
		}
	}

	private String classDir;

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		String classFileName = classDir + "\\"  + name.substring(name.lastIndexOf('.')+1) + ".class";
		try {
			FileInputStream fis = new FileInputStream(classFileName);
			ByteArrayOutputStream  bos = new ByteArrayOutputStream();
			cypher(fis,bos);
			fis.close();
			System.out.println("aaa");
			byte[] bytes = bos.toByteArray();
			return defineClass(bytes, 0, bytes.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public MyClassLoader(){
		
	}
	
	public MyClassLoader(String classDir){
		this.classDir = classDir;
	}
}
