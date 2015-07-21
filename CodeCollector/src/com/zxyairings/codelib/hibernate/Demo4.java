package com.zxyairings.codelib.hibernate;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.User3;

public class Demo4 {
	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User3.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 构建对象
		User3 user = new User3();
//		user.setId(1);
		user.setId(UUID.randomUUID().toString());
		user.setName("zhangsan");

		// 保存
		session.save(user);
		// session.save(new User()); //注意记住老师是如何做多线程实验的。

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
