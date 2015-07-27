package com.zxyairings.codelib.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * 写servlet的步骤
 * 1. 在tomcat中新建一个test web应用，然后再web应用中新建一个WEB-INF/classes目录
 * 2. 在classes目录中新建一个FirstServlet.java，内容就是这个类
 * 3. set classpath，编译此类
 * 4. 在WEB-INF目录中新建一个web.xml,配置servlet的对外访问路径
 * 5. 启动tomcat，访问 http://localhost:8080/test/FirstServlet
 * check this example at /Users/airings/Applications/apache-tomcat-6.0.44/webapps/test
 * 
 * 如何新建一个web应用？
 * Go to tomcat 的home路径，在webapps下新建文件夹即你的web应用的文件夹，再建这些文件夹：webapps/test/WEB-INF/classes
 * 将你的java程序放在这里，并在这里编译。
 * 
 */

public class FirstServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		OutputStream outputStream = res.getOutputStream();
		outputStream.write("hello servlet!".getBytes());;
		
	}
}
