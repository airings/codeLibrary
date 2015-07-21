package com.zxyairings.codelib.hibernate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.User2;



public class Demo3 {


	private static SessionFactory sessionFactory;

	static {
		sessionFactory = new Configuration()//
				.configure()// 读取配置文件
				.addClass(User2.class)//
				.buildSessionFactory();
	}

	@Test
	public void testSave() throws Exception {
		// 读取图片文件
//		InputStream in = new FileInputStream( "c:/test.png");
//		byte[] photo = new byte[in.available()];
//		in.read(photo);
//		in.close();
		
		// 创建对象实例
		User2 user = new User2();
		user.setName("zhangsan");
		user.setAge(20);
		user.setBirthday(new Date());
		user.setDesc("description here and put a lot words here and let's ignore ...");
//		user.setPhoto(photo);

		// 保存
		Session session = sessionFactory.openSession(); // 打开一个新的Session
		Transaction tx = session.beginTransaction(); // 开始事务

		session.save(user);

		tx.commit(); // 提交事务
		session.close(); // 关闭Session，释放资源
	}

	
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		User2 user = (User2) session.get(User2.class, 1); // 获取
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getDesc());
		System.out.println(user.getPhoto());
		
//		OutputStream out = new FileOutputStream("c:/copy.png");
//		out.write(user.getPhoto());
//		out.close();

		tx.commit();
		session.close();
	}
}
