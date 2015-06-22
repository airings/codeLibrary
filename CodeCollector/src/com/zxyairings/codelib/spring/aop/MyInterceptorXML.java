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

public class MyInterceptorXML {
	
	public void doAccessCheck() {
		System.out.println("前置通知"); 
	}
	
	public void doAfterReturning() {
		System.out.println("后置通知");
	}
	
	public void doAfter() {
		System.out.println("最终通知");
	}
	
	public void doAfterThrowing() {
		System.out.println("例外通知");
	}
	
	
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		//if(){//判断用户是否在权限
		System.out.println("进入方法");
		Object result = pjp.proceed();
		System.out.println("退出方法");
		//}
		return result;
	}
	
}