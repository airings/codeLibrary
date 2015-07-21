package com.zxyairings.codelib.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.Student;
import com.zxyairings.codelib.hibernate.domain.Teacher;
import com.zxyairings.codelib.hibernate.domain.User_sessionMethod;

public class App_sessionMethod {
	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User_sessionMethod.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	// save()：把临时状态变为持久化状态（交给Sessioin管理）
	// 会生成：insert into ...
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User_sessionMethod user = new User_sessionMethod(); // 临时状态
		user.setName("test");
		session.save(user); // 变为了持久化状态

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

		user.setName("李四"); // 游离状态
		System.out.println(user.getName()); // 游离状态
	}

	// update()：把游离状态变为持久化状态
	// 会生成：update ...
	// 在更新时，对象不存在就报错
	@Test
	public void testUpdate() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User_sessionMethod user = (User_sessionMethod) session.get(User_sessionMethod.class, 1);
		System.out.println(user.getName()); // 持久化状态

		// session.clear(); // 清除Session中所有的对象
		session.evict(user); // 清除Session中一个指定的对象

		user.setName("newname3");
		session.update(user);
		System.out.println("----");
		// session.flush(); // 刷出到数据库

		// --------------------------------------------
		session.getTransaction().commit(); //
		session.close();
	}

	// saveOrUpdate()：把临时或游离状态转为持久化状态
	// 会生成：insert into 或 update ...
	// 在更新时，对象不存在就报错
	// 本方法是根据id判断对象是什么状态的：如果id为原始值（对象的是null，原始类型数字是0）就是临时状态，如果不是原始值就是游离状态。
	@Test
	public void testSaveOrUpdate() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User_sessionMethod user = new User_sessionMethod();
		user.setId(3); // 自己生成一个游离状态对象
		user.setName("newName");

		session.saveOrUpdate(user);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// delete()：把持久化或游离转为删除状态
	// 会生成：delete ...
	// 如果删除的对象不存在，就会抛异常
	@Test
	public void testDelete() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// User user = (User) session.get(User.class, 2); // 持久化

		User_sessionMethod user = new User_sessionMethod();
		user.setId(300);

		session.delete(user);
		session.flush();

		System.out.println("---");

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// get()：获取数据，是持久化状态
	// 会生成：select ... where id=?
	// 会马上执行sql语句
	// 如果数据不存在，就返回null
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User_sessionMethod user = (User_sessionMethod) session.get(User_sessionMethod.class, 5); // 持久化
		System.out.println(user.getClass());
		// System.out.println("---");
		// System.out.println(user.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// load()：获取数据，是持久化状态
	// 会生成：select ... where id=?
	// load()后返回的是一个代理对象，要求类不能是final的，否则不能生成子类代理，就不能使用懒加载功能了。
	// 让懒加载失效的方式：一、把实体写成final的；二、在hbm.xml中写<class ... lazy="false">
	// 不会马上执行sql语句，而是在第1次使用非id或class属性时执行sql。
	// 如果数据不存在，就抛异常：ObjectNotFoundException
	@Test
	public void testLoad() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User_sessionMethod user = (User_sessionMethod) session.load(User_sessionMethod.class, 5);
		System.out.println(user.getClass());
		System.out.println("---");
		System.out.println(user.getId());
		System.out.println(user.getName());
		// System.out.println(user.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// 操作大量数据，要防止Session中对象过多而内存溢出
	@Test
	public void testBatchSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		for (int i = 0; i < 30; i++) {
			User_sessionMethod user = new User_sessionMethod();
			user.setName("测试");
			session.save(user);

			if (i % 10 == 0) {
				session.flush(); // 先刷出
				session.clear(); // 再清空
			}
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void test2() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User_sessionMethod user = (User_sessionMethod) session.get(User_sessionMethod.class, 5); // 持久化
		System.out.println(user.getName());

		// session.clear();
		// user = (User_sessionMethod) session.get(User_sessionMethod.class, 5); // 持久化

		session.refresh(user); // 刷新Session缓存中对象的状态，即重新select一下
		System.out.println(user.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
