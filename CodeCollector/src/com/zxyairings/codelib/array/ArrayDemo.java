package com.zxyairings.codelib.array;

/*
Java程序在运行时，需要在内存中的分配空间。
为了提高运算效率，有对空间进行了不同区域的划分，因为每一片区域都有特定的处理数据方式和内存管理方式。

java堆内存3个特点：
数组和对象，通过new建立的实例都存放在堆内存中。

每一个实体都有内存地址值：通过地址值将堆内存与栈中的引用局部变量联系起来

实体中的变量都有默认初始化值

实体不在被使用，会在不确定的时间内被垃圾回收器回收

栈：用于存储局部变量，当数据使用完，所占空间会自动释放。

*/


class ArrayDemo 
{
	public static void main(String[] args) 
	{

		//元素类型[] 数组名 = new 元素类型[元素个数或数组长度];
		
		//需求：想定义一个可以存储3个整数的容器。
		int[] x = new int[3];



		//打印数组中角标为0的元素的值。
		System.out.println(x[1]);
	}
}