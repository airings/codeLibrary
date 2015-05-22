package com.zxyairings.codelib.generic;



/*
class Tool
{
	private Worker w;
	public void setWorker(Worker w)
	{
		this.w = w;
	}
	public Worker getWorker()
	{
		return w;
	}
}
*/

class Worker
{

}
class Student
{
}
//泛型前做法。
class Tool
{
	private Object obj;
	public void setObject(Object obj)
	{
		this.obj = obj;
	}
	public Object getObject()
	{
		return obj;
	}
}

//泛型类。
/*
什么时候定义泛型类？
当类中要操作的引用数据类型不确定且固定的时候，
早期定义Object来完成扩展。
现在定义泛型来完成扩展。
*/
//Utils只可以操作QQ类型的数据
class Utils<QQ>
{
	private QQ q;
	public void setObject(QQ q)
	{
		this.q = q;
	}
	public QQ getObject()
	{
		return q;
	}
}


class  GenericClass
{
	public static void main(String[] args) 
	{

		Utils<Worker> u = new Utils<Worker>();

		u.setObject(new Student());
		Worker w = u.getObject();;
		/*
		Tool t = new Tool();
		t.setObject(new Student());
		Worker w = (Worker)t.getObject();
		*/
	}
}
