package com.zxyairings.codelib.jdbc.dao;

import java.util.Date;

import com.zxyairings.codelib.jdbc.dao.impl.UserDaoJdbcImpl;
import com.zxyairings.codelib.jdbc.dao.refactor.UserDaoImpl;
import com.zxyairings.codelib.jdbc.domain.User;

/*
 *这组代码很经典。 

J2EE三层架构简介
		表示层 、业务逻辑层、数据访问层，三层之间用接口隔离。
		
定义domain对象User，定义存取用户的接口

用JDBC实现接口

用配置文件(properties)和反射实现与具体类的耦合 

 * 
 */

public class UserDaoTest {

	/*
	 * 这类很像业务逻辑层的类，它通过接口访问数据访问层
	 */
	public static void main(String[] args) {

//		test();
//		noDependenceDAO();
		testAbstractDao();
	}
	
	public static void testAbstractDao(){
		UserDaoImpl userDao = new UserDaoImpl();
		
		User user = new User();
		user.setBirthday(new Date());
		user.setName("dao name1");
		user.setMoney(1000.0f);

		//find
		User u = userDao.findUser(user.getName(), null);
		System.out.println(u);
		
	}
	
	//使用配置文件和简单工厂模式来解耦
	public static void noDependenceDAO() {
		UserDao userDao = DaoFactory.getInstance().getUserDao();
		
		User user = new User();
		user.setBirthday(new Date());
		user.setName("dao name1");
		user.setMoney(1000.0f);
		
		//create
		userDao.addUser(user);
		System.out.println("inserted user id: "+user.getId());
		
		//find
		User u = userDao.findUser(user.getName(), null);
		System.out.println(u.getId());
		
		//update
		User u2 = userDao.getUser(3);
		u2.setMoney(20000.1f);
		userDao.update(u2);
		
		//delete
		User u3 = userDao.getUser(7);
		userDao.delete(u3);

	}
	
//	这个业务逻辑层的代码基本上是可以的，但是还是存在数据访问层的依赖，如果我们想换另一种数据访问层的实现，
//	还是需要修改这个业务逻辑层的代码，如何才能消除这种依赖呢？1.使用spring 2. 工厂模式
//	目标是在业务逻辑层代码UserDaoTest类中不要出现数据访问层的类UserDaoJdbcImpl，从而消除依赖。
	public static void test() {
		UserDao userDao = new UserDaoJdbcImpl();//就是这句存在依赖
		
		User user = new User();
		user.setBirthday(new Date());
		user.setName("dao name1");
		user.setMoney(1000.0f);
		
		//create
		userDao.addUser(user);
		System.out.println("inserted user id: "+user.getId());
		
		//find
		User u = userDao.findUser(user.getName(), null);
		System.out.println(u.getId());
		
		//update
		User u2 = userDao.getUser(3);
		u2.setMoney(20000.1f);
		userDao.update(u2);
		
		//delete
		User u3 = userDao.getUser(7);
		userDao.delete(u3);
	}

}
