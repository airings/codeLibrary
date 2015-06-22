package com.zxyairings.codelib.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面
 *
 */
@Aspect
public class MyInterceptorAnnotation {
	@Pointcut("execution (* com.zxyairings.codelib.spring.aop.PersonServiceBean.*(..))") //aop表达式定义哪些方法可以被拦截
	private void anyMethod() {}//声明一个切入点
	
	@Before("anyMethod() && args(name)")
	public void doAccessCheck(String name) {
		System.out.println("前置通知:"+ name); //可以得到拦截方法的传入参数
	}
	@AfterReturning(pointcut="anyMethod()",returning="result")
	public void doAfterReturning(String result) {
		System.out.println("后置通知:"+ result);//可以得到拦截方法的返回结果
	}
	@After("anyMethod()")
	public void doAfter() {
		System.out.println("最终通知");
	}
	@AfterThrowing(pointcut="anyMethod()",throwing="e")
	public void doAfterThrowing(Exception e) {
		System.out.println("例外通知:"+ e);//可以得到拦截方法跑出的异常
	}
	
	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		//if(){//判断用户是否在权限
		System.out.println("进入方法");
		Object result = pjp.proceed();
		System.out.println("退出方法");
		//}
		return result;
	}
	
}