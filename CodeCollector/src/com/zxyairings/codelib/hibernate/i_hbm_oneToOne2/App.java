package com.zxyairings.codelib.hibernate.i_hbm_oneToOne2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Person.class)// 添加Hibernate实体类（加载对应的映射文件）
			.addClass(IdCard.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	// 保存，有关联关系
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 新建对象
		Person person = new Person();
		person.setName("张三");

		IdCard idCard = new IdCard();
		idCard.setNumber("100000011X");

		// 关联起来
		person.setIdCard(idCard);
		idCard.setPerson(person);

		// 保存
		session.save(person);
		session.save(idCard);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// 获取，可以获取到关联的对方
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 获取一方，并显示另一方信息
		// Person person = (Person) session.get(Person.class, 1);
		// System.out.println(person);
		// System.out.println(person.getIdCard());

		IdCard idCard = (IdCard) session.get(IdCard.class, 1);
		System.out.println(idCard);
		System.out.println(idCard.getPerson());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	// 解除关联关系：使用基于主键的一对一映射方式：双方都不可以解除关联关系。
	@Test
	public void testRemoveRelation() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 基于主键的一对一映射：从有外键方解除关系，不可以，因为主键 不能为null。
		IdCard idCard = (IdCard) session.get(IdCard.class, 1);
		idCard.setPerson(null);

		// // 基于主键的一对一映射：从无外键方解除关系，不可以。
		// Person person = (Person) session.get(Person.class, 1);
		// person.setIdCard(null);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// 删除对象，对关联对象的影响
	@Test
	public void testDelete() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// a, 如果没有关联的对方：能删除。
		// c, 如果有关联的对方：因为会直接执行删除自己，所以无外键方会有异常，有外键方没有异常。

		IdCard idCard = (IdCard) session.get(IdCard.class, 1);
		session.delete(idCard);

		// Person person = (Person) session.get(Person.class, 1);
		// session.delete(person);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
