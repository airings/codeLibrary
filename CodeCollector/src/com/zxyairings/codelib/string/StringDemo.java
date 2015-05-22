package com.zxyairings.codelib.string;

class StringDemo 
{
	public static void main(String[] args) 
	{
		/*
		内存中有常量池，是个数组。
		String s1 = "abc";//s1是一个类类型变量， "abc"是一个对象。
						//字符串最大特点：一旦被初始化就不可以被改变。
						 
		s1 = "kk"; // 字符串"abc"没有变化，只是s1的指向变化了。

		String s2 = new String("abc"); //这句话表示在内存中有两个对象，一个是“abc”，一个是new出来的

		//s1和s2有什么区别？
		//s1在内存中有一个对象。
		//s2在内存中有两个对象。
		
		System.out.println(s1==s2); //false
		System.out.println(s1.equals(s2));//String类复写了Object类中equals方法，
										//该方法用于判断字符串是否相同。

		*/

		String s = "abcde";
		method_1(s);
	}

	/*
	String类是对字符串事物的描述。
	该类定义了专门用于操作字符串的方法。
	"abc":
	*/

	public static void method_1(String s)
	{
		char ch = s.charAt(3);

		System.out.println("ch="+ch);
		int num = s.codePointAt(3);

		System.out.println("num="+num);

		String s1 = "qq";
		s1 = s1.concat("mm");

		System.out.println("s1="+s1);
		System.out.println("qq"+"mm");

		String a = "opq";
		String b = "opq";
		System.out.println("a==b:"+(a==b));


	}
}
