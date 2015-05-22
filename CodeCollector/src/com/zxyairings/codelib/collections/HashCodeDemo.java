package com.zxyairings.codelib.collections;

/*
 * 当对象存Hash类型的集合时，先调用hashcode计算出该对象应该存储的位置，然后如果在该位置有不止一个对象，才会调用equals方法。
 * 
 * 提示1：
 * 		通常来说，一个类的连个实例对象用equals()方法比较的结果相等时，他们的哈希码也必须相等，但反之则不成立，即equals方法比较结果不相等的对象可以有相同的哈希码。
 * 提示2：
 * 		当一个对象被存储进HashSet集合中后，就不能修改这个对象中的那些参与计算哈希值得字段了，否则，对象修改后的哈希值与最初存进HashSet集合中时的哈希值就不同了，在这种情况下，即使在contains方法
 * 		使用该对象的当前引用作为参数取HashSet集合中检索对象，也将返回找不到对象的结果，这也会导致无法从HashSet集合中单独删除当前对象，从而造成内存泄露。
 * */

public class HashCodeDemo {

	public static void main(String[] args) throws Exception{
		Collection collections = new HashSet();
		ReflectPoint pt1 = new ReflectPoint(3,3);
		ReflectPoint pt2 = new ReflectPoint(5,5);
		ReflectPoint pt3 = new ReflectPoint(3,3);
		//我们要求pt1和pt3是相同的对象，所以重写了equals方法，但是这时这两个对象的hashcode方法仍然返回不同的hashcode
		//这就造成在hash类型的集合里，他们仍然被当成不同的对象对待，
		//所以，在编程时，我们要求：如果两个对象的equals相等的话，那么也应该让他们的hashcode相等。但是如果对象并不存到hash类型的集合里，那就没有必要写hashcode方法
		

		collections.add(pt1);
		collections.add(pt2);
		collections.add(pt3);
		collections.add(pt1);	
		
		pt1.y = 7;//hashcode 变了
		collections.remove(pt1);//没有删除pt1，造成内存泄露
		
		System.out.println(collections.size());
	}
}
