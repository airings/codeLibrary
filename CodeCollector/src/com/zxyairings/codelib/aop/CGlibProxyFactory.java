package com.zxyairings.codelib.aop;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGlibProxyFactory implements MethodInterceptor {
private Object target;
	
	public Object createProxyInstance(Object target){
		this.target = target;
		Enhancer enhancer = new Enhancer();
//		enhancer继承目标类，对目标类中的所有非final方法，进行覆盖
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		PersonServiceBean bean = (PersonServiceBean) this.target;
		Object result = null;
		if(bean.getUser() != null) {
			result = methodProxy.invoke(target, args);//调用目标对象的被拦截方法
		}
		return result;
	}

}
