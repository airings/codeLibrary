package com.zxyairings.codelib.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理
public class JDKProxyFactory implements InvocationHandler {
	private Object target;//目标对象，被代理的类
	
	public Object createProxyInstance(Object target){
		this.target = target;
		
//		当调用目标对象的方法时，通过下面的生成动态代理，就可以将目标对象的方法拦截到，拦截对象就是this，即代理类，
//		拦截后就会回调代理类的invoke方法，它的第二个参数就是被拦截到的目标对象的方法，第三个参数就是这个被拦截对象的参数列表
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this);//this是拦截对象。
	}

//	method是被拦截到的方法
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {//环绕通知
		PersonServiceBean bean = (PersonServiceBean) this.target;
		Object result = null; 
		if(bean.getUser()!=null){
			//..... advice()-->前置通知
			try {
				result = method.invoke(target, args);
				// afteradvice() -->后置通知
			} catch (RuntimeException e) {
				//exceptionadvice()--> 例外通知
			}finally{
				//finallyadvice(); -->最终通知
			}
		}
		return result;
	}



}
