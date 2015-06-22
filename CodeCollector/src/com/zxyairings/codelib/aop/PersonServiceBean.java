package com.zxyairings.codelib.aop;
/*
1. 拦截所有业务方法
2. 判断用户是否有权限，有权限就允许他执行业务方法，没有权限不允许他执行业务方法
（是否有权限是根据user是否为null作为判断依据）

这些步骤都是横切性关注点

代理：
1.静态代理：企业开发中很少使用
2.动态代理：一般都使用这个, 不需要手动实现目标对象的接口的静态代理类，而交给JDK通过反射动态地生成动态代理类的字节码

使用JDk Proxy这个类创建动态代理的条件是：目标对象必须实现一个接口，他必须是面向接口编程。

如果目标对象实现了接口，就使用JDK生成代理类
如果目标对象没有实现接口，就是用cglib生成代理类。

spring为我们包装了aop的实现，它的原理就是这些。

*/
//public class PersonServiceBean implements PersonService{ //使用JDK代理，因为他要求目标类必须实现接口
public class PersonServiceBean{ // 使用GClib实现代理，因为它不要求目标对象必须实现接口，它通过继承的方式类覆盖目标类的所有非final方法来实现代理的目的
	private String user = null;
	
	public PersonServiceBean(){
		
	}
	
	public PersonServiceBean(String user){
		this.user = user;
	}

	public void save(String name) {
		System.out.println("this is save() method");
		
	}

	public void update(String name, Integer personid) {
		System.out.println("this is update() method");
		
	}

	public String getPersonName(Integer personid) {
		System.out.println("this is getPersonName() method");
		return "xxx";
	}

	public Object getUser() {
		return user;
	}

}
