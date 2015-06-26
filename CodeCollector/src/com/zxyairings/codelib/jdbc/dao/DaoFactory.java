package com.zxyairings.codelib.jdbc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	private static UserDao userDao = null;//这句话一定要放在下面这句的前面，否则会造成userDao虽然在构造函数中成功赋值，单又会被重新赋成null。原因是类的初始化顺序。
	private static DaoFactory instance = new DaoFactory();

	private DaoFactory() {
		try {
			Properties prop = new Properties();
//			InputStream inStream = new FileInputStream(new File("src/daoconfig.properties"));
			InputStream inStream = DaoFactory.class.getClassLoader()
					.getResourceAsStream("daoconfig.properties");//只要文件在classpath里，这种方式都可以找得到。
			prop.load(inStream);
			String userDaoClass = prop.getProperty("userDaoClass");
			Class clazz = Class.forName(userDaoClass);//Class对象就是类的字节码，相当于建筑中的图纸
			userDao = (UserDao) clazz.newInstance();
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DaoFactory getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
