package com.zxyairings.codelib.jdbc.service;

import com.zxyairings.codelib.jdbc.dao.UserDao;
import com.zxyairings.codelib.jdbc.domain.User;

//这里通过这个类来模拟业务逻辑层
public class UserService {
	private UserDao userDao;//通过依赖注入赋值

	public void regist(User user) {
		this.userDao.addUser(user);
		// sendMail.send(user);
	}
}
