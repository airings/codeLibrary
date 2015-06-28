package com.zxyairings.codelib.jdbc.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

//动态代理 实现 MyConnection

class MyConnectionHandler implements InvocationHandler {
	private Connection realConnection;
	private Connection warpedConnection;
	private MyDataSource2 dataSource;

	private int maxUseCount = 10;
	private int currentUserCount = 0;

	MyConnectionHandler(MyDataSource2 dataSource) {
		this.dataSource = dataSource;
	}

	Connection bind(Connection realConn) {
		this.realConnection = realConn;
		this.warpedConnection = (Connection) Proxy.newProxyInstance(this
				.getClass().getClassLoader(), new Class[] { Connection.class },
				this);//动态代理
		return warpedConnection;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if ("close".equals(method.getName())) {
			this.currentUserCount++;
			if (this.currentUserCount < this.maxUseCount){
				this.dataSource.connectionsPool.addLast(this.warpedConnection);
				return null;
			}
			else {
				this.dataSource.currentCount--;
				this.realConnection.close();
				return null;
				
			}
		}
		return method.invoke(this.realConnection, args);
	}

}
