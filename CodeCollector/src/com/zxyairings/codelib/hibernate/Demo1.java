package com.zxyairings.codelib.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.User;



public class Demo1 {

	private static SessionFactory sessionFactory;

	static {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml"); // 读取指定的主配置文件
		sessionFactory = cfg.buildSessionFactory(); // 根据生成Session工厂
	}

	@Test
	public void testSave() throws Exception {
		User user = new User();
		user.setName("zhangsan");

		// 保存
		Session session = sessionFactory.openSession(); // 打开一个新的Session
		Transaction tx = session.beginTransaction(); // 开始事务

		session.save(user);

		tx.commit(); // 提交事务
		session.close(); // 关闭Session，释放资源，不一定是真正的关闭数据库连接，内部维护一个数据库连接池。
	}

	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		User user = (User) session.get(User.class, 2); // 获取
		System.out.println(user);

		tx.commit();
		session.close();
	}
}
