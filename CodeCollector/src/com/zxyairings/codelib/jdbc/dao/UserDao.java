package com.zxyairings.codelib.jdbc.dao;

import com.zxyairings.codelib.jdbc.domain.User;

//对User对象的数据访问接口，业务逻辑层可以通过这个接口访问到数据访问层的数据
//这个接口为业务逻辑层服务
//使业务逻辑层不依赖于数据访问层的具体实现，业务逻辑层只使用这个接口

public interface UserDao {
	
	public void addUser(User user);

	public User getUser(int userId);

	public User findUser(String loginName, String password);

	public void update(User user);

	public void delete(User user);

}
