package com.zxyairings.codelib.hibernate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zxyairings.codelib.hibernate.domain.User_collection;;

public class App_collection {
	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User_collection.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 构建对象
		User_collection user = new User_collection();
		user.setName("zhangsan");
		// >> Set集合
//		user.setAddressSet(new TreeSet<String>()); // 当设置了sort属性时，就要使用SortedSet类型
		user.setAddressSet(new HashSet<String>()); // 当设置了order by属性时，可以使用HashSet
		user.getAddressSet().add("2yufu park");
		user.getAddressSet().add("1tangdong road");
		// >> List集合
		user.getAddressList().add("yufu park");
		user.getAddressList().add("tangdong road");
		user.getAddressList().add("tangdong road");
		// >> 数组
		user.setAddressArray(new String[] { "yufu park", "tangdong road" });
		// >> Map集合
		user.getAddressMap().put("company", "yufu park");
		user.getAddressMap().put("family", "tangdong road");
		// >> Bag集合
		user.getAddressBag().add("yufu park");
		user.getAddressBag().add("tangdong road");
		user.getAddressBag().add("tangdong road");

		// 保存
		session.save(user);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 获取数据
		User_collection user = (User_collection) session.get(User_collection.class, 3);

		// 显示信息
		System.out.println(user.getName());
		System.out.println(user.getAddressSet());
		System.out.println(user.getAddressList());
		System.out.println(Arrays.toString(user.getAddressArray()));
		System.out.println(user.getAddressMap());
		System.out.println(user.getAddressBag());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
