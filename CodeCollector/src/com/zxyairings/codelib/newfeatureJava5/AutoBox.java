package com.zxyairings.codelib.newfeatureJava5;

/*
JDK1.5版本以后出现的新特性。

*/

class AutoBox 
{
	public static void main(String[] args) 
	{
		
//		Integer x = new Integer(4);

		Integer x = 4;//自动装箱。//new Integer(4)
		//Integer x = null; // 注意判断

		x = x/* x.intValue() */ + 2;//x+2:x 进行自动拆箱。变成成了int类型。和2进行加法运算。
					//再将和进行装箱赋给x。

		

		Integer m = 128;
		Integer n = 128;

		sop("m==n:"+(m==n));

		Integer a = 127;
		Integer b = 127;

		sop("a==b:"+(a==b));//结果为true。因为a和b指向了同一个Integer对象。
						//因为当数值在byte范围内容，对于新特性，如果该数值已经存在，则不会在开辟新的空间。
	}

	public static void method()
	{
		Integer x = new Integer("123");

		Integer y = new Integer(123);

		sop("x==y:"+(x==y));
		sop("x.equals(y):"+x.equals(y));
	}
	
	public static void method1(){
		Integer iObj = 3;
		System.out.println(iObj + 12);
		
		String s1 = new String("abc");
		String s2 = new String("abc");		
		Integer i1 = 137;
		Integer i2 = 137;
		

		System.out.println(i1 == i2);
		
		Integer i3 = Integer.valueOf(213);
		Integer i4 = Integer.valueOf(213);
		System.out.println(i3==i4);
	}

	public static void sop(String str)
	{
		System.out.println(str);
	}
	
}
