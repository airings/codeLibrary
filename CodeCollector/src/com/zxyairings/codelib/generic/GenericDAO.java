package com.zxyairings.codelib.generic;

import java.util.Set;
//类的泛型
//dao data access object--->crud增删改查
public class GenericDAO<E>  {
	public void add(E x){
		
	}
	
	public E findById(int id){
		return null;
	}
	
	public void delete(E obj){
		
	}
	
	public void delete(int id){
		
	}	
	
	public void update(E obj){
		
	}
	
//	public static void update2(E obj){}//静态方法不能使用类的泛型类型
	
	public static <T> void update2(T obj){
		
	}
	
	public E findByUserName(String name){
		return null;
	}
	public Set<E> findByConditions(String where){
		return null;
	}
}