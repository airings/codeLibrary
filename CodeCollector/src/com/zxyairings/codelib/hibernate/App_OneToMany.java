package com.zxyairings.codelib.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.Department;
import com.zxyairings.codelib.hibernate.domain.Employee;

public class App_OneToMany {
	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Department.class)// 添加Hibernate实体类（加载对应的映射文件）
			.addClass(Employee.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	// 保存，有关联关系
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 新建对象
		Department department = new Department();
		department.setName("Development");

		Employee employee1 = new Employee();
		employee1.setName("Harrison");

		Employee employee2 = new Employee();
		employee2.setName("Tenzing");

		// 关联起来
		employee1.setDepartment(department);
		employee2.setDepartment(department);
		department.getEmployees().add(employee1);
		department.getEmployees().add(employee2);

		// 保存
		// session.save(employee1);
		// session.save(employee2);
		session.save(department); // 保存部门

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
		Department department = (Department) session.get(Department.class, 1);
		System.out.println(department);
		System.out.println(department.getEmployees());

		// Employee employee = (Employee) session.get(Employee.class, 1);
		// System.out.println(employee);
		// System.out.println(employee.getDepartment());

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

		// // 从员工方解除
		// Employee employee = (Employee) session.get(Employee.class, 1);
		// employee.setDepartment(null);

		// 从部门方解除（与inverse有关系，为false时可以解除）
		Department department = (Department) session.get(Department.class, 1);
		department.getEmployees().clear();

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

		// // 删除员工方（多方），对对方没有影响
		// Employee employee = (Employee) session.get(Employee.class,2);
		// session.delete(employee);

		// 删除部门方（一方）
		// a, 如果没有关联的员工：能删除。
		// b, 如果有关联的员工且inverse=true，由于不能维护关联关系，所以会直接执行删除，就会有异常
		// c, 如果有关联的员工且inverse=false，由于可以维护关联关系，他就会先把关联的员工的外键列设为null值，再删除自己。
		Department department = (Department) session.get(Department.class, 4);
		session.delete(department);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
