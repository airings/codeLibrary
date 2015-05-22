package com.zxyairings.codelib.collections;



/*
使用LinkedList模拟一个堆栈或者队列数据结构。

堆栈：先进后出  如同一个杯子。
队列：先进先出 First in First out  FIFO 如同一个水管。



*/

import java.util.*;
class DuiLie
{
	private LinkedList link;

	DuiLie()
	{
		link = new LinkedList();
	}
	
	public void myAdd(Object obj)
	{
		link.addFirst(obj);
	}
	public Object myGet()
	{
		return link.removeFirst();
	}
	public boolean isNull()
	{
		return link.isEmpty();
	}

}



class  Stack
{
	public static void main(String[] args) 
	{
		DuiLie dl = new DuiLie();
		dl.myAdd("java01");
		dl.myAdd("java02");
		dl.myAdd("java03");
		dl.myAdd("java04");

		while(!dl.isNull())
		{
			System.out.println(dl.myGet());
		}
	}
}
