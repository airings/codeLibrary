package com.zxyairings.codelib.array;

class ArrayDemo3 
{
	public static void main(String[] args) 
	{
		//数组的操作：
		//获取数组中的元素。通常会用到遍历。

		//int[] arr = new int[3];
		int[] arr = {3,6,5,1,8,9,67};

		//数组中有一个属性可以直接获取到数组元素个数。length.
		//使用方式：数组名称.length = 
		//System.out.println("length:"+arr.length);

		//int sum = 0;
		/*
		for(int x=0; x<arr.length; x++)
		{
			//sum += arr[x];
			System.out.println("arr["+x+"]="+arr[x]+";");//arr[0]=0;
		}
		*/
		
		System.out.println(arr);//打印出数组实体的引用的值
		//[I@6276e1db: [表示数据类型，I表示数组中存的是int类型，@后表示的是数组的内存存放地址，是hash值，16进制

		Object[] y = new Object[3];
		System.out.println(y);
		
		printArray(arr);
//		printArray(arr);

	}

	//定义功能，用于打印数组中的元素。元素间用逗号隔开。
	public static void printArray(int[] arr)
	{
		System.out.print("[");
		for(int x=0; x<arr.length; x++)
		{
			if(x!=arr.length-1)
				System.out.print(arr[x]+", ");
			else
				System.out.println(arr[x]+"]");

		}		
	}
}
