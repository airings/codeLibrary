package com.zxyairings.codelib.hibernate.k_query_hql;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Department.class)// 添加Hibernate实体类（加载对应的映射文件）
			.addClass(Employee.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	// 准备数据
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 保存一些部门数据
		for (int i = 1; i <= 10; i++) {
			Department department = new Department();
			department.setName("开发部_" + i);
			session.save(department);
		}

		// 保存一些员工数据
		for (int i = 1; i <= 20; i++) {
			Employee employee = new Employee();
			employee.setName("李XX_" + i);
			session.save(employee);
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// 使用HQL查询
	// HQL: Hibernate Query Language.
	// 特点：
	// >> 1，与SQL相似，SQL中的语法基本上都可以直接使用。
	// >> 2，SQL查询的是表和表中的列；HQL查询的是对象与对象中的属性。
	// >> 3，HQL的关键字不区分大小写，类名与属性名是区分大小写的。
	// >> 4，SELECT可以省略.
	@Test
	public void testHql() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------
		String hql = null;

		// 1，简单的查询
		// hql = "FROM Employee";
		// hql = "FROM Employee AS e"; // 使用别名
		// hql = "FROM Employee e"; // 使用别名，as关键字可省略

		// 2，带上过滤条件的（可以使用别名）：Where
		// hql = "FROM Employee WHERE id<10";
		// hql = "FROM Employee e WHERE e.id<10";
		// hql = "FROM Employee e WHERE e.id<10 AND e.id>5";

		// 3，带上排序条件的：Order By
		// hql = "FROM Employee e WHERE e.id<10 ORDER BY e.name";
		// hql = "FROM Employee e WHERE e.id<10 ORDER BY e.name DESC";
		// hql = "FROM Employee e WHERE e.id<10 ORDER BY e.name DESC, id ASC";

		// 4，指定select子句（不可以使用select *）
		// hql = "SELECT e FROM Employee e"; // 相当于"FROM Employee e"
		// hql = "SELECT e.name FROM Employee e"; // 只查询一个列，返回的集合的元素类型就是这个属性的类型
		// hql = "SELECT e.id,e.name FROM Employee e"; // 查询多个列，返回的集合的元素类型是Object数组
		// hql = "SELECT new Employee(e.id,e.name) FROM Employee e"; // 可以使用new语法，指定把查询出的部分属性封装到对象中

		// 5，执行查询，获得结果（list、uniqueResult、分页 ）
		// Query query = session.createQuery("FROM Employee e WHERE id<3");
		// query.setFirstResult(0);
		// query.setMaxResults(10);
		// // List list = query.list(); // 查询的结果是一个List集合
		// Employee employee = (Employee) query.uniqueResult();// 查询的结果是唯一的一个结果，当结果有多个，就会抛异常
		// System.out.println(employee);

		// 6，方法链
		List list = session.createQuery(//
				"FROM Employee")//
				.setFirstResult(0)//
				.setMaxResults(10)//
				.list();

		// // ----- 执行查询
		// List list = session.createQuery(hql).list();
		//
		// ----- 显示结果
		for (Object obj : list) {
			if (obj.getClass().isArray()) {
				System.out.println(Arrays.toString((Object[]) obj));
			} else {
				System.out.println(obj);
			}
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testHql_2() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------
		String hql = null;

		// 1，聚集函数：count(), max(), min(), avg(), sum()
		// hql = "SELECT COUNT(*) FROM Employee"; // 返回的结果是Long型的
		// hql = "SELECT min(id) FROM Employee"; // 返回的结果是id属性的类型
		// Number result = (Number) session.createQuery(hql).uniqueResult();
		// System.out.println(result.getClass());
		// System.out.println(result);

		// 2，分组: Group By ... Having
		// hql = "SELECT e.name,COUNT(e.id) FROM Employee e GROUP BY e.name";
		// hql = "SELECT e.name,COUNT(e.id) FROM Employee e GROUP BY e.name HAVING count(e.id)>1";
		// hql = "SELECT e.name,COUNT(e.id) FROM Employee e WHERE id<9 GROUP BY e.name HAVING count(e.id)>1";
		// ---
		// hql = "SELECT e.name,COUNT(e.id) " + //
		// "FROM Employee e " + //
		// "WHERE id<9 " + //
		// "GROUP BY e.name " + //
		// "HAVING count(e.id)>1 " + //
		// "ORDER BY count(e.id) ASC";
		// ---
		// hql = "SELECT e.name,COUNT(e.id) AS c " + //
		// "FROM Employee e " + //
		// "WHERE id<9 " + //
		// "GROUP BY e.name " + //
		// "HAVING count(e.id)>1 " + // 在having子句中不能使用列别名
		// "ORDER BY c ASC"; // 在orderby子句中可以使用列别名

		// 3，连接查询 / HQL是面向对象的查询
		// >> 内连接（inner关键字可以省略）
		// hql = "SELECT e.id,e.name,d.name FROM Employee e JOIN e.department d";
		// hql = "SELECT e.id,e.name,d.name FROM Employee e INNER JOIN e.department d";
		// >> 左外连接（outer关键字可以省略）
		// hql = "SELECT e.id,e.name,d.name FROM Employee e LEFT OUTER JOIN e.department d";
		// >> 右外连接（outer关键字可以省略）
		// hql = "SELECT e.id,e.name,d.name FROM Employee e RIGHT JOIN e.department d";
		// 可以使用更方便的方法
		// hql = "SELECT e.id,e.name,e.department.name FROM Employee e";

		// 4，查询时使用参数
		// >> 方式一：使用'?'占位
		// hql = "FROM Employee e WHERE id BETWEEN ? AND ?";
		// List list = session.createQuery(hql)//
		// .setParameter(0, 5)// 设置参数，第1个参数的索引为0。
		// .setParameter(1, 15)//
		// .list();

		// >> 方式二：使用变量名
		// hql = "FROM Employee e WHERE id BETWEEN :idMin AND :idMax";
		// List list = session.createQuery(hql)//
		// .setParameter("idMax", 15)//
		// .setParameter("idMin", 5)//
		// .list();

		// 当参数是集合时，一定要使用setParameterList()设置参数值
		// hql = "FROM Employee e WHERE id IN (:ids)";
		// List list = session.createQuery(hql)//
		// .setParameterList("ids", new Object[] { 1, 2, 3, 5, 8, 100 })//
		// .list();

		// 5，使用命名查询
		// Query query = session.getNamedQuery("queryByIdRange");
		// query.setParameter("idMin", 3);
		// query.setParameter("idMax", 10);
		// List list = query.list();

		// 6，update与delete，不会通知Session缓存
		// >> Update
		// int result = session.createQuery(//
		// "UPDATE Employee e SET e.name=? WHERE id>15")//
		// .setParameter(0, "无名氏")//
		// .executeUpdate(); // 返回int型的结果，表示影响了多少行。
		// System.out.println("result = " + result);
		// >> Delete
		int result = session.createQuery(//
				"DELETE FROM Employee e WHERE id>15")//
				.executeUpdate(); // 返回int型的结果，表示影响了多少行。
		System.out.println("result = " + result);

		// ----- 执行查询并显示结果
		// // List list = session.createQuery(hql).list();
		// for (Object obj : list) {
		// if (obj.getClass().isArray()) {
		// System.out.println(Arrays.toString((Object[]) obj));
		// } else {
		// System.out.println(obj);
		// }
		// }

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testHql_DML() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 第一次显示名称
		Employee employee = (Employee) session.get(Employee.class, 13);
		System.out.println(employee.getName());

		// update与delete，不会通知Session缓存
		int result = session.createQuery(//
				"UPDATE Employee e SET e.name=? WHERE id>10")//
				.setParameter(0, "无名氏3")//
				.executeUpdate(); // 返回int型的结果，表示影响了多少行。

		// 第二次显示名称
		// 在update或delete后，需要refresh(obj)一下以获取最新的状态
		session.refresh(employee);
		System.out.println(employee.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
