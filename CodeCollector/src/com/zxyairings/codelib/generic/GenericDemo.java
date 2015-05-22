package com.zxyairings.codelib.generic;

/*
 * 使用泛型的好处：
 * 1.在编译时，挡住其他类型的非法的输入
 * 2.在使用时对象时，不需要进行类型转化。
 * 
 * 如果你不需要这两个便捷，那么你就可以不使用泛型。因为编译后是去类型化的，有没有泛型是一样的，泛型是给编译器看的。
 * 
 * 泛型是提供给javac编译器使用的，可以限定集合中的输入类型，让编译器挡住源程序中的非法输入，
 * 编译器编译带类型说明的集合时会去除掉“类型”信息，使程序运行效率不受影响，对于参数化的泛型类型，
 * getClass()方法的返回值和原始类型完全一样。由于编译生成的字节码会去掉泛型的类型信息，
 * 只要能跳过编译器，就可以往某个泛型集合中加入其它类型的数据，
 * 例如，用反射得到集合，再调用其add方法即可。
 * 
 * */


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


import com.zxyairings.codelib.reflect.ReflectPoint;

public class GenericDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ArrayList collection1 = new ArrayList();
		collection1.add(1);
		collection1.add(1L);
		collection1.add("abc");
		//int i = (Integer)collection1.get(1);
		
		ArrayList<String> collection2 = new ArrayList<String>();
		//collection2.add(1);
		//collection2.add(1L);
		collection2.add("abc");
		String element = collection2.get(0);	
		
		//new String(new StringBuffer("abc"));
		Constructor<String> constructor1 = String.class.getConstructor(StringBuffer.class);
		String str2 = constructor1.newInstance(/*"abc"*/new StringBuffer("abc"));
		System.out.println(str2.charAt(2));		
		
		ArrayList<Integer> collection3 = new ArrayList<Integer>();
		
		//编译器会去掉泛型信息，所以编译完成以后 collection3 和 collection2 的字节码是同一份，这叫去类型化
		System.out.println(collection3.getClass() == collection2.getClass());//true
		
		//因为去类型化，跳过编译器，可以通过反射的方式来加入其他类型的对象
		//collection3.add("abc");
		collection3.getClass().getMethod("add", Object.class).invoke(collection3, "abc");
		System.out.println(collection3.get(0));
		
		printCollection(collection3);
		
		//Class<Number> x = String.class.asSubclass(Number.class);
		Class<?> y;
		Class<String> x =null;//Class.forName("java.lang.String");
		y=x;
//		x=y;
		
		
		
		HashMap<String,Integer> maps = new HashMap<String, Integer>();
		maps.put("zxx", 28);
		maps.put("lhm", 35);
		maps.put("flx", 33);
		
		Set<Map.Entry<String,Integer>> entrySet = maps.entrySet();
		for(Map.Entry<String, Integer> entry : entrySet){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		
		
		add(3,5);
		//类型推断，取"最大公约数"
		Number x1 = add(3.5,3);
//		float x3 = add(3.5,3);
		Object x2 = add(3,"abc");
		
		swap(new String[]{"abc","xyz","itcast"},1,2);
		swap(new int[][]{{1,3},{5,4}},3,4);
//		swap(new int[]{1,3,5,4,6},3,4);//泛型类型引用的是引用类型，不能是基本类型
		
		Object obj = "abc";
		String x3 = autoConvert(obj);
		
		//copy1和copy2有什么区别
		copy1(new Vector<String>(),new String[10]);
		copy2(new Date[10],new String[10]);//为什么可以？类型推断！取最小公倍数：就是Object
		//copy1(new Vector<Date>(),new String[10]);
		
		//类的泛型
		GenericDAO<ReflectPoint> dao = new GenericDAO<ReflectPoint>();
		dao.add(new ReflectPoint(3,3));		
		//String s = dao.findById(1);
		
		//通过反射获得泛型的参数化类型
		//Vector<Date> v1 = new Vector<Date>();
//		v1.getClass()//这样无法得到Vector的Date的类型
		Method applyMethod = GenericDemo.class.getMethod("applyVector", Vector.class);
		Type[] types = applyMethod.getGenericParameterTypes();
		ParameterizedType pType = (ParameterizedType)types[0];
		System.out.println(pType.getRawType());
		System.out.println(pType.getActualTypeArguments()[0]);
	}
	
	public static void applyVector(Vector<Date> v1){
		
	}

	//练习
	private static <T> void fillArray(T[] a,T obj){
		for(int i=0;i<a.length;i++){
			a[i] = obj;
		}
	}
	
	private static <T> T autoConvert(Object obj){
		return (T)obj;
	}
	
	private static <T> void swap(T[] a,int i,int j){
		T tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	private static <T> T add(T x,T y){
		return null;
	}
	
	//使用?通配符可以引用其他各种参数化的类型，?通配符定义的变量主要用作引用，可以调用与参数化无关的方法，不能调用与参数化有关的方法。
	public static void printCollection(Collection<?> collection){
		//collection.add(1);不能调用与类型相关的方法
		//cols = new HashSet<Date>();没错
		System.out.println(collection.size());
		for(Object obj : collection){
			System.out.println(obj);
		}
	}
	
	public static <T> void printCollection2(Collection<T> collection, T obj2){
		//collection.add(1);
		System.out.println(collection.size());
		for(Object obj : collection){
			System.out.println(obj);
		}
//		可以使用add方法
		collection.add(obj2);

	}
	
	
	public static <T> void copy1(Collection<T> dest,T[] src){
		
	}
	
	public static <T> void copy2(T[] dest,T[] src){
		
	}	
}
