package com.zxyairings.codelib.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.Student;
import com.zxyairings.codelib.hibernate.domain.Teacher;

public class App_ManyToMany {
	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Student.class)// 添加Hibernate实体类（加载对应的映射文件）
			.addClass(Teacher.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();// 在此时建表，前提是设置了hbm2ddl.auto

	// 保存，有关联关系
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 新建对象
		Student student1 = new Student();
		student1.setName("wang");

		Student student2 = new Student();
		student2.setName("li");

		Teacher teacher1 = new Teacher();
		teacher1.setName("zhao Teacher");

		Teacher teacher2 = new Teacher();
		teacher2.setName("cai Teacher");

		// 关联起来
		student1.getTeachers().add(teacher1);
		student1.getTeachers().add(teacher2);
		student2.getTeachers().add(teacher1);
		student2.getTeachers().add(teacher2);

		teacher1.getStudents().add(student1);
		teacher1.getStudents().add(student2);
		teacher2.getStudents().add(student1);
		teacher2.getStudents().add(student2);

		// 保存
		session.save(student1);
		session.save(student2);
		session.save(teacher1);
		session.save(teacher2);

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
		Teacher teacher = (Teacher) session.get(Teacher.class, 3L);
		System.out.println(teacher);
		System.out.println(teacher.getStudents());
		
		Student student = (Student) session.get(Student.class, 3L);
		System.out.println(student);
		System.out.println(student.getTeachers());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	// 解除关联关系
	@Test
	public void testRemoveRelation() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 如果inverse=false就可以解除，如果为true就不可以解除
		Teacher teacher = (Teacher) session.get(Teacher.class, 3L);
		teacher.getStudents().clear();

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
		// b, 如果有关联的对方且inverse=false，由于可以维护关联关系，他就会先删除关联关系，再删除自己。
		// c, 如果有关联的对方且inverse=true，由于不能维护关联关系，所以会直接执行删除自己，就会有异常。
		Teacher teacher = (Teacher) session.get(Teacher.class, 9L);
		session.delete(teacher);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
