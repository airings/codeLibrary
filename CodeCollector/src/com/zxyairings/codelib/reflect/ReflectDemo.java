package com.zxyairings.codelib.reflect;

/*
 * 反射是所有框架的基石。
 * 
 * 反射的基石是 Class类
 * 
 * Class对象 代表的是 类的字节码：如果在java程序中定义了3个类，那么在内存中就有这3个不同类的字节码，那么每一个字节码就是一个Class类的实例对象。
 * 
 * 一个类被类加载器加载到内存中，占用一片存储空间，这个空间里的内容就是类的字节码，这一个个的内存空间可分别用一个个的对象来表示，这些对象显然有相同的类型，就是Class
 * 
 * 总之，只要是在源程序中出现的类型，都有各自的Class实例对象，例如，int[], void
 * 
 * 对反射的最好的总结：“反射就是把Java类中的各种成分 映射成 相应的java类。”
 * 一个类中的每个成员都可以用相应的反射API类的一个实例对象来表示。
 * 
 * 反射比较消耗性能
 * 
 * */


import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectDemo {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String str1 = "abc";
		Class cls1 = str1.getClass();
		Class cls2 = String.class;
		Class cls3 = Class.forName("java.lang.String");
		System.out.println(cls1 == cls2);//true
		System.out.println(cls1 == cls3);//true
		
		System.out.println(cls1.isPrimitive());//false
		System.out.println(int.class.isPrimitive());//true
		System.out.println(int.class == Integer.class);//false
		System.out.println(int.class == Integer.TYPE);//true
		System.out.println(int[].class.isPrimitive());//false
		System.out.println(int[].class.isArray());		//true
		
		
		
		//构造方法的反射
		//new String(new StringBuffer("abc"));
		//Class的对象.newInstance();不带参数的构造方法
		Constructor constructor1 = String.class.getConstructor(StringBuffer.class);//获得Constructor时要用类型
		String str2 = (String)constructor1.newInstance(/*"abc"*/new StringBuffer("abc"));//调用方法是要用上面相同类型的实例对象
		System.out.println(str2.charAt(2));
		
		
		
		//成员变量的反射
		ReflectPoint pt1 = new ReflectPoint(3,5);
		Field fieldY = pt1.getClass().getField("y");
		//fieldY的值是多少？是5,错！fieldY不是对象身上的变量，而是类上，要用它去取某个对象上对应的值
		System.out.println(fieldY.get(pt1));
		Field fieldX = pt1.getClass().getDeclaredField("x");//x is private
		fieldX.setAccessible(true);//暴力反射
		System.out.println(fieldX.get(pt1));	
		// 成员变量的反射-高级
		changeStringValue(pt1);
		System.out.println(pt1);
		
		
		//成员方法的反射
		//成员方法是类的，不是对象的，成员方法跟对象没有关系。但是我们必须先得到对象再通过该对象调用类的成员方法，所以在反射下，我们先得到方法，然后再针对某个对象调用该方法
		Method methodCharAt = String.class.getMethod("charAt", int.class);
		System.out.println(methodCharAt.invoke(str1, 1));
//		System.out.println(methodCharAt.invoke(null, 1)); 如果第一个参数是null，说明这个方法不依赖于对象，那它肯定是静态方法。
		System.out.println(methodCharAt.invoke(str1, new Object[]{2}));//这是按照java1.4的语法来调用，因为他没有可变参数。
		
		
		
//		调用TestArguments的main方法：
		//TestArguments.main(new String[]{"111","222","333"});
		String startingClassName = args[0];
		Method mainMethod = Class.forName(startingClassName).getMethod("main", String[].class);
		//启动java程序的main方法的参数是一个字符串数组，即public static void main(String[] args)，通过反射方式来调用这个main方法时，如何为invoke方法传递参数呢？按jdk1.5的语法，整个数组是一个参数，
		//而按jdk1.4的语法，数组中的每个元素对应一个参数，当吧一个字符串数组作为参数传递给invoke方法时，javac到底会按照哪种语法进行处理呢？jdk1.5肯定要兼容jdk1.4的语法，会按照jdk1.4的语法进行处理，即把
		//数组打散成为若干单独的参数。所以，在给main方法传递参数时，不能使用代码 mainMethod.invoke(null, new String[]{"111","222","333"}), javac只把它当做jdk1.4的语法进行理解，而不把它当做jdk1.5
		//的语法解释，因此会出现参数类型不对的问题。
		//mainMethod.invoke(null, new String[]{"111","222","333"})//会报异常，参数个数不对
		//下面是两种解决方式：
		//mainMethod.invoke(null, new Object[]{new String[]{"111","222","333"}});//在数组外再包装一层，将其变成一个参数
		mainMethod.invoke(null, (Object)new String[]{"111","222","333"});//将String[]变成一个Object对象，告诉编译器我这个是一个对象，编译时不把参数当做数组看待，不要将数组拆开来算参数个数。
		
		
		
		
		//数组的反射：具有相同维度和元素类型的数组属于同一个类型，即具有相同的Class实例对象
		int [] a1 = new int[]{1,2,3};
		int [] a2 = new int[4];
		int[][] a3 = new int[2][3];
		String [] a4 = new String[]{"a","b","c"};
		System.out.println(a1.getClass() == a2.getClass());//true
//		System.out.println(a1.getClass() == a4.getClass());//false
//		System.out.println(a1.getClass() == a3.getClass());//false
		System.out.println(a1.getClass().getName());
		System.out.println(a1.getClass().getSuperclass().getName());
		System.out.println(a4.getClass().getSuperclass().getName());
		
		Object aObj1 = a1;
		Object aObj2 = a4;
		//Object[] aObj3 = a1; //基本类型int不是Objcet
		Object[] aObj4 = a3;//int[] 是 Object
		Object[] aObj5 = a4;
		//总结：基本类型的一位数组可以被当做Object类型使用，不能当做Object[]类型使用，非基本类型的一位数组则都可以。
		
		
		//由上面的引出的
		System.out.println(a1);
		System.out.println("array toString:"+a1.toString());
		System.out.println(a4);
		//Arrays.asList 方法处理int[] 和 String[] 时的差异
		System.out.println(Arrays.asList(a1));//
		System.out.println(Arrays.asList(a4));	
		
		//数组反射java.lang.reflect.Array
		printObject(a4);
		
		printObject("xyz");
		//?如何得到数组的类型？目前没有办法。需要去除每个元素对象，然后再对每个对象进行判断，因为其中每个具体元素的类型都可以不同：Object[] x = new Object[]{"abc",Integer.Max,'c'}
	}

	private static void printObject(Object obj) {
		Class clazz = obj.getClass();
		if(clazz.isArray()){
			int len = Array.getLength(obj);
			for(int i=0;i<len;i++){
				System.out.println(Array.get(obj, i));
			}
		}else{
			System.out.println(obj);
		}
		
	}

	private static void changeStringValue(Object obj) throws Exception {
		Field[] fields = obj.getClass().getFields();
		for(Field field : fields){
			//if(field.getType().equals(String.class)){
			if(field.getType() == String.class){//对字节码的比较就用 ==，因为字节码只有一份，所以都是那同一份字节码
				String oldValue = (String)field.get(obj);
				String newValue = oldValue.replace('b', 'a');
				field.set(obj, newValue);
			}
		}
		
	}

}

//写程序调用这个类的main方法
class TestArguments{
	public static void main(String[] args){
		for(String arg : args){
			System.out.println(arg);
		}
	}
}