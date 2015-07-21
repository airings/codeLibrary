package com.zxyairings.codelib.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	// SessionFactory全局只需要有一个就可以了
	private static SessionFactory sessionFactory;

	static {
		Configuration cfg = new Configuration();
		// hibernate.cfg.xml一般放在根目录下(src下面)，也可以放在某个包下面，但是要加上包得路径，开始不要加/。
		// cfg.configure(); // 如果配置文件名字不改，路径不变，读取默认的根路径下的配置文件（hibernate.cfg.xml）
		// // cfg.configure("hibernate.cfg.xml"); // 读取指定位置的配置文件
		// sessionFactory = cfg.buildSessionFactory();

		// 不读取配置文件的话，也可以这样将配置信息通知Hibernate，因为配置信息就是key-value对。
		// cfg.setProperties(properties);
		// cfg.setProperty(propertyName, value);

		// 测试时经常用下面两个方式之一，来增加映射文件，等价于 hibernate.cfg.xml的第29行
		// cfg.addResource("cn/itcast/a_helloworld/User.hbm.xml");//导入一个指定位置的映射文件
		// cfg.addClass(User.class); // 去User类所在的包中查找名称为User，后缀为.hbm.xml的文件

		// 初始化SessionFactory
		sessionFactory = new Configuration()//
				.configure()//
				.buildSessionFactory();

		// sessionFactory.close();
	}

	/**
	 * 获取全局唯一的SessionFactory
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 从全局唯一的SessionFactory中打开一个Session
	 * 
	 * @return
	 */
	public static Session openSession() {
		return sessionFactory.openSession();
	}
}
